package wlsh.project.discordgames.pokemon.csv;

import java.util.List;

public interface CSVFormatter<T> {
    List<String[]> format(List<T> data);
}
