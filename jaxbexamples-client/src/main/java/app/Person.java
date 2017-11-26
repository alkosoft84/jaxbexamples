package app;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "person")
public class Person {
    public String firstName;
    public String lastName;
    @XmlElementWrapper(name = "identities")
    public Set<String> identities = new HashSet<>();
}
