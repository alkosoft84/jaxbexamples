package jaxbclasses.documents;

import commonsadapters.JaxbDocumentMapAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Document {
    private String name;
    private String type;
    @XmlJavaTypeAdapter(JaxbDocumentMapAdapter.class)
    private Map<String,String> parsers;

    //for JAXB purposes
    protected Document(){}

    public Document(String name, String type, Map<String, String> parsers) {
        this.name = name;
        this.type = type;
        this.parsers = parsers;
    }

    public Map<String, String> getParsers() {
        return parsers;
    }
}
