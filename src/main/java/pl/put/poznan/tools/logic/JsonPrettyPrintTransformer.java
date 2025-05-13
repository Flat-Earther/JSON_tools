package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A concrete decorator in the Decorator design pattern that pretty-prints a JSON object.
 * <p>
 * This class wraps another {@link JsonTransformer} and applies an additional transformation
 * that outputs the JSON in a human-readable, indented format.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * JsonTransformer transformer = new JsonPrettyPrintTransformer(new IdentityJsonTransformer());
 * String result = transformer.prettyPrint(originalJson);
 * </pre>
 * </p>
 */
public class JsonPrettyPrintTransformer implements JsonTransformer {

    private final JsonTransformer inner;

    /**
     * Constructs a JsonPrettyPrintTransformer with an inner transformer.
     *
     * @param inner the inner {@link JsonTransformer} to wrap
     */
    public JsonPrettyPrintTransformer(JsonTransformer inner) {
        this.inner = inner;
    }

    /**
     * Transforms the input JSON by first applying the inner transformer.
     * This method does not perform pretty-printing itself — use {@link #prettyPrint(JsonNode)} for that.
     *
     * @param node the input {@link JsonNode}
     * @return the transformed {@link JsonNode}
     */
    @Override
    public JsonNode transform(JsonNode node) {
        return inner.transform(node);
    }

    /**
     * Converts a JsonNode into its pretty-printed (indented) string representation.
     *
     * @param node the input JsonNode
     * @return a formatted JSON string with indentation
     * @throws Exception if the node can't be serialized
     */
    public String prettyPrint(JsonNode node) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transform(node));
    }
}
