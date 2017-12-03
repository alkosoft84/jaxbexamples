package app.utils;

import alkosoft.com.pl.v1_0.ObjectFactory;
import org.xml.sax.SAXException;
import tools.MySchemaOutputResolver;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

public class JAXBUtils {

    public static JAXBContext initiateJaxContextBasedOnPackage(final String packageName) throws JAXBException {
        try {
            return JAXBContext.newInstance(packageName, ObjectFactory.class.getClassLoader());
        } catch (JAXBException e) {
            throw new RuntimeException("JaxbContext initialization failed" + e);
        }
    }

    public static JAXBContext initiateJaxContextBasedOnClass(final Class className) throws JAXBException {
        try {
            return JAXBContext.newInstance(className);
        } catch (JAXBException e) {
            throw new RuntimeException("JaxbContext initialization failed" + e);
        }
    }

    public static Marshaller getXmlMarshaller(final JAXBContext jaxbContext) {
        try {
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            return marshaller;

        } catch (JAXBException e) {
            throw new RuntimeException("Jaxb marshaller initialization failed" + e);
        }
    }

    public static Unmarshaller getXmlUnMarshaller(final JAXBContext jaxbContext, boolean schemaAware) {
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            if (schemaAware) {
                makeUnmarshallerSchemaAware(unmarshaller, JAXBUtils.class);
            }
            return unmarshaller;

        } catch (JAXBException e) {
            throw new RuntimeException("Jaxb unmarshaller initialization failed" + e);
        }
    }

    public static void generateSchemaFromJavaClass(final Class javaClass) {
        MySchemaOutputResolver mySchemaOutputResolver = new MySchemaOutputResolver();
        try {
            JAXBContext jaxbContext = initiateJaxContextBasedOnClass(javaClass);
            jaxbContext.generateSchema(mySchemaOutputResolver);
        } catch (JAXBException | IOException e) {
            throw new RuntimeException("Problem with generation schema from: " + javaClass.toString() + " details: " + e);
        }
    }

    public static void showJaxbImplementation(final JAXBContext jaxbContext){
        System.out.println(jaxbContext.toString());
    }

    private static void makeUnmarshallerSchemaAware(final Unmarshaller unmarshaller, final Class javaClass) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(javaClass.getClassLoader().getResource("schema/creditcard.xsd").getFile()));
            unmarshaller.setSchema(schema);
        } catch (SAXException e) {
            throw new RuntimeException("Problem with add schema to unmarshaller" + e);
        }
    }


}
