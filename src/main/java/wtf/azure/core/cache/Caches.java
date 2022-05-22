package wtf.azure.core.cache;

import wtf.azure.core.Azure;

import java.util.HashMap;
import java.util.Map;

public class Caches {
    private static final Map<Class<? extends BaseCache>, BaseCache> caches = new HashMap<>();

    /**
     * Registers a cache
     * @param cache the cache instance
     */
    public static void registerCache(BaseCache cache) {
        if (cache == null) {
            throw new IllegalArgumentException("Cache cannot be null!");
        }

        Azure.EVENT_BUS.subscribe(cache);
        cache.init();
        caches.put(cache.getClass(), cache);
    }

    /**
     * Gets a cache
     * @param klass the cache class
     * @param <T> the type of cache casted to in the method
     * @return the cache or null
     */
    public static <T extends BaseCache> T getCache(Class<? extends BaseCache> klass) {
        return (T) caches.getOrDefault(klass, null);
    }
}
