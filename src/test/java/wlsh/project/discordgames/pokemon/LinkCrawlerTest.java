package wlsh.project.discordgames.pokemon;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;
import wlsh.project.discordgames.pokemon.infra.csv.CSVService;
import wlsh.project.discordgames.pokemon.infra.csv.PokemonFormatter;
import wlsh.project.discordgames.pokemon.infra.csv.PokemonLinkParser;
import wlsh.project.discordgames.pokemon.domain.Pokemon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class LinkCrawlerTest {

    CSVService csvService = new CSVService();

    @Test
    void test() throws Exception {
        //given
        LinkCrawler linkCrawler = new LinkCrawler();

        //when
        linkCrawler.crawl();

        //then

    }

    @Test
    void te1() throws Exception {
        //given
//        Document document = Jsoup.connect("https://pokemon.fandom.com/ko/wiki/%EC%9D%B4%EC%83%81%ED%95%B4%EC%94%A8_(%ED%8F%AC%EC%BC%93%EB%AA%AC)")
//                .get();
        int i = 0;
        List<PokemonLink> linkList1 = csvService.readData("pokemon-link", new PokemonLinkParser());
        List<PokemonLink> linkList = linkList1.subList(421, linkList1.size());
//        List<PokemonLink> linkList = List.of(new PokemonLink("2", "/ko/wiki/%EB%B8%94%EB%A3%A8_(%ED%8F%AC%EC%BC%93%EB%AA%AC)"));
        for (PokemonLink pokemonLink : linkList) {
            i++;
            save(pokemonLink);
        }

//if (x = "다급", if(y >= 14, 320000, z), if(y >= 18, 320000, z))
    }

    private void save(PokemonLink pokemonLink) throws IOException {
        Document document = Jsoup.connect("https://pokemon.fandom.com" + pokemonLink.link())
                .get();

        //when
        Elements select3 = document.select("div#mw-content-text");
        Elements otherForm = select3.select("div#pokemon");
        Elements pokemonInfoBox = select3.select("div.infobox-pokemon");

        List<Pokemon> pokemonList = new ArrayList<>();
        int index = 0;
        if (otherForm.isEmpty()) {
            pokemonList.add(getPokemonInfo(pokemonInfoBox.get(0), pokemonLink.generation()));
        } else {
            Elements toggle = document.select("table#pokemonToggle").select("tr");
            for (int i = 0; i < pokemonInfoBox.size(); i++) {
                String name = toggle.get(i + 1).text();
                System.out.println("name = " + name);
                Pokemon pokemonInfo = getPokemonInfo(pokemonInfoBox.get(i), pokemonLink.generation());
                if (name.startsWith("히스이")) {
                    pokemonInfo.changeName(name, "Hisui " + pokemonInfo.getEn());
                } else if (name.startsWith("가라르")) {
                    pokemonInfo.changeName(name, "Galar " + pokemonInfo.getEn());
                } else if (name.startsWith("알로라")) {
                    pokemonInfo.changeName(name, "Alola " + pokemonInfo.getEn());
                } else if (name.startsWith("거다이맥스")) {
                    pokemonInfo.changeName(name, "Dynamax " + pokemonInfo.getEn());
                } else if (name.startsWith("팔데아")) {
                    pokemonInfo.changeName(name, "Paldea " + pokemonInfo.getEn());
                } else if (name.startsWith("메가")) {
                    pokemonInfo.setIndex(index);
                } else {
                    index = pokemonInfo.getIndex();
                }
                pokemonList.add(pokemonInfo);
            }
        }

        csvService.upload("pokemon.csv", new PokemonFormatter(), pokemonList);
    }

    private Pokemon getPokemonInfo(Element pokemonInfoBox, String generation) {
        Elements head = pokemonInfoBox.select("div.head");
        String name = head.select("div.name").select("strong").text().trim();
        String enName = head.select("div.name-ja").get(1).select("span").text().trim();
        System.out.println("name = " + name);
        System.out.println("name = " + enName);

        String strong = head.select("div.index").select("strong").text();
        int index = 0;
        if (StringUtils.hasText(strong)) {
            String indexString = strong.substring(3);
            index = Integer.parseInt(indexString);
            System.out.println("index = " + indexString);
        }

        //then
        String image = pokemonInfoBox.select("div.image").select("a").attr("href");
        System.out.println("image = " + image);

        Elements infoTable = pokemonInfoBox.select("table");
        Elements types = infoTable.select("tr").get(1).select("td").get(0).select("a");
        List<String> typeList = new ArrayList<>();
        for (Element type : types) {
            String typeString = type.select("span").text();
            System.out.println("type = " + typeString);
            typeList.add(typeString);
        }

        return new Pokemon(index, name, enName, image, Integer.parseInt(generation), typeList);
    }
}
