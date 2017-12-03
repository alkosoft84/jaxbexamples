package jaxbclasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Game {
    private String name;
    private Map<String,String> textures;

    //for JAXB purposes
    protected Game(){}

    public Game(String name, Map<String, String> textures) {
        this.name = name;
        this.textures = textures;
    }
}
