package java9.jaxbclasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SomeClass {
    private String name;

    public SomeClass() {
    }

    public SomeClass(String name) {
        this.name = name;
    }
}
