package wtf.azure.core.features;

import lombok.Getter;
import wtf.azure.util.Globals;

/**
 * Represents a base feature
 *
 * @author aesthetical
 * @since 5/22/22
 */
@Getter
public class Feature implements Globals {
    private final String name;

    public Feature(String name) {
        this.name = name;
    }
}
