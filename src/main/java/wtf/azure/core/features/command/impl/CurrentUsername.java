package wtf.azure.core.features.command.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import wtf.azure.core.features.command.Command;

import java.util.List;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class CurrentUsername extends Command {
    public CurrentUsername() {
        super(List.of("username", "currentusername", "currentuser"), "Tells you your current username");
    }

    @Override
    public void build(LiteralArgumentBuilder<Object> builder) {
        builder.executes((ctx) -> {
            if (mc.player == null) {
                return SINGLE_SUCCESS;
            }

            print("You are playing on the account " + mc.player.getGameProfile().getName());

            return SINGLE_SUCCESS;
        });
    }
}
