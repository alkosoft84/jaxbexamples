package commonsadapters;
import org.eclipse.persistence.oxm.annotations.XmlVariableNode;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class JaxbDocumentMapAdapter extends XmlAdapter<JaxbDocumentMapAdapter.Parsers,Map<String, String>> {

    public static class Parsers {
        @XmlVariableNode("key")
        public List<Parser> entry = new ArrayList<>();
    }

    public static class Parser {
        @XmlTransient
        public String key;
        @XmlValue
        public String value;
    }
    @Override
    public Parsers marshal(Map<String, String> map) throws Exception {
        Parsers myMapType = new Parsers();
        for(Map.Entry<String, String> entry : map.entrySet()) {
            Parser texture =
                    new Parser();
            texture.key = entry.getKey();
            texture.value = entry.getValue();
            myMapType.entry.add(texture);
        }
        return myMapType;
    }

    @Override
    public Map<String, String> unmarshal(Parsers textures) throws Exception {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for(Parser myEntryType : textures.entry) {
            hashMap.put(myEntryType.key, myEntryType.value);
        }
        return hashMap;
    }

}
