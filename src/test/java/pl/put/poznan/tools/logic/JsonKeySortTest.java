package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JsonKeySortTest {
    private String inputJson;

    @BeforeEach
    void setup() {
        this.inputJson = "{\n" +
                "  \"name\" : \"Donald\",\n" +
                "  \"surname\" : \"Tusk\",\n" +
                "  \"age\" : 68,\n" +
                "  \"profession\" : \"politician\",\n" +
                "  \"loveFor\" : {\n" +
                "    \"Poland\" : \"-100\",\n" +
                "    \"Germany\" : \"100\"\n" +
                "  }\n" +
                "}";
    }

    @Test
    void keySortTest() throws JsonProcessingException {
        JsonKeySort transformer = new JsonKeySort(new IdentityJsonTransformer());
        String expectedJson = "{\n" +
                "  \"age\" : 68,\n" +
                "  \"loveFor\" : {\n" +
                "    \"Germany\" : \"100\",\n" +
                "    \"Poland\" : \"-100\"\n" +
                "  },\n" +
                "  \"name\" : \"Donald\",\n" +
                "  \"profession\" : \"politician\",\n" +
                "  \"surname\" : \"Tusk\"\n" +
                "}";
        String result = transformer.transform(this.inputJson);
        result = result.replaceAll("\\r\\n?", "\n");
        assertEquals(expectedJson, result);
    }
}