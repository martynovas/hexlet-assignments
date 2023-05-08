package exercise;

import java.util.stream.Collectors;
import java.util.Map;

// BEGIN
public abstract class Tag{
    private String name;
    private Map<String, String> attributes;

    Tag(String name, Map<String, String> attributes){
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    protected String attributesToString() {
        StringBuilder builder = new StringBuilder();
        attributes.forEach((k,v) -> builder.append(k+"=\""+v+"\" "));
        return builder.toString().trim();
    }
}
// END
