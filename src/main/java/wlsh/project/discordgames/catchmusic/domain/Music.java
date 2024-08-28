package wlsh.project.discordgames.catchmusic.domain;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Music(
        String name,
        String secondName,
        String artist
) {

    public static Music of(String title, String artist) {
        String secondName = extractSecondName(title);
        return new Music(title, secondName, artist);
    }

    private static String extractSecondName(String title) {
        // Regex pattern to match the last parentheses at the end of the string
        Pattern pattern = Pattern.compile(".*\\(([^)]+)\\)$");
        Matcher matcher = pattern.matcher(title);

        if (matcher.matches()) {
            String insideParentheses = matcher.group(1).trim().toLowerCase();
            // Check if the text inside the parentheses does not start with "feat", "song", "duet", etc.
            if (!(insideParentheses.contains("feat") || insideParentheses.contains("song") || insideParentheses.contains("duet") ||
                    insideParentheses.contains("ver") || insideParentheses.contains("version") || insideParentheses.contains("edit")) ||
                insideParentheses.contains("from")) {
                return matcher.group(1).trim();
            }
        }
        return null;
    }
}
