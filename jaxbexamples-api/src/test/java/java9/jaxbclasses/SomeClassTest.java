package java9.jaxbclasses;







import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;


public class SomeClassTest {

    @Test
    public void basic_run_with_jaxb() throws Exception {
        //given
        JAXBContext jaxbContext = JAXBContext.newInstance(SomeClass.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        SomeClass someClass = new SomeClass("test class");
        //when
        marshaller.marshal(someClass,System.out);
    }

}