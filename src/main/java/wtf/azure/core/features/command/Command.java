package wtf.azure.core.features.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import lombok.Getter;
import wtf.azure.core.features.Feature;

import java.util.List;

@Getter
public abstract class Command extends Feature {
    private final List<String> aliases;
    private final String description;

    public Command(List<String> aliases, String description) {
        super(aliases.get(0));

        this.aliases = aliases;
        this.description = description;
    }

    /**
     * Called whenever one of the aliases has been called via a command input
     *
     * @param builder the builder for the alias (see Command#build(String))
     */
    public abstract void build(LiteralArgumentBuilder<Object> builder);

    /**
     * Creates a literal argument builder for a specific alias
     *
     * @param alias the alias for the builder
     * @return a literal argument builder for the command
     */
    public static LiteralArgumentBuilder<Object> build(String alias) {
        return LiteralArgumentBuilder.literal(alias);
    }
}
