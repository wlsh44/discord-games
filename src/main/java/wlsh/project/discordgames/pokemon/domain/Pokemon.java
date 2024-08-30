package wlsh.project.discordgames.pokemon.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class Pokemon {

    private int index;
    private String ko;
    private String en;
    private String image;
    private int generation;
    private List<String> types;

    public void changeName(String ko, String en) {
        this.ko = ko;
        this.en = en;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
