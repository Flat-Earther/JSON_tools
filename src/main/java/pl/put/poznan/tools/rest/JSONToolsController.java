package pl.put.poznan.tools.rest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.tools.logic.IdentityJsonTransformer;
import pl.put.poznan.tools.logic.JSONTools;
import pl.put.poznan.tools.logic.JsonMinifyTransformer;
import pl.put.poznan.tools.logic.TextComparer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/api/json")
public class JSONToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JSONToolsController.class);
    private final JSONTools transformer = new JSONTools();

    @PostMapping(path = "/prettyprint", produces = "application/json")
    public ResponseEntity<String> prettyPrint(@RequestBody String fullJson) {

        logger.debug("Method prettyprint called for string: {}", fullJson);
        return ResponseEntity.ok(transformer.prettyPrint(fullJson));
    }

    @PostMapping(path = "/minify", produces = "application/json")
    public ResponseEntity<String> minify(@RequestBody String fullJson) {
        logger.debug("Method minify called for string: {}", fullJson);
        return ResponseEntity.ok(transformer.minify(fullJson));
    }

    @PostMapping(path = "/keyfilter", produces = "application/json")
    public ResponseEntity<String> keyFilter(@RequestBody String fullJson,
                         @RequestParam Set<String> keys) {
        logger.debug("Method keyfilter called for string: {} with keys: {}", fullJson, String.join(",", keys));
        return ResponseEntity.ok(transformer.keyFilter(fullJson, keys));
    }

    @PostMapping(path = "/keyremove", produces = "application/json")
    public ResponseEntity<String> keyRemove(@RequestBody String fullJson,
                            @RequestParam Set<String> keys) {
        logger.debug("Method keyremove called for string: {} with keys: {}", fullJson, String.join(",", keys));
        return ResponseEntity.ok(transformer.keyRemove(fullJson, keys));
    }

    @PostMapping(path = "/compare", produces = "application/json")
    public ResponseEntity<?> keyRemove(@RequestBody String[] jsons) {

        try {
            if (jsons.length != 2) {
                logger.error("Incorrect number of arguments provided to compare method. Expected: 2. Provided: {}", jsons.length);
                throw new IllegalArgumentException("Exactly two texts must be provided to compare.");
            }

            logger.debug("Method compare called for strings: {}, {}", jsons[0], jsons[1]);

            TextComparer comparer = new TextComparer(jsons[0], jsons[1]);

            return ResponseEntity.ok(comparer.compare());
        }
        catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}


