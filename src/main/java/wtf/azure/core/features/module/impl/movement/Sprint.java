package wtf.azure.core.features.module.impl.movement;

import me.bush.eventbus.annotation.EventListener;
import wtf.azure.core.events.TickEvent;
import wtf.azure.core.features.module.Module;
import wtf.azure.core.features.module.ModuleCategory;
import wtf.azure.core.features.setting.Setting;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", ModuleCategory.MOVEMENT, "Makes you sprint");
    }

    @Override
    protected void onActivated() {
        super.onActivated();
    }

    @Override
    protected void onDeactivated() {
        super.onDeactivated();

        if (nullCheck()) {
            return;
        }

        if (mc.player.isSprinting() && !mc.options.sprintToggled) {
            mc.player.setSprinting(false);
        }
    }

    public final Setting<Mode> mode = new Setting<>("Mode", Mode.LEGIT);

    @EventListener
    public void onTick(TickEvent event) {
        assert mc.player != null;
        if (!mc.player.isSprinting()) {
            switch (mode.getValue()) {
                case LEGIT -> mc.player.setSprinting(mc.player.input.pressingForward && canSprintLegit());
                case RAGE -> mc.player.setSprinting(true);
                case OMNI -> mc.player.setSprinting((mc.player.input.pressingForward || mc.player.input.pressingBack)
                        && canSprintLegit());
            }
        }
    }

    private boolean canSprintLegit() {
        assert mc.player != null;
        return !mc.player.isUsingItem() &&
                !mc.player.isSneaking() &&
                mc.player.getHungerManager().getFoodLevel() > 6 &&
                !mc.player.horizontalCollision;
    }

    public enum Mode {
        LEGIT, RAGE, OMNI
    }
}
