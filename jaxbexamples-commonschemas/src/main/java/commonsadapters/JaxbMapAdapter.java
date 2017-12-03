package commonsadapters;

import jaxbclasses.jaxbhashmapimpl.Texture;
import jaxbclasses.jaxbhashmapimpl.Textures;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public final class JaxbMapAdapter extends XmlAdapter<Textures,Map<String, String>> {

    @Override
    public Textures marshal(Map<String, String> map) throws Exception {
        Textures myMapType = new Textures();
        for(Map.Entry<String, String> entry : map.entrySet()) {
            Texture texture =
                    new Texture();
            texture.key = entry.getKey();
            texture.value = entry.getValue();
            myMapType.entry.add(texture);
        }
        return myMapType;
    }

    @Override
    public Map<String, String> unmarshal(Textures textures) throws Exception {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for(Texture myEntryType : textures.entry) {
            hashMap.put(myEntryType.key, myEntryType.value);
        }
        return hashMap;
    }

}
