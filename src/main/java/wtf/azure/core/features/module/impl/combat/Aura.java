package wtf.azure.core.features.module.impl.combat;

import me.bush.eventbus.annotation.EventListener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import wtf.azure.core.events.TickEvent;
import wtf.azure.core.features.module.Module;
import wtf.azure.core.features.module.ModuleCategory;
import wtf.azure.core.features.setting.Setting;

public class Aura extends Module {
    public Aura() {
        super("Aura", ModuleCategory.COMBAT, "Attacks entities");
    }

    public final Setting<Mode> mode = new Setting<>("Mode", Mode.FOCUS);

    public final Setting<Double> range = new Setting<>("Range", 3.5, 3.0, 6.0);
    public final Setting<Boolean> walls = new Setting<>(() -> mode.getValue().equals(Mode.SINGLE), "Walls", false);

    public final Setting<Boolean> rotate = new Setting<>(() -> mode.getValue().equals(Mode.SINGLE), "Rotate", false);
    public final Setting<Boolean> gcd = new Setting<>(rotate, "GCD", true);

    @EventListener
    public void onTick(TickEvent event) {
        if (mode.getValue().equals(Mode.FOCUS)) {
            HitResult result = mc.crosshairTarget;
            if (result == null || !result.getType().equals(Type.ENTITY)) {
                return;
            }

            EntityHitResult entityHitResult = (EntityHitResult) result;
            if (!(entityHitResult.getEntity() instanceof LivingEntity living)) {
                return;
            }

            if (living.isDead()) {
                return;
            }

            if (mc.player.getAttackCooldownProgress(1.0f) == 1.0f) {
                mc.interactionManager.attackEntity(mc.player, living);
                mc.player.swingHand(Hand.MAIN_HAND);
            }

            return;
        }

        LivingEntity target = null;
        double distance = 0.0;

        for (Entity entity : mc.world.getEntities()) {
            if (entity == null || entity.equals(mc.player) || !(entity instanceof LivingEntity living)) {
                continue;
            }

            if (living.isDead()) {
                continue;
            }

            double dist = mc.player.squaredDistanceTo(living);
            if (dist > range.getValue() * range.getValue() || (!walls.getValue() && !mc.player.canSee(living))) {
                continue;
            }

            if (distance > dist || distance == 0.0) {
                distance = dist;
                target = living;
            }
        }

        if (target != null) {
            if (rotate.getValue()) {
                // TODO
            }

            if (mc.player.getAttackCooldownProgress(1.0f) == 1.0f) {
                mc.interactionManager.attackEntity(mc.player, target);
                mc.player.swingHand(Hand.MAIN_HAND);
            }
        }
    }

    public enum Mode {
        FOCUS, SINGLE
    }
}
