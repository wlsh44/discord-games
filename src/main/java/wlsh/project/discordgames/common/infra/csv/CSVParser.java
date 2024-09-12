package wlsh.project.discordgames.common.infra.csv;

public interface CSVParser<T> {
    T parse(String[] content);
}
