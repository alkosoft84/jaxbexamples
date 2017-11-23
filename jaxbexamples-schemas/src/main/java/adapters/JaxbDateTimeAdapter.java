package adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Objects;

public class JaxbDateTimeAdapter extends XmlAdapter<String, LocalDate>
{
    private static final String DATE_INPUT_FORMATS = "[yyyy-MM-dd]"
            + "[yyyy-MM-dd]" + "[yyyy/MM/dd]";

    private static final String XML_GREGORIAN_CALENDAR_OUTPUT_FORMAT = "yyyy-MM-dd";
    private static final String GMONTH_OUTPUT_FORMAT = "--MM";

    private DateTimeFormatter inputDateFormatter;
    private DateTimeFormatter outputDateFormatter;

    /**
     * Constructor
     */
    public JaxbDateTimeAdapter()
    {
        this.inputDateFormatter = getDateTimeFormatter(DATE_INPUT_FORMATS);
        this.outputDateFormatter = getDateTimeFormatter(XML_GREGORIAN_CALENDAR_OUTPUT_FORMAT);
    }

    @Override
    public LocalDate unmarshal(String inputDate) throws Exception
    {
        if (inputDate==null || inputDate.equals(""))
        {
            return null;
        }
        return LocalDate.parse(inputDate, inputDateFormatter);
    }

    @Override
    public String marshal(LocalDate inputDate) throws Exception
    {
        if (Objects.isNull(inputDate))
        {
            return null;
        }
        return inputDate.format(outputDateFormatter);
    }

    private DateTimeFormatter getDateTimeFormatter(String pattern)
    {
        DateTimeFormatterBuilder formatterBuilder = new DateTimeFormatterBuilder();
        formatterBuilder.parseCaseInsensitive();
        formatterBuilder.append(DateTimeFormatter.ofPattern(pattern));
        DateTimeFormatter formatter = formatterBuilder.toFormatter(Locale.ENGLISH);
        return formatter;
    }

}