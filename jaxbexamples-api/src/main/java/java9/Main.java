package java9;

import java9.jaxbclasses.SomeClass;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Main {
    public static void main(String[] args) throws JAXBException {
        //run in application
        JAXBContext jaxbContext = JAXBContext.newInstance(SomeClass.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        SomeClass someClass = new SomeClass("some class");
        marshaller.marshal(someClass, System.out);
    }
}
