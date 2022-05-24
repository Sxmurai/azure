package wtf.azure.core;

import lombok.extern.slf4j.Slf4j;
import me.bush.eventbus.bus.EventBus;
import me.bush.eventbus.handler.handlers.LambdaHandler;
import me.bush.eventbus.handler.handlers.ReflectHandler;
import net.fabricmc.api.ClientModInitializer;
import wtf.azure.core.cache.Caches;
import wtf.azure.core.cache.caches.CommandCache;
import wtf.azure.core.cache.caches.ModuleCache;
import wtf.azure.util.versioning.BuildConfig;

/**
 * The main entry class for a client mod
 *
 * @author aesthetical
 * @since 5/22/22
 */
@Slf4j
public class Azure implements ClientModInitializer {
    public static final String NAME = "Azure";
    public static final String HASH = BuildConfig.HASH;
    public static final String VERSION = "1.0.0-" + HASH;

    public static final EventBus EVENT_BUS = new EventBus(ReflectHandler.class, log::error);

    @Override
    public void onInitializeClient() {
        log.info("Loading {} v{}", NAME, VERSION);

        Caches.registerCache(new ModuleCache());
        Caches.registerCache(new CommandCache());
    }
}
