package jaxbclasses.moxy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Car {
    private String model;
    private Engine engine;

    public Car() {
    }

    public Car(String model, Engine engine) {
        this.model = model;
        this.engine = engine;
    }
}
