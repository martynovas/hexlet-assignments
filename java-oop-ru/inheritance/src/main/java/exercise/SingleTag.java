package exercise;

import java.util.Map;

// BEGIN
public class SingleTag extends Tag {

    SingleTag(String name, Map<String, String> attributes) {
        super(name, attributes);
    }

    @Override
    public String toString() {
        if (getAttributes().isEmpty()) {
            return String.format("<%s>",getName());
        } else {
            return String.format("<%s %s>",getName(),attributesToString());
        }
    }
}
// END
