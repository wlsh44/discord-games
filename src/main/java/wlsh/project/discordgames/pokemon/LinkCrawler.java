package wlsh.project.discordgames.pokemon;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import wlsh.project.discordgames.pokemon.infra.csv.CSVService;
import wlsh.project.discordgames.pokemon.infra.csv.PokemonLinkFormatter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LinkCrawler {

    public void crawl() throws IOException {
        for (int i = 1; i <= 9; i++) {
            extracted(String.valueOf(i));
        }
    }

    private void extracted(String generation) throws IOException {
        String html = getHtml(generation);
        Document doc = Jsoup.parse(html);

        // 모든 <a> 태그에서 href 속성 추출
        Elements rows = doc.select("tr");

        // 이미지 링크를 저장할 리스트
        List<PokemonLink> imageLinks = new ArrayList<>();

        String number = null;
        // 각 <tr> 태그를 반복합니다.
        for (Element row : rows) {
            // 첫 번째 <td> 태그를 선택합니다.
            Element firstTd = row.select("td").first();
            Elements select = row.select("td");

            try {
                select.get(1).text();
            } catch (Exception e) {
                System.out.println("firstTd = " + firstTd);
            }

            String currentNumber = select.get(1).text();
            // 첫 번째 <td>에 값이 있는지 확인합니다.
            if (!currentNumber.equals(number)) {
                // 첫 번째 <td>에 값이 있는 경우, 이미지 링크를 추출합니다.
                Elements links = row.select("a:has(img)");
                String href = links.get(0).attr("href");
                imageLinks.add(new PokemonLink(generation, href));
                number = currentNumber;
            }
        }


        // 결과 출력
//        System.out.println("Parsed hrefs: " + linkList);
        CSVService csvService = new CSVService();
        csvService.upload("pokemon-link", new PokemonLinkFormatter(), imageLinks);
    }

    private static String getHtml(String generation) throws IOException {
        FileInputStream fis = new FileInputStream("database/csv/generation%s".formatted(generation));
        String html = null;
        try (BufferedReader br =
                     new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            html = sb.toString();
        }
        return html;
    }
}
