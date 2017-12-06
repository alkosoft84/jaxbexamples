package jaxbclasses.moxy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Engine {
    private String type;

    public Engine() {
    }

    public Engine(String type) {
        this.type = type;
    }
}
