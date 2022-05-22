package wtf.azure.core.cache.caches;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.bush.eventbus.annotation.EventListener;
import wtf.azure.core.cache.BaseCache;
import wtf.azure.core.cache.Caches;
import wtf.azure.core.events.KeyInputEvent;
import wtf.azure.core.features.module.Module;
import wtf.azure.util.internal.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter @Slf4j
public class ModuleCache extends BaseCache<Module> {
    private final List<Module> modules = new CopyOnWriteArrayList<>();

    @SneakyThrows
    @Override
    public void init() {
        ReflectionUtil.getClasses("wtf.azure.core.features.module.impl", Module.class)
                .forEach((clazz) -> {
                    try {
                        Module module = clazz.getConstructor().newInstance();
                        addModule(module);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                });

        log.info("Loaded {} modules!", modules.size());
    }

    @EventListener
    public void onKeyInput(KeyInputEvent event) {
        if (mc.currentScreen == null && event.getAction() == 0) {
            modules.forEach((module) -> {
                if (module.getBind().getValue() == event.getKeyCode()) {
                    module.setToggled(!module.isToggled());
                }
            });
        }
    }

    public <T extends Module> T getModule(Class<? extends Module> clazz) {
        return (T) objects.getOrDefault(clazz, null);
    }

    private void addModule(Module module) {
        objects.put(module.getClass(), module);
        modules.add(module);
    }

    public static ModuleCache get() {
        return Caches.getCache(ModuleCache.class);
    }
}
