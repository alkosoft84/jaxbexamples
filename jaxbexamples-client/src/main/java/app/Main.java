package app;


import alkosoft.com.pl.v1_0.PaymentCard;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Month;
import java.util.GregorianCalendar;

public class Main {

    public static void main(String[] args) {
        JAXBProxy jaxbProxy = new JAXBProxy();
        String request = handleRequest(jaxbProxy);
        PaymentCard paymentCard;
        paymentCard = (PaymentCard) jaxbProxy.parseXmlToJavaObj(request);
        System.out.println(paymentCard.getExpiryMonth());

    }

    private static String handleRequest(JAXBProxy jaxbProxy) {
        PaymentCard paymentCard = getPaymentCard();
        String xmlOutput = jaxbProxy.parseJavaObjToXml(paymentCard);
        System.out.println(xmlOutput);
        return xmlOutput;
    }

    private static PaymentCard getPaymentCard() {
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardNumber(2);
        paymentCard.setCardType("cardType");
        paymentCard.setExpiryMonth(Month.JULY);
        paymentCard.setExpiryYear(createGregorianCalendarDate());
        paymentCard.setPaymentType("paymentType");
        paymentCard.setMasked(true);
        return paymentCard;
    }

    private static XMLGregorianCalendar createGregorianCalendarDate() {
        GregorianCalendar c = new GregorianCalendar();
        c.set(2017, 12, 05);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException("Problem with add schema to unmarshaller" + e);
        }
    }

    private static String getValidXmlForSchema() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<PaymentCard xmlns=\"http://pl.com.alkosoft/v1_0\">\n" +
                "    <CardNumber>2</CardNumber>\n" +
                "    <PaymentType>paymentType</PaymentType>\n" +
                "    <CardType>cardType</CardType>\n" +
                "    <ExpiryMonth>--01</ExpiryMonth>\n" +
                "    <ExpiryYear>2018</ExpiryYear>\n" +
                "    <Masked>true</Masked>\n" +
                "</PaymentCard>";
    }
}
