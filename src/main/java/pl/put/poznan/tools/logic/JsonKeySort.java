package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * A decorator class that applies JSON key sorting to a JSON string.
 * It wraps another {@link JsonTransformer} and applies its transformation first
 * before sorting all keys in the resulting JSON object recursively.
 * Keys in JSON objects are sorted alphabetically.
 */
public class JsonKeySort implements JsonTransformer {
    private final JsonTransformer inner;
    private final ObjectMapper mapper;

    /**
     * Constructs a JsonKeySort transformer.
     *
     * @param inner the inner JsonTransformer to apply before sorting keys
     */
    public JsonKeySort(JsonTransformer inner) {
        this.inner = inner;
        this.mapper = new ObjectMapper();
    }

    /**
     * Transforms the input JSON string by first applying the inner transformer,
     * then recursively sorting the keys of all JSON objects.
     *
     * @param json the input JSON string
     * @return a JSON string with keys sorted alphabetically
     * @throws JsonProcessingException if the input or transformed JSON is invalid
     */
    @Override
    public String transform(String json) throws JsonProcessingException {
        // Apply the inner transformation first
        String transformedJson = inner.transform(json);

        JsonNode rootNode;
        try {
            // Parse the transformed JSON into a tree structure
            rootNode = mapper.readTree(transformedJson);
        } catch (JsonProcessingException e) {
            throw new JsonProcessingException("Invalid JSON input.") {};
        }

        // Recursively sort the keys in the JSON tree
        JsonNode sortedNode = sortKeysRecursive(rootNode);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sortedNode);
    }

    /**
     * Recursively sorts keys of all objects in a JSON tree.
     *
     * @param node the root of the JSON tree
     * @return a new JsonNode with all object keys sorted alphabetically
     */
    private JsonNode sortKeysRecursive(JsonNode node) {
        if (node.isObject()) {
            // Sort keys in the object node using a TreeMap
            ObjectNode sortedObject = mapper.createObjectNode();
            TreeMap<String, JsonNode> sortedMap = new TreeMap<>();

            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                sortedMap.put(entry.getKey(), sortKeysRecursive(entry.getValue()));
            }

            sortedMap.forEach(sortedObject::set);
            return sortedObject;
        } else if (node.isArray()) {
            ArrayNode sortedArray = mapper.createArrayNode();
            // Recursively sort elements in the array
            for (JsonNode element : node) {
                sortedArray.add(sortKeysRecursive(element));
            }
            return sortedArray;
        } else {
            return node;
        }
    }
}