package wlsh.project.discordgames.pokemon.infra.csv;

public interface CSVParser<T> {
    T parse(String[] content);
}
