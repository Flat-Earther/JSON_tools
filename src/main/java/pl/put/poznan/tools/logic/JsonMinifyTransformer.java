package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A concrete decorator in the Decorator design pattern that minifies JSON object
 * by removing unnecessary whitespace and formatting.
 * <p>
 * This class wraps another {@link JsonTransformer} and provides a utility method
 * to convert the transformed JSON into a compact, single-line string format.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * JsonTransformer transformer = new JsonMinifyTransformer(new IdentityJsonTransformer());
 * String mini = transformer.transform(originalJson);
 * </pre>
 * </p>
 */
public class JsonMinifyTransformer implements JsonTransformer {

    private final JsonTransformer inner;
    ObjectMapper mapper;

    /**
     * Constructs a JsonMinifyTransformer with an inner transformer.
     *
     * @param inner the inner {@link JsonTransformer} to wrap
     */
    public JsonMinifyTransformer(JsonTransformer inner) {
        this.inner = inner;
        mapper = new ObjectMapper();
    }

    /**
     * Converts the input JSON string into its minified (single-line) string representation.
     *
     * @param json the input JSON string
     * @return a minified JSON string without unnecessary whitespace
     * @throws JsonProcessingException if the input JSON can't be parsed or serialized
     */
    @Override
    public String transform(String json) throws JsonProcessingException {
        json = inner.transform(json);
        JsonNode node = mapper.readTree(json);
        return mapper.writeValueAsString(node);
    }
}
