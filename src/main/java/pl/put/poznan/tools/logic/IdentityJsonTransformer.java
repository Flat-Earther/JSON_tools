package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * A concrete implementation of {@link JsonTransformer} that performs no transformation.
 * <p>
 * This class serves as the concrete component in the Decorator design pattern.
 * It simply returns the input {@link JsonNode} unchanged, allowing decorators to be composed
 * without requiring a specific transformation at the core. In other words, it is intended to be
 * used as the innermost component in a transformation pipeline.
 * </p>
 */
public class IdentityJsonTransformer implements JsonTransformer {
    /**
     * Returns the input JSON node unchanged.
     *
     * @param node the input {@link JsonNode}
     * @return the same {@link JsonNode} instance with no modifications
     */
    @Override
    public JsonNode transform(JsonNode node) {
        return node;
    }
}
