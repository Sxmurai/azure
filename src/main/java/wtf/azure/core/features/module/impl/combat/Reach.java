package wtf.azure.core.features.module.impl.combat;

import wtf.azure.core.features.module.Module;
import wtf.azure.core.features.module.ModuleCategory;
import wtf.azure.core.features.setting.Setting;

public class Reach extends Module {
    public Reach() {
        super("Reach", ModuleCategory.COMBAT, "Makes you reach further");
    }

    public final Setting<Float> reach = new Setting<>("Reach", 3.5f, 3.0f, 6.0f);
    public final Setting<Double> hitboxAdd = new Setting<>("Hitbox Add", 0.0, 0.0, 1.0);
}
