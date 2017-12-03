package jaxbclasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlRootElement
public final class WrongGame {
    private String name;
    private Map<String,String> textures;

    //for JAXB purposes
    protected WrongGame(){}

    public WrongGame(String name, Map<String, String> textures) {
        this.name = name;
        this.textures = textures;
    }
}
