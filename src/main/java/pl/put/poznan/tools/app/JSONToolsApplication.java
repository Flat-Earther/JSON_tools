package pl.put.poznan.tools.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.put.poznan.tools.logic.IdentityJsonTransformer;
import pl.put.poznan.tools.logic.JsonKeyFilter;
import pl.put.poznan.tools.logic.JsonKeyRemove;
import pl.put.poznan.tools.logic.JsonMinifyTransformer;
import pl.put.poznan.tools.logic.JsonPrettyPrintTransformer;
import pl.put.poznan.tools.logic.JsonTransformer;

import java.util.Set;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.tools.rest"})
public class JSONToolsApplication {

    public static void main(String[] args) {

        // Test code
        // ------------------------------------------------------------
        String json = "{\n" +
                "  \"name\": \"Alice\",\n" +
                "  \"age\": 30,\n" +
                "  \"city\": \"New York\",\n" +
                "  \"email\": \"alice@example.com\",\n" +
                "  \"projects\": [\n" +
                "    { \"title\": \"Project A\", \"year\": 2021, \"status\": \"completed\" },\n" +
                "    { \"title\": \"Project B\", \"year\": 2022, \"status\": \"ongoing\" }\n" +
                "  ]\n" +
                "}";

        Set<String> keysToRemove = Set.of("name", "age", "projects");

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            JsonTransformer transformer = new JsonKeyRemove(new IdentityJsonTransformer(), keysToRemove);

            String filtered = transformer.transform(json);
            System.out.println(filtered);

        } catch (Exception e) {
            System.err.println("Invalid JSON: " + e.getMessage());
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            JsonMinifyTransformer minifier = new JsonMinifyTransformer(new IdentityJsonTransformer());

            String mini = minifier.transform(json);
            System.out.println("\nMinified JSON:\n" + mini);

        } catch (Exception e) {
            System.err.println("\nInvalid JSON: " + e.getMessage());
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            JsonPrettyPrintTransformer prettyJson = new JsonPrettyPrintTransformer(new IdentityJsonTransformer());

            String pretty = prettyJson.transform(json);
            System.out.println("\nPretty JSON:\n" + pretty);

        } catch (Exception e) {
            System.err.println("\nInvalid JSON: " + e.getMessage());
        }
        // ------------------------------------------------------------

        SpringApplication.run(JSONToolsApplication.class, args);
    }
}
