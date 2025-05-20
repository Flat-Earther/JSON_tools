package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A concrete decorator in the Decorator design pattern that minifies JSON output
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
 * String result = transformer.minify(originalJson);
 * </pre>
 * </p>
 */
public class JsonMinifyTransformer implements JsonTransformer {

    private final JsonTransformer inner;

    /**
     * Constructs a JsonMinifyTransformer with an inner transformer.
     *
     * @param inner the inner {@link JsonTransformer} to wrap
     */
    public JsonMinifyTransformer(JsonTransformer inner) {
        this.inner = inner;
    }

    /**
     * Transforms the input JSON by first applying the inner transformer.
     * This method does not perform minification itself — use {@link #transformToString(JsonNode)} for that.
     *
     * @param node the input {@link JsonNode}
     * @return the transformed {@link JsonNode}
     */
    @Override
    public JsonNode transform(JsonNode node) {
        return inner.transform(node);
    }

    /**
     * Converts the transformed JSON into its minified (single-line) string representation.
     *
     * @param node the input {@link JsonNode}
     * @return a minified JSON string without unnecessary whitespace
     * @throws Exception if the node can't be serialized
     */
    public String transformToString(JsonNode node) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(transform(node));
    }
}
