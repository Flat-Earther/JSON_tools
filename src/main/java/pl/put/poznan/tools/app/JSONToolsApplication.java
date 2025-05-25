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

import java.util.List;
import java.util.Set;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.tools.rest"})
public class JSONToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JSONToolsApplication.class, args);
    }
}
