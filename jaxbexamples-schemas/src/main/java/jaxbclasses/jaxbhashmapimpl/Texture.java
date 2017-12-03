package jaxbclasses.jaxbhashmapimpl;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Texture {
    @XmlAttribute
    public String key;

    @XmlValue
    public String value;
}
