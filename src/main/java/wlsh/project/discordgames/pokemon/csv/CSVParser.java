package wlsh.project.discordgames.pokemon.csv;

public interface CSVParser<T> {
    T parse(String[] content);
}
