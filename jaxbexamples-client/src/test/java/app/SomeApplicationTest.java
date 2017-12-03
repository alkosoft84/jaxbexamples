package app;

import alkosoft.com.pl.hashmap.Game;
import app.utils.JAXBUtils;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SomeApplicationTest {
    private static final String PACKAGE_NAME = "alkosoft.com.pl.hashmap:jaxbclasses.jaxbhashmapimpl";
    private JAXBContext jaxbContext;

    @Test
    public void marshall_using_standard_implementation() throws JAXBException {
        //given
        jaxbContext = initiateJaxContextBasedOnPackage(PACKAGE_NAME);
        //jaxbContext = initiateJaxContextBasedOnClass(Game.class);
        JAXBUtils.showJaxbImplementation(jaxbContext);
        Marshaller marshaller = getXmlMarshaller(jaxbContext);
        StringWriter stringWriter = new StringWriter();
        Map<String,String> textures = new HashMap<>();
        textures.put("blue","pathToBlue");
        textures.put("green","pathToGreen");

        Game game = new Game();
        game.setName("test name");
        game.setTextures(textures);
        //when
        marshaller.marshal(game, stringWriter);
        System.out.println(stringWriter.toString());
    }

    @Test
    public void unmarshall_using_standard_implementation() throws JAXBException {
        //given
        jaxbContext = initiateJaxContextBasedOnPackage(PACKAGE_NAME);
        //jaxbContext = initiateJaxContextBasedOnClass(Game.class);
        JAXBUtils.showJaxbImplementation(jaxbContext);
        //generateSchemaFromJavaClass(Game.class);
        Unmarshaller unmarshaller = getXmlUnMarshaller(jaxbContext, false);
        //when
        Game game = (Game) unmarshaller.unmarshal(new File(getClass().getClassLoader()
                .getResource("game_finished_rq_copied_from_marshaller.xml").getFile()));
        //then
        assertThat(game).isInstanceOf(Game.class);
    }




}
