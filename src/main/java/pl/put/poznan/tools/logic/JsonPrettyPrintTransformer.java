package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
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
 * String prettyprint = transformer.transform(originalJson);
 * </pre>
 * </p>
 */
public class JsonPrettyPrintTransformer implements JsonTransformer {

    private final JsonTransformer inner;
    ObjectMapper mapper;

    /**
     * Constructs a JsonPrettyPrintTransformer with an inner transformer.
     *
     * @param inner the inner {@link JsonTransformer} to wrap
     */
    public JsonPrettyPrintTransformer(JsonTransformer inner) {
        this.inner = inner;
        mapper = new ObjectMapper();
    }

    /**
     * Converts the input JSON string into its pretty-printed (indented) string representation.
     *
     * @param json the input JSON string
     * @return a formatted JSON string with indentation
     * @throws JsonProcessingException if the input JSON can't be parsed or serialized
     */
    @Override
    public String transform(String json) throws JsonProcessingException {
        json = inner.transform(json);
        JsonNode node = mapper.readTree(json);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
    }
}
