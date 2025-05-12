package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Set;

/**
 * A concrete decorator in the Decorator design pattern that removes specified keys from a JSON object.
 * <p>
 * This class wraps another {@link JsonTransformer} and applies an additional transformation
 * that removes all keys from the JSON object except those explicitly specified in the {@code keysToKeep} set.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * Set&lt;String&gt; keys = Set.of("name", "age");
 * JsonTransformer transformer = new JsonKeyRemove(new IdentityJsonTransformer(), keys);
 * JsonNode filtered = transformer.transform(originalJson);
 * </pre>
 * </p>
 */
public class JsonKeyRemove implements JsonTransformer {

    private final JsonTransformer inner;
    private final Set<String> keysToRemove;

    /**
     * Constructs a JsonKeyRemove with an inner transformer and a set of keys to retain.
     *
     * @param inner       the inner {@link JsonTransformer} to wrap
     * @param keysToRemove  the set of keys to retain in the final JSON output
     */
    public JsonKeyRemove(JsonTransformer inner, Set<String> keysToRemove) {
        this.inner = inner;
        this.keysToRemove = keysToRemove;
    }

    /**
     * Transforms the input JSON by first applying the inner transformer,
     * then filtering the result to remove the specified keys.
     *
     * @param node the input {@link JsonNode}
     * @return a new {@link JsonNode} without the removed keys
     */
    @Override
    public JsonNode transform(JsonNode node) {
        // First apply the inner transformer (if any)
        JsonNode processed = inner.transform(node);

        // Create a new JSON node
        ObjectNode filteredNode = ((ObjectNode) node).objectNode();

        // Don't add keys which are to be removed, to the new node
        processed.fields().forEachRemaining(entry -> {
            if (!keysToRemove.contains(entry.getKey())) {
                filteredNode.set(entry.getKey(), entry.getValue());
            }
        });

        // return the new node
        return filteredNode;
    }
}