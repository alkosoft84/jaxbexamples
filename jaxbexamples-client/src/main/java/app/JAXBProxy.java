package app;

import alkosoft.com.pl.v1_0.ObjectFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

public class JAXBProxy {

    private  JAXBContext jaxbContext;
    private static final String PACKAGE_NAME = "alkosoft.com.pl.v1_0";

    public JAXBProxy() {
        try {
            this.jaxbContext = JAXBContext.newInstance(PACKAGE_NAME, ObjectFactory.class.getClassLoader());
        } catch (JAXBException e) {
            throw new RuntimeException("JaxbContext initialization failed" + e);
        }
    }

    public JAXBProxy(JAXBContext jaxbContext) {
        this.jaxbContext = jaxbContext;

    }

    public Object parseXmlToJavaObj(final String xmlRequest) {
        try {
            return getXmlUnMarshaller().unmarshal(new StringReader(xmlRequest));
        } catch (JAXBException e) {
            throw new IllegalArgumentException("Unmarshalling error. You can't unmarshall xml: " + xmlRequest + " details: " + e);
        }
    }

    public String parseJavaObjToXml(final Object object) {
        StringWriter sw = new StringWriter();
        try {
            getXmlMarshaller().marshal(object, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new IllegalArgumentException("Marshalling error. You can't marshall object: " + object + " details: " + e);
        }
    }

    private Marshaller getXmlMarshaller() {
        try {
            Marshaller marshaller = this.jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            return marshaller;

        } catch (JAXBException e) {
            throw new RuntimeException("Jaxb marshaller initialization failed" + e);
        }
    }

    private Unmarshaller getXmlUnMarshaller() {
        try {
            Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
            makeUnmarshallerSchemaAware(unmarshaller);
            return unmarshaller;

        } catch (JAXBException e) {
            throw new RuntimeException("Jaxb unmarshaller initialization failed" + e);
        }
    }

    private void makeUnmarshallerSchemaAware(Unmarshaller unmarshaller) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(getClass().getClassLoader().getResource("schema/creditcard.xsd").getFile()));
            unmarshaller.setSchema(schema);
        } catch (SAXException e) {
            throw new RuntimeException("Problem with add schema to unmarshaller" + e);
        }

    }
}
