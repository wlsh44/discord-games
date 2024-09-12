package wlsh.project.discordgames.common.infra.csv;

import java.util.List;

public interface CSVFormatter<T> {
    List<String[]> format(List<T> data);
}
