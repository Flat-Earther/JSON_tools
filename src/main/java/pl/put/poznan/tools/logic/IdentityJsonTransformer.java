package pl.put.poznan.tools.logic;

/**
 * A concrete implementation of {@link JsonTransformer} that performs no transformation.
 * <p>
 * This class serves as the concrete component in the Decorator design pattern.
 * It simply returns the input JSON string unchanged, allowing decorators to be composed
 * without requiring a specific transformation at the core. In other words, it is intended to be
 * used as the innermost component in a transformation pipeline.
 * </p>
 */
public class IdentityJsonTransformer implements JsonTransformer {
    /**
     * Returns the input JSON string unchanged.
     *
     * @param json the input JSON string
     * @return the same JSON string with no modifications
     */
    @Override
    public String transform(String json) {
        return json;
    }
}
