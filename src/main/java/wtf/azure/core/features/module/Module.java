package wtf.azure.core.features.module;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.glfw.GLFW;
import wtf.azure.core.features.ToggleableFeature;
import wtf.azure.core.features.setting.Bind;
import wtf.azure.core.features.setting.Setting;
import wtf.azure.util.internal.ReflectionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter @Setter
public class Module extends ToggleableFeature {
    private final ModuleCategory category;
    private final String description;

    private final Map<String, Setting> settingsMap = new HashMap<>();
    private final List<Setting> settings = new CopyOnWriteArrayList<>();

    protected final Bind bind = new Bind("Bind", GLFW.GLFW_KEY_UNKNOWN);

    public Module(String name, ModuleCategory category, String description) {
        super(name);

        this.category = category;
        this.description = description;

        addSetting(bind);
    }

    public void loadSettings() {
        ReflectionUtil.allFieldsWithType(this, Setting.class)
                .forEach((field) -> {
                    field.setAccessible(true);

                    try {
                        addSetting((Setting) field.get(this));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }

    protected void addSetting(Setting setting) {
        settingsMap.put(setting.getName(), setting);
        settings.add(setting);
    }
}
