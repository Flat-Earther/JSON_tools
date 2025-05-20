package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represents a transformation operation on a JSON string.
 * <p>
 * This interface serves as the component role in the Decorator design pattern.
 * Implementations define how a JSON string should be transformed,
 * enabling filtering, restructuring, or other modifications.
 * </p>
 */
public interface JsonTransformer {
    /**
     * Transforms the input JSON string and returns a modified JSON string.
     *
     * @param json the input JSON string to be transformed
     * @return the transformed JSON string
     * @throws JsonProcessingException if the input or output JSON is invalid
     */
    String transform(String json) throws JsonProcessingException;
}
