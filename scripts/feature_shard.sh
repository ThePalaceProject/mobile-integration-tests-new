#!/usr/bin/env bash

set -euo pipefail

if [[ $# -lt 2 ]]; then
  echo "Usage: $0 <shard_index_1_based> <total_shards> [features_dir] [tag_expression]" >&2
  exit 1
fi

shard_index="$1"
total_shards="$2"
features_dir="${3:-src/test/resources/features}"
tag_expression="${4:-}"

if ! [[ "$shard_index" =~ ^[0-9]+$ && "$total_shards" =~ ^[0-9]+$ ]]; then
  echo "shard_index and total_shards must be positive integers" >&2
  exit 1
fi

if (( shard_index < 1 || total_shards < 1 || shard_index > total_shards )); then
  echo "Invalid shard configuration: shard_index=$shard_index total_shards=$total_shards" >&2
  exit 1
fi

feature_files=()
while IFS= read -r feature_file; do
  feature_files+=("$feature_file")
done < <(find "$features_dir" -type f -name '*.feature' | sort)

if (( ${#feature_files[@]} == 0 )); then
  echo "No feature files found under $features_dir" >&2
  exit 1
fi

if [[ -n "$tag_expression" ]]; then
  tags=()
  while IFS= read -r tag; do
    [[ -n "$tag" ]] && tags+=("$tag")
  done < <(printf '%s\n' "$tag_expression" | grep -Eo '@[A-Za-z0-9_-]+' | sort -u)

  if (( ${#tags[@]} > 0 )); then
    filtered_feature_files=()
    for feature_file in "${feature_files[@]}"; do
      for tag in "${tags[@]}"; do
        if grep -Fq "$tag" "$feature_file"; then
          filtered_feature_files+=("$feature_file")
          break
        fi
      done
    done

    if (( ${#filtered_feature_files[@]} > 0 )); then
      feature_files=("${filtered_feature_files[@]}")
    fi
  fi
fi

weighted_feature_entries=()
for feature_file in "${feature_files[@]}"; do
  scenario_count=$(grep -Ec '^[[:space:]]*Scenario( Outline)?:' "$feature_file")
  (( scenario_count == 0 )) && scenario_count=1
  weighted_feature_entries+=("${scenario_count}|${feature_file}")
done

sorted_feature_files=()
while IFS= read -r weighted_entry; do
  [[ -z "$weighted_entry" ]] && continue
  sorted_feature_files+=("${weighted_entry#*|}")
done < <(printf '%s\n' "${weighted_feature_entries[@]}" | sort -t'|' -k1,1nr -k2,2)

declare -a shard_weights
declare -a shard_feature_lists
for (( shard=1; shard<=total_shards; shard++ )); do
  shard_weights[$shard]=0
  shard_feature_lists[$shard]=""
done

for feature_file in "${sorted_feature_files[@]}"; do
  scenario_count=$(grep -Ec '^[[:space:]]*Scenario( Outline)?:' "$feature_file")
  (( scenario_count == 0 )) && scenario_count=1

  target_shard=1
  min_weight=${shard_weights[1]}
  for (( shard=2; shard<=total_shards; shard++ )); do
    if (( shard_weights[$shard] < min_weight )); then
      target_shard=$shard
      min_weight=${shard_weights[$shard]}
    fi
  done

  shard_feature_lists[$target_shard]+="${feature_file}"$'\n'
  shard_weights[$target_shard]=$(( shard_weights[$target_shard] + scenario_count ))
done

selected=()
while IFS= read -r feature_file; do
  [[ -n "$feature_file" ]] && selected+=("$feature_file")
done <<< "${shard_feature_lists[$shard_index]}"

if (( ${#selected[@]} == 0 )); then
  # Fallback to a stable feature to avoid empty test invocations.
  echo "${feature_files[0]}"
  exit 0
fi

printf '%s ' "${selected[@]}"
echo
