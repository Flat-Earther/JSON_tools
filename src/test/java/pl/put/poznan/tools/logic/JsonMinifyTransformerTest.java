package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JsonMinifyTransformerTest {
    private String inputJson;

    @BeforeEach
    void setup() {
        this.inputJson = "{\n" +
                "  \"name\" : \"Donald\",\n" +
                "  \"surname\" : \"Tusk\"\n" +
                "}";
    }

    @Test void minifyTest() throws JsonProcessingException {
        JsonMinifyTransformer transformer = new JsonMinifyTransformer(new IdentityJsonTransformer());
        String expectedJson = "{\"name\":\"Donald\",\"surname\":\"Tusk\"}";

        String result = transformer.transform(this.inputJson);
        assertEquals(expectedJson, result);
    }

    @Test void emptyJsonTest() throws JsonProcessingException {
        JsonMinifyTransformer transformer = new JsonMinifyTransformer(new IdentityJsonTransformer());
        String expectedJson = "{}";

        String result = transformer.transform("{ }");
        assertEquals(expectedJson, result);
    }
}