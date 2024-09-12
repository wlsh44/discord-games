package wlsh.project.discordgames.catchpokemon.infra.crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import wlsh.project.discordgames.catchposkill.domain.PoSkill;
import wlsh.project.discordgames.common.infra.csv.CSVService;
import wlsh.project.discordgames.catchposkill.infra.csv.PoSkillFormatter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SkillCrawler {

    public void crawl1() throws IOException {
        String html = getHtml("database/csv/skill-link");
        Document parse = Jsoup.parse(html);

        Elements rows = parse.select("tr");
        List<PoSkill> poSkillList = new ArrayList<>();
        for (int i = 1; i < rows.size(); i++) {
            Elements td = rows.get(i).select("td");
            String id = td.get(0).text();
            String link = td.get(1).select("a").attr("href");

            PoSkill poSkill = getPoSkill(link, id);
            poSkillList.add(poSkill);
        }
        CSVService csvService = new CSVService();
        csvService.upload("po-skill.csv", new PoSkillFormatter(), poSkillList);
    }

    public void crawl2() throws IOException {
        String html = getHtml("database/csv/skill1");
        Document parse = Jsoup.parse(html);

        Elements rows = parse.select("tr");
        for (int i = 1; i < rows.size(); i++) {
            Elements td = rows.get(i).select("td");
            String attack = td.get(1).text();
            String damage = td.get(2).text();
            String accuracy = td.get(3).text();
            String pp = td.get(4).text();

            String link = td.get(0).select("a").attr("href");
            Document document = Jsoup.connect(link).get();


        }
    }

    private static PoSkill getPoSkill(String link, String id) throws IOException {
        try {
            Document skillDoc = Jsoup.connect("https://pokemon.fandom.com" + link).get();
            Elements skillTable = skillDoc.select("tbody").getFirst().select("table").select("tr").select("td");

            Element nameElement = skillTable.get(0);
            String koName = nameElement.select("strong").text();
            String enName = nameElement.childNode(4).toString().trim();

            String type = skillTable.get(3).select("span.text-white").text();

            String attack = skillTable.get(5).select("span.text-white").text();

            String description = skillTable.get(7).text();

            String damage = skillTable.get(10).text();

            String accuracy = skillTable.get(12).text();

            String pp = skillTable.get(14).childNode(0).toString().trim();

            String generation = skillTable.get(30).text().trim();

//            Element skillTable = skillTable.select("tr").getFirst();
            PoSkill poSkill = new PoSkill(
                    id,
                    koName,
                    enName,
                    type,
                    attack,
                    description,
                    damage,
                    accuracy,
                    pp,
                    generation
            );
            return poSkill;
        } catch (Exception e) {
            System.out.println("id = " + id);
            return new PoSkill(id, null, null, null, null, null, null, null, null, null);
        }
    }

    private static String getHtml(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
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
