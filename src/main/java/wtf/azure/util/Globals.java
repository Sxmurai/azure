package wtf.azure.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public interface Globals {
    /**
     * The global Minecraft client instance
     */
    MinecraftClient mc = MinecraftClient.getInstance();

    /**
     * The chat prefix
     */
    String PREFIX = "\u00A7c[Azure]\u00A7r ";

    default boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    /**
     * Prints a message into the in-game chat
     * @param content the message to print
     */
    default void print(String content) {
        mc.inGameHud.getChatHud().addMessage(new LiteralText(PREFIX + content));
    }

    /**
     * Prints a message into the in-game chat with the editable property
     * @param id the editable id
     * @param content the message to print
     */
    default void print(int id, String content) {
        // TODO: mixin
    }
}
