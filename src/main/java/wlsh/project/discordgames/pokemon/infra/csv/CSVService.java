package wlsh.project.discordgames.pokemon.infra.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CSVService {

    private static final String CSV_PATH = "database/csv/%s";

    public <T> List<T> readData(String path, CSVParser<T> parser) {
        String content = getResourceContent(path);
        StringReader reader = new StringReader(content);

        try (CSVReader csvReader = new CSVReader(reader)) {
            return parseWithCsvReader(csvReader, parser);
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    private String getResourceContent(String filename) {
        FileSystemResource resource = new FileSystemResource(CSV_PATH.formatted(filename));
        try {
            return resource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> List<T> parseWithCsvReader(CSVReader csvReader, CSVParser<T> parser) throws IOException, CsvException {
        List<String[]> lines = csvReader.readAll();
        return lines.stream()
                .map(parser::parse)
                .toList();
    }

    public <T> void upload(String filename, CSVFormatter<T> formatter, List<T> data) throws IOException {
        FileSystemResource resource = new FileSystemResource(CSV_PATH.formatted(filename));
        File file = resource.getFile();
        createFileIfNotExist(file);
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(file, true))) {
            List<String[]> records = formatter.format(data);
            csvWriter.writeAll(records);
        }
    }

    private void createFileIfNotExist(File file) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }
}
