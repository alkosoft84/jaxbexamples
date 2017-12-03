package app;

import alkosoft.com.pl.v1_0.ObjectFactory;
import app.utils.JAXBUtils;
import jaxbclasses.Game;
import org.junit.Ignore;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.io.StringWriter;

import static app.utils.JAXBUtils.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SomeApplicationTest {

    private static final String PACKAGE_NAME = "alkosoft.com.pl.v1_0";
    private JAXBContext jaxbContext;


    @Test
    public void marshall_using_standard_implementation() throws JAXBException {
        //given
        jaxbContext = initiateJaxContextBasedOnClass(Game.class);
        showJaxbImplementation(jaxbContext);
        Marshaller marshaller = getXmlMarshaller(jaxbContext);
        StringWriter stringWriter = new StringWriter();
        //when
        marshaller.marshal(new Game("test name",null), stringWriter);
        System.out.println(stringWriter.toString());
        //then
    }
    @Test
    @Ignore
    public void unmarshall_using_standard_implementation() throws JAXBException {
        //given
        jaxbContext = initiateJaxContextBasedOnClass(Game.class);
        showJaxbImplementation(jaxbContext);
        Unmarshaller unmarshaller = getXmlUnMarshaller(jaxbContext, false);
        //when
        Object game = unmarshaller.unmarshal(new File(getClass().getClassLoader()
                .getResource("game_basic_rq.xml").getFile()));
        //then
        assertThat(game).isInstanceOf(Game.class);
    }




}
