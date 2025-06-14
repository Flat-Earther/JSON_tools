package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class JsonKeyRemoveTest {
    private JsonTransformer innerMock;
    private String inputJson;

    @BeforeEach
    void setup() throws JsonProcessingException {
        innerMock = mock(JsonTransformer.class);
        inputJson = "{\n" +
                "  \"name\" : \"Steve\",\n" +
                "  \"age\" : 42,\n" +
                "  \"profession\" : \"miner\",\n" +
                "  \"items\" : {\n" +
                "    \"1\" : \"pickaxe\",\n" +
                "    \"2\" : \"apple\"\n" +
                "  }\n" +
                "}";
        when(innerMock.transform(anyString())).thenReturn(inputJson);
    }

    @Test
    void testKeyRemoval() throws JsonProcessingException {

        JsonKeyRemove keyRemover = new JsonKeyRemove(innerMock, Set.of("age", "profession"));
        String result = keyRemover.transform(inputJson);
        result = result.replaceAll("\\r\\n?", "\n");

        String expectedJson = "{\n" +
                "  \"name\" : \"Steve\",\n" +
                "  \"items\" : {\n" +
                "    \"1\" : \"pickaxe\",\n" +
                "    \"2\" : \"apple\"\n" +
                "  }\n" +
                "}";

        assertEquals(expectedJson, result);
        verify(innerMock).transform(inputJson);
    }

    @Test
    void testNestedObjectKeyRemoval() throws JsonProcessingException {
        JsonKeyRemove keyRemover = new JsonKeyRemove(innerMock, Set.of("items"));
        String result = keyRemover.transform(inputJson);
        result = result.replaceAll("\\r\\n?", "\n");

        String expectedJson = "{\n" +
                "  \"name\" : \"Steve\",\n" +
                "  \"age\" : 42,\n" +
                "  \"profession\" : \"miner\"\n" +
                "}";

        assertEquals(expectedJson, result);
        verify(innerMock).transform(inputJson);
    }

    @Test
    void testUnexistingKeyRemoval() throws JsonProcessingException {
        JsonKeyRemove keyRemover = new JsonKeyRemove(innerMock, Set.of("height"));
        String result = keyRemover.transform(inputJson);
        result = result.replaceAll("\\r\\n?", "\n");

        String expectedJson = inputJson;

        assertEquals(expectedJson, result);
        verify(innerMock).transform(inputJson);
    }

    @Test
    void testEmptyOutput() throws JsonProcessingException {
        JsonKeyRemove keyRemover = new JsonKeyRemove(innerMock, Set.of("name", "age", "profession", "items"));
        String result = keyRemover.transform(inputJson);
        result = result.replaceAll("\\r\\n?", "\n");

        String expectedJson = "{ }";

        assertEquals(expectedJson, result);
        verify(innerMock).transform(inputJson);
    }

    @Test
    void testEmptyInput() throws JsonProcessingException {
        String inputJson2 = "{ }";
        JsonTransformer innerMock2 = mock(JsonTransformer.class);
        when(innerMock.transform(anyString())).thenReturn(inputJson2);

        JsonKeyRemove keyRemover = new JsonKeyRemove(innerMock2, Set.of("name", "age"));

        assertThrows(IllegalArgumentException.class, () -> {
            keyRemover.transform(inputJson2);
        });
    }
}
