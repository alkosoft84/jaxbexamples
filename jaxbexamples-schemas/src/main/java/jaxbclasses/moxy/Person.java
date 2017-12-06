package jaxbclasses.moxy;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"firstname", "lastname", "engineModel"})
public class Person {
    private String firstname;
    private String lastname;

    @XmlPath("car/engine/type/text()")
    private String engineModel;

    public Person() {
    }

    public Person(String firstname, String lastname, String engineModel) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.engineModel = engineModel;
    }

    public String getEngineModel() {
        return engineModel;
    }
}
