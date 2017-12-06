package app;

import alkosoft.com.pl.hashmap.Game;
import app.utils.JAXBUtils;
import jaxbclasses.moxy.Person;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static app.utils.JAXBUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.entry;


public class SomeApplicationTest {
    private static final String PACKAGE_NAME = "alkosoft.com.pl.hashmap:jaxbclasses.jaxbhashmapimpl";
    private JAXBContext jaxbContext;

    @Test
    public void marshall_based_on_annotated_class() throws JAXBException {
        //given
        jaxbContext = initiateJaxContextBasedOnClass(jaxbclasses.Game.class);
        JAXBUtils.showJaxbImplementation(jaxbContext);
        Marshaller marshaller = getXmlMarshaller(jaxbContext);
        StringWriter stringWriter = new StringWriter();
        Map<String, String> textures = fillTextures();
        jaxbclasses.Game game = new jaxbclasses.Game("test name", textures);
        //when
        marshaller.marshal(game, stringWriter);
        System.out.println(stringWriter.toString());
    }

    @Test
    public void marshall_based_on_generated_class() throws JAXBException {
        //given
        jaxbContext = initiateJaxContextBasedOnPackage(PACKAGE_NAME);
        Marshaller marshaller = getXmlMarshaller(jaxbContext);
        StringWriter stringWriter = new StringWriter();
        Map<String, String> textures = fillTextures();
        Game game = new Game();
        game.setName("test name");
        game.setTextures(textures);
        //when
        marshaller.marshal(game, stringWriter);
        System.out.println(stringWriter.toString());
    }

    @Test
    public void unmarshall_based_on_annotated_class() throws JAXBException {
        //given
        jaxbContext = initiateJaxContextBasedOnClass(jaxbclasses.Game.class);
        JAXBUtils.showJaxbImplementation(jaxbContext);
        Unmarshaller unmarshaller = getXmlUnMarshaller(jaxbContext, false);
        //when
        jaxbclasses.Game game = (jaxbclasses.Game) unmarshaller.unmarshal(new File(getClass().getClassLoader()
                .getResource("game_rq.xml").getFile()));
        //then
        assertThat(game).isInstanceOf(jaxbclasses.Game.class);
        assertThat(game.getTextures().entrySet())
                .contains(entry("blue", "pathToBlue"),
                        entry("green", "pathToGreen")
                );
    }

    @Test
    public void unmarshall_based_on_generated_class() throws JAXBException {
        //given
        jaxbContext = initiateJaxContextBasedOnPackage(PACKAGE_NAME);
        JAXBUtils.showJaxbImplementation(jaxbContext);
        Unmarshaller unmarshaller = getXmlUnMarshaller(jaxbContext, false);
        //when
        Game game = (Game) unmarshaller.unmarshal(new File(getClass().getClassLoader()
                .getResource("game_rq_copied_from_marshaller.xml").getFile()));
        //then
        assertThat(game).isInstanceOf(Game.class);
        assertThat(game.getTextures())
                .contains(entry("blue", "pathToBlue"),
                        entry("green", "pathToGreen")
                );
    }

    @Test
    public void marshall_annotated_structure() throws JAXBException {
        //given
        jaxbContext = initiateJaxContextBasedOnClass(Person.class);
        JAXBUtils.showJaxbImplementation(jaxbContext);
        Marshaller marshaller = getXmlMarshaller(jaxbContext);
        StringWriter stringWriter = new StringWriter();
        Person person = new Person("Mariusz", "Wrobel", "V6");
        //when
        marshaller.marshal(person, stringWriter);
        System.out.println(stringWriter.toString());
    }

    @Test
    public void unmarshall_annotated_structure() throws JAXBException {
        //given
        jaxbContext = initiateJaxContextBasedOnClass(Person.class);
        JAXBUtils.showJaxbImplementation(jaxbContext);
        Unmarshaller unmarshaller = getXmlUnMarshaller(jaxbContext, false);
        //when
        Person person = (Person) unmarshaller.unmarshal(new File(getClass().getClassLoader()
                .getResource("person_rq.xml").getFile()));
        //then
        assertThat(person).isInstanceOf(Person.class);
        assertThat(person.getEngineModel()).isEqualToIgnoringCase("V6");
    }

    private Map<String, String> fillTextures() {
        Map<String, String> textures = new HashMap<>();
        textures.put("blue", "pathToBlue");
        textures.put("green", "pathToGreen");
        return textures;
    }
}
