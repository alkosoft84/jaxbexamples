package adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Objects;

public class JaxbGMonthAdapter extends XmlAdapter<String, Month>
{
    private static final String INPUT_FORMATS = "[yyyy-MM-dd]"
            + "[yyyy-MM-dd]" + "[yyyy/MM/dd]";

    private static final String OUTPUT_FORMAT = "--MM";

    private DateTimeFormatter inputFormatter;
    private DateTimeFormatter outputFormatter;

    /**
     * Constructor
     */
    public JaxbGMonthAdapter()
    {
        this.inputFormatter = getDateTimeFormatter(INPUT_FORMATS);
        this.outputFormatter = getDateTimeFormatter(OUTPUT_FORMAT);
    }

    @Override
    public Month unmarshal(String inputDate) throws Exception
    {
        if (inputDate==null || inputDate.equals(""))
        {
            return null;
        }
        return Month.of(Integer.parseInt(inputDate.replace("--","")));
    }

    @Override
    public String marshal(Month input) throws Exception {
        if (Objects.isNull(input))
        {
            return null;
        }
        LocalDate temp = LocalDate.of(1970, input, 1);
        return temp.format(outputFormatter);
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