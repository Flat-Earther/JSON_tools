package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Set;

/**
 * A concrete decorator in the Decorator design pattern that filters a JSON object to retain only a specified subset of keys.
 * <p>
 * This class wraps another {@link JsonTransformer} and applies an additional transformation
 * that removes all keys from the JSON object except those explicitly specified in the {@code keysToKeep} set.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * Set&lt;String&gt; keys = Set.of("name", "age");
 * JsonTransformer transformer = new JsonKeyFilter(new IdentityJsonTransformer(), keys);
 * String filteredJson = transformer.transform(originalJsonString);
 * </pre>
 * </p>
 */
public class JsonKeyFilter implements JsonTransformer {

    private final JsonTransformer inner;
    private final Set<String> keysToKeep;
    ObjectMapper mapper;

    /**
     * Constructs a JsonKeyFilter with an inner transformer and a set of keys to retain.
     *
     * @param inner       the inner {@link JsonTransformer} to wrap
     * @param keysToKeep  the set of keys to retain in the final JSON output
     */
    public JsonKeyFilter(JsonTransformer inner, Set<String> keysToKeep) {
        this.inner = inner;
        this.keysToKeep = keysToKeep;
        mapper = new ObjectMapper();
    }

    /**
     * Transforms the input JSON by first applying the inner transformer,
     * then filtering the result to keep only the specified keys.
     *
     * @param json the input JSON string
     * @return a new JSON string containing only the retained keys
     * @throws JsonProcessingException if the input or output JSON cannot be processed
     */
    @Override
    public String transform(String json) throws JsonProcessingException {
        // First apply the inner transformer (if any)
        json = inner.transform(json);
        JsonNode node = mapper.readTree(json);

        // Create a new JSON node
        ObjectNode filteredNode = ((ObjectNode) node).objectNode();

        // Add only the keys we want to keep
        node.fields().forEachRemaining(entry -> {
            if (keysToKeep.contains(entry.getKey())) {
                filteredNode.set(entry.getKey(), entry.getValue());
            }
        });

        // Return the transformed JSON as a string
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(filteredNode);
    }
}