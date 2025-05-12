package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.databind.JsonNode;
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
 * JsonNode filtered = transformer.transform(originalJson);
 * </pre>
 * </p>
 */
public class JsonKeyFilter implements JsonTransformer {

    private final JsonTransformer inner;
    private final Set<String> keysToKeep;

    /**
     * Constructs a JsonKeyFilter with an inner transformer and a set of keys to retain.
     *
     * @param inner       the inner {@link JsonTransformer} to wrap
     * @param keysToKeep  the set of keys to retain in the final JSON output
     */
    public JsonKeyFilter(JsonTransformer inner, Set<String> keysToKeep) {
        this.inner = inner;
        this.keysToKeep = keysToKeep;
    }

    /**
     * Transforms the input JSON by first applying the inner transformer,
     * then filtering the result to keep only the specified keys.
     *
     * @param node the input {@link JsonNode}
     * @return a new {@link JsonNode} containing only the retained keys
     */
    @Override
    public JsonNode transform(JsonNode node) {
        // First apply the inner transformer (if any)
        JsonNode processed = inner.transform(node);

        // Create a new JSON node
        ObjectNode filteredNode = ((ObjectNode) node).objectNode();

        // Add keys to keep to the new node
        processed.fields().forEachRemaining(entry -> {
            if (keysToKeep.contains(entry.getKey())) {
                filteredNode.set(entry.getKey(), entry.getValue());
            }
        });

        // return the new node
        return filteredNode;
    }
}