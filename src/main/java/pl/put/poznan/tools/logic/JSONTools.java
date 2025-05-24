package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Set;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class JSONTools {

    public JSONTools(){}

    private JsonTransformer transformer;

    public String minify(String text){
        transformer = new JsonMinifyTransformer(new IdentityJsonTransformer());
        try {
            return transformer.transform(text);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String prettyPrint(String text){
        transformer = new JsonPrettyPrintTransformer(new IdentityJsonTransformer());
        try {
            return transformer.transform(text);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String keyFilter(String text, Set<String> keys){
        transformer = new JsonKeyFilter(new IdentityJsonTransformer(), keys);
        try {
            return transformer.transform(text);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String keyRemove(String text, Set<String> keys){
        transformer = new JsonKeyRemove(new IdentityJsonTransformer(), keys);
        try {
            return transformer.transform(text);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String compare(String text, String text2){
        throw new RuntimeException("Not implemented yet");
    }
}
