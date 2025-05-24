package pl.put.poznan.tools.rest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.tools.logic.IdentityJsonTransformer;
import pl.put.poznan.tools.logic.JSONTools;
import pl.put.poznan.tools.logic.JsonMinifyTransformer;

import java.util.Arrays;
import java.util.Set;


@RestController
@RequestMapping("/api/json")
public class JSONToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JSONToolsController.class);
    private final JSONTools transformer = new JSONTools();

    @PostMapping(path = "/prettyprint", produces = "application/json")
    public String prettyPrint(@RequestBody String fullJson) {

        logger.debug(fullJson);

        return transformer.prettyPrint(fullJson);
    }

    @PostMapping(path = "/minify", produces = "application/json")
    public String minify(@RequestBody String fullJson) {

        logger.debug(fullJson);

        return transformer.minify(fullJson);
    }

    @PostMapping(path = "/keyfilter", produces = "application/json")
    public String keyFilter(@RequestBody String fullJson,
                         @RequestParam Set<String> keys) {

        logger.debug(fullJson);

        return transformer.keyFilter(fullJson, keys);
    }

    @PostMapping(path = "/keyremove", produces = "application/json")
    public String keyRemove(@RequestBody String fullJson,
                            @RequestParam Set<String> keys) {

        logger.debug(fullJson);

        return transformer.keyRemove(fullJson, keys);
    }

    @PostMapping(path = "/compare", produces = "application/json")
    public String keyRemove(@RequestBody String[] jsons) {

        for(String json: jsons) {
            logger.debug(json);
        }

        if (jsons.length != 2) {
            throw new IllegalArgumentException("Exactly two JSON structures must be provided.");
        }

        return transformer.compare(jsons[0], jsons[1]);
    }
}


