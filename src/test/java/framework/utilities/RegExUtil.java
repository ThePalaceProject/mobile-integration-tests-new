package framework.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExUtil {

    public static String deleteBracketsFromText(String text) {
        text = text.replaceAll("\\(", "");
        text = text.replaceAll("\\)", "");
        return text;
    }

    public static String getStringFromFirstGroup(String text, String regex) {
        Matcher matcher = getMatcher(text, regex);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalStateException(String.format("No match found for text '%s' with regex '%s'", text, regex));
        }
    }

    public static Matcher getMatcher(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(text);
    }

    public static String getStringFromThirdGroup(String text, String regex) {
        Matcher matcher = getMatcher(text, regex);
        if (matcher.find()) {
            return matcher.group(3);
        } else {
            throw new IllegalStateException(String.format("No match found for text '%s' with regex '%s'", text, regex));
        }
    }
}
