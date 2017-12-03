package jaxbclasses;

import commonsadapters.JaxbMapAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public final class Game {
    private String name;
    @XmlJavaTypeAdapter(JaxbMapAdapter.class)
    private Map<String,String> textures;

    //for JAXB purposes
    protected Game(){}

    public Game(String name, Map<String, String> textures) {
        this.name = name;
        this.textures = textures;
    }

    public Map<String, String> getTextures() {
        return textures;
    }
}
