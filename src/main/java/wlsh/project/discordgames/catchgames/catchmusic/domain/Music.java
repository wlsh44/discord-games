package wlsh.project.discordgames.catchgames.catchmusic.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Music(
        String name,
        String secondName,
        Artist artist,
        Album album,
        int popularity,
        String releaseDate
) {

    public static Music of(String title, String artistName) {
        String secondName = extractSecondName(title);
        return new Music(title, secondName, new Artist(artistName, null), null, 0, null);
    }

    public static Music of(String title, String secondName, Artist artist, Album album, int popularity, String releaseDate) {
        return new Music(title, secondName, artist, album, popularity, releaseDate);
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
                insideParentheses.contains("from") || insideParentheses.contains("remaster") || insideParentheses.contains("cf") ||
                    insideParentheses.contains("ver")) {
                return matcher.group(1).trim();
            }
        }
        return null;
    }
}
