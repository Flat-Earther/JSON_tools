package pl.put.poznan.tools.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.put.poznan.tools.logic.IdentityJsonTransformer;
import pl.put.poznan.tools.logic.JsonKeyFilter;
import pl.put.poznan.tools.logic.JsonTransformer;

import java.util.Set;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.tools.rest"})
public class JSONToolsApplication {

    public static void main(String[] args) {
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

        Set<String> keysToKeep = Set.of("name", "age", "projects");

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            JsonTransformer transformer = new JsonKeyFilter(new IdentityJsonTransformer(), keysToKeep);

            JsonNode filtered = transformer.transform(root);

            String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(filtered);
            System.out.println(result);

        } catch (Exception e) {
            System.err.println("Invalid JSON: " + e.getMessage());
        }

        SpringApplication.run(JSONToolsApplication.class, args);
    }
}
