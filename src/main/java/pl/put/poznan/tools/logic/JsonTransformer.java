package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represents a transformation operation on a JSON structure.
 * <p>
 * This interface serves as the component role in the Decorator design pattern.
 * Implementations define how a {@link JsonNode} should be transformed,
 * enabling filtering, restructuring, or other modifications.
 * </p>
 */
public interface JsonTransformer {
    /**
     * Transforms the input JSON node and returns a modified JSON node.
     *
     * @param node the input {@link JsonNode} to be transformed
     * @return the transformed {@link JsonNode}
     */
    JsonNode transform(JsonNode node);
}
