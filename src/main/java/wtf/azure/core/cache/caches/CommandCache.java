package wtf.azure.core.cache.caches;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.bush.eventbus.annotation.EventListener;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import wtf.azure.core.cache.BaseCache;
import wtf.azure.core.cache.Caches;
import wtf.azure.core.events.PacketEvent;
import wtf.azure.core.features.command.Command;
import wtf.azure.util.internal.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter @Setter @Slf4j
public class CommandCache extends BaseCache<Command> {
    private final Map<String, Command> commandMap = new HashMap<>();
    private final List<Command> commands = new CopyOnWriteArrayList<>();
    private final CommandDispatcher<Object> dispatcher = new CommandDispatcher<>();

    /**
     * The command prefix
     */
    private String prefix = ".";

    @SneakyThrows
    @Override
    public void init() {
        ReflectionUtil.getClasses("wtf.azure.core.features.command.impl", Command.class)
                .forEach((clazz) -> {
                    try {
                        Command module = clazz.getConstructor().newInstance();
                        addCommand(module);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                });

        log.info("Loaded {} commands!", commands.size());
    }

    @EventListener
    public void onPacketOutbound(PacketEvent.Outbound event) {
        if (event.getPacket() instanceof ChatMessageC2SPacket packet) {
            String message = packet.getChatMessage();

            if (message.toLowerCase().startsWith(prefix)) {
                event.setCancelled(true);

                try {
                    dispatcher.execute(dispatcher.parse(message.substring(prefix.length()), 1));
                } catch (Exception e) {
                    e.printStackTrace();
                    print("A command threw an exception, check the logs for more info.");
                }
            }
        }
    }

    public <T extends Command> T getCommand(Class<? extends Command> clazz) {
        return (T) objects.getOrDefault(clazz, null);
    }

    private void addCommand(Command command) {
        objects.put(command.getClass(), command);
        commands.add(command);

        command.getAliases().forEach((alias) -> {
            LiteralArgumentBuilder<Object> argumentBuilder = Command.build(alias);
            command.build(argumentBuilder);

            dispatcher.register(argumentBuilder);

            commandMap.put(alias, command);
        });
    }

    public static CommandCache get() {
        return Caches.getCache(CommandCache.class);
    }
}
