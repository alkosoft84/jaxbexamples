package app;

import alkosoft.com.pl.hashmap.Game;
import app.utils.JAXBUtils;
import jaxbclasses.documents.Document;
import jaxbclasses.moxy.Person;
import org.junit.Test;
import org.xml.sax.*;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
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

    @Test
    public void marshall_document_with_map() throws JAXBException {
        //given
        jaxbContext = initiateJaxContextBasedOnClass(Document.class);
        JAXBUtils.showJaxbImplementation(jaxbContext);
        Marshaller marshaller = getXmlMarshaller(jaxbContext);
        marshaller.setProperty("eclipselink.media-type", "application/json");
        StringWriter stringWriter = new StringWriter();
        Map<String, String> parsers = fillParsers();
        Document document = new Document("Word", "doc", parsers);
        //when
        marshaller.marshal(document, stringWriter);
        System.out.println(stringWriter.toString());
    }

    @Test
    public void unmarshall_document_with_map() throws JAXBException {
        //given
        jaxbContext = initiateJaxContextBasedOnClass(Document.class);
        JAXBUtils.showJaxbImplementation(jaxbContext);
        Unmarshaller unmarshaller = getXmlUnMarshaller(jaxbContext, false);
/*        unmarshaller.setProperty("eclipselink.media-type", "application/json");*/
        //when
        Document document = (Document) unmarshaller.unmarshal(new File(getClass().getClassLoader()
                .getResource("documents/document_rq.xml").getFile()));
        //then
        assertThat(document).isInstanceOf(Document.class);
        assertThat(document.getParsers())
                .contains(entry("json", "JsonWordParser"),
                        entry("xml", "XmlWordParser")
                );
    }

    @Test
    public void unmarshall_by_default_sax_jaxb() throws JAXBException {
        jaxbContext = initiateJaxContextBasedOnClass(Document.class);
        JAXBUtils.showJaxbImplementation(jaxbContext);
        Unmarshaller unmarshaller = getXmlUnMarshaller(jaxbContext, false);
        Document document = (Document) unmarshaller.unmarshal(new File(getClass().getClassLoader()
                .getResource("documents/document_rq.xml").getFile()));
    }

    @Test
    public void unmarshall_by_different_SAX_parser() throws JAXBException, XMLStreamException, SAXException, ParserConfigurationException, FileNotFoundException {
        //jaxb context
        jaxbContext = initiateJaxContextBasedOnClass(Document.class);
        JAXBUtils.showJaxbImplementation(jaxbContext);
        Unmarshaller unmarshaller = getXmlUnMarshaller(jaxbContext, false);
        //staxParser
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        spf.setFeature("http://xml.org/sax/features/validation", false);
        XMLReader xmlReader = spf.newSAXParser().getXMLReader();
        InputSource inputSource = new InputSource(new FileReader(getClass().getClassLoader()
                .getResource("documents/document_rq.xml").getFile()));
        SAXSource source = new SAXSource(xmlReader, inputSource);

        unmarshaller.unmarshal(source);
    }

    @Test
    public void unmarshall_by_StAX() throws JAXBException, XMLStreamException {
        //jaxb context
        jaxbContext = initiateJaxContextBasedOnClass(Document.class);
        JAXBUtils.showJaxbImplementation(jaxbContext);
        Unmarshaller unmarshaller = getXmlUnMarshaller(jaxbContext, false);
        //staxParser
        XMLInputFactory xif = XMLInputFactory.newFactory();
        StreamSource xml = new StreamSource(new File(getClass().getClassLoader()
                .getResource("documents/document_rq.xml").getFile()));
        XMLStreamReader xsr = xif.createXMLStreamReader(xml);
        while (xsr.hasNext()) {
            unmarshaller.unmarshal(xsr, Document.class);
        }
    }

    @Test
    public void unmarshall_from_DOM_jaxb() throws JAXBException, ParserConfigurationException, IOException, SAXException {
        jaxbContext = initiateJaxContextBasedOnClass(Document.class);
        JAXBUtils.showJaxbImplementation(jaxbContext);
        Unmarshaller unmarshaller = getXmlUnMarshaller(jaxbContext, false);
        //DOM parser
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        org.w3c.dom.Document document = db.parse(new File(getClass().getClassLoader()
                .getResource("documents/document_rq.xml").getFile()));
        Document document2 = (Document) unmarshaller.unmarshal(document);
    }

    private Map<String, String> fillTextures() {
        Map<String, String> textures = new HashMap<>();
        textures.put("blue", "pathToBlue");
        textures.put("green", "pathToGreen");
        return textures;
    }

    private Map<String, String> fillParsers() {
        Map<String, String> parsers = new HashMap<>();
        parsers.put("json", "JsonWordParser");
        parsers.put("xml", "XmlWordParser");
        return parsers;
    }
}
