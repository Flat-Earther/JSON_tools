package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JsonKeyFilterTest {

    private String inputJson;

    @BeforeEach
    void setup() {
        this.inputJson = "{\n" +
                "  \"name\" : \"Donald\",\n" +
                "  \"surname\" : \"Tusk\",\n" +
                "  \"age\" : 68,\n" +
                "  \"profession\" : \"politician\",\n" +
                "  \"LoveFor\" : {\n" +
                "    \"Poland\" : \"-100\",\n" +
                "    \"Germany\" : \"100\"\n" +
                "  }\n" +
                "}";
    }

    @Test
    void keyFilterTest() throws JsonProcessingException {
        JsonKeyFilter transformer = new JsonKeyFilter(new IdentityJsonTransformer(), Set.of("surname"));
        String expectedJson = "{\n" +
                "  \"surname\" : \"Tusk\"\n}";
        String result = transformer.transform(this.inputJson);
        result = result.replaceAll("\\r\\n?", "\n");
        assertEquals(expectedJson, result);
    }

    @Test
    void keyFilter2Test() throws JsonProcessingException {
        JsonKeyFilter transformer = new JsonKeyFilter(new IdentityJsonTransformer(), Set.of("profession"));
        String expectedJson = "{\n" +
                "  \"profession\" : \"politician\"\n}";
        String result = transformer.transform(this.inputJson);
        result = result.replaceAll("\\r\\n?", "\n");
        assertEquals(expectedJson, result);
    }

    @Test
    void keyFilter3Test() throws JsonProcessingException {
        JsonKeyFilter transformer = new JsonKeyFilter(new IdentityJsonTransformer(), Set.of("age"));
        String expectedJson = "{\n" +
                "  \"age\" : 68\n}";
        String result = transformer.transform(this.inputJson);
        result = result.replaceAll("\\r\\n?", "\n");
        assertEquals(expectedJson, result);
    }

    @Test
    void multipleKeysFilterTest() throws JsonProcessingException {
        JsonKeyFilter transformer = new JsonKeyFilter(new IdentityJsonTransformer(), Set.of("surname", "age"));
        String expectedJson = "{\n" +
                "  \"surname\" : \"Tusk\",\n" +
                "  \"age\" : 68\n}";
        String result = transformer.transform(this.inputJson);
        result = result.replaceAll("\\r\\n?", "\n");
        assertEquals(expectedJson, result);
    }

    @Test
    void emptyKeysTest() throws JsonProcessingException {
        JsonKeyFilter transformer = new JsonKeyFilter(new IdentityJsonTransformer(), Set.of());
        String expectedJson = "{ }";
        String result = transformer.transform(this.inputJson);
        result = result.replaceAll("\\r\\n?", "\n");
        assertEquals(expectedJson, result);
    }

    @Test
    void emptyJsonTest() throws JsonProcessingException {
        JsonKeyFilter transformer = new JsonKeyFilter(new IdentityJsonTransformer(), Set.of());
        String expectedJson = "{ }";
        String result = transformer.transform("{ }");
        result = result.replaceAll("\\r\\n?", "\n");
        assertEquals(expectedJson, result);
    }

    @Test
    void nonExistingKeysTest() throws JsonProcessingException {
        JsonKeyFilter transformer = new JsonKeyFilter(new IdentityJsonTransformer(), Set.of("IQ"));
        String expectedJson = "{ }";
        String result = transformer.transform(this.inputJson);
        result = result.replaceAll("\\r\\n?", "\n");
        assertEquals(expectedJson, result);
    }
}