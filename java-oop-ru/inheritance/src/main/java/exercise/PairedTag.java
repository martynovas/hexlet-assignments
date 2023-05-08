package exercise;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

// BEGIN
public class PairedTag extends Tag {
    private String body;

    private List<Tag> children;

    PairedTag(String name, Map<String, String> attributes, String body, List<Tag> children) {
        super(name, attributes);
        this.body = body;
        this.children = children;
    }

    public String getBody() {
        return body;
    }

    public List<Tag> getChildren() {
        return children;
    }

    protected String childrenToString(){
        StringBuilder builder = new StringBuilder();
        children.forEach(e -> builder.append(e));
        return builder.toString();
    }

    @Override
    public String toString() {
        if (getAttributes().isEmpty()) {
            return String.format("<%s>%s%s</%s>",getName(),body,childrenToString(),getName());
        } else {
            return String.format("<%s %s>%s%s</%s>",getName(),attributesToString(),body,childrenToString(),getName());
        }
    }
}
// END
