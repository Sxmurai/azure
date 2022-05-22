package wtf.azure.core.cache;

import lombok.Getter;
import wtf.azure.util.Globals;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class BaseCache<T> implements Globals {
    protected final Map<Class<?>, T> objects = new HashMap<>();

    public abstract void init();
}
