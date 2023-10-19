package framework.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExUtil {

    private RegExUtil() {}

    public static String deleteBracketsFromText(String text) {
        text = text.replace("\\(", "");
        text = text.replace("\\)", "");
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
