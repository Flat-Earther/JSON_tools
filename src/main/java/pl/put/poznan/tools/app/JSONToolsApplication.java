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

            JsonNode filtered = transformer.transform(root);

            String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(filtered);
            System.out.println(result);

        } catch (Exception e) {
            System.err.println("Invalid JSON: " + e.getMessage());
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            JsonMinifyTransformer minifier = new JsonMinifyTransformer(new IdentityJsonTransformer());

            String result = minifier.minify(root);
            System.out.println("\nMinified JSON:\n" + result);

        } catch (Exception e) {
            System.err.println("\nInvalid JSON: " + e.getMessage());
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            JsonPrettyPrintTransformer prettyJson = new JsonPrettyPrintTransformer(new IdentityJsonTransformer());
            
            String result = prettyJson.prettyPrint(root);
            System.out.println("\nPretty JSON:\n" + result);

        } catch (Exception e) {
            System.err.println("\nInvalid JSON: " + e.getMessage());
        }
        // ------------------------------------------------------------

        SpringApplication.run(JSONToolsApplication.class, args);
    }
}
