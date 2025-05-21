package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Set;

/**
 * A concrete decorator in the Decorator design pattern that removes specified keys from a JSON object.
 * <p>
 * This class wraps another {@link JsonTransformer} and applies an additional transformation
 * that removes specific keys from the JSON object based on the {@code keysToRemove} set.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * Set&lt;String&gt; keysToRemove = Set.of("password", "ssn");
 * JsonTransformer transformer = new JsonKeyRemove(new IdentityJsonTransformer(), keysToRemove);
 * String filtered = transformer.transform(originalJsonString);
 * </pre>
 * </p>
 */
public class JsonKeyRemove implements JsonTransformer {

    private final JsonTransformer inner;
    private final Set<String> keysToRemove;
    ObjectMapper mapper;

    /**
     * Constructs a JsonKeyRemove with an inner transformer and a set of keys to remove.
     *
     * @param inner         the inner {@link JsonTransformer} to wrap
     * @param keysToRemove  the set of keys to remove from the final JSON output
     */
    public JsonKeyRemove(JsonTransformer inner, Set<String> keysToRemove) {
        this.inner = inner;
        this.keysToRemove = keysToRemove;
        mapper = new ObjectMapper();
    }

    /**
     * Transforms the input JSON string by first applying the inner transformer,
     * then removing the specified keys from the result.
     *
     * @param json the input JSON string
     * @return a new JSON string with specified keys removed
     * @throws JsonProcessingException if the input JSON cannot be parsed or serialized
     */
    @Override
    public String transform(String json) throws JsonProcessingException {
        // First apply the inner transformer (if any)
        json = inner.transform(json);
        JsonNode node = mapper.readTree(json);

        // Create a new filtered JSON object
        ObjectNode filteredNode = ((ObjectNode) node).objectNode();

        // Copy only the keys that are not in the keysToRemove set
        node.fields().forEachRemaining(entry -> {
            if (!keysToRemove.contains(entry.getKey())) {
                filteredNode.set(entry.getKey(), entry.getValue());
            }
        });

        // Return the filtered JSON as a string
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(filteredNode);
    }
}