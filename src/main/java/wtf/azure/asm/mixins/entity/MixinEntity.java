package wtf.azure.asm.mixins.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.azure.core.cache.caches.ModuleCache;
import wtf.azure.core.features.module.impl.combat.Reach;

@Mixin(Entity.class)
public class MixinEntity {
    @Shadow private Box boundingBox;

    @Inject(method = "getBoundingBox", at = @At("HEAD"), cancellable = true)
    public void hookGetBoundingBox(CallbackInfoReturnable<Box> info) {
        Reach REACH = ModuleCache.get().getModule(Reach.class);
        if (REACH.isToggled()) {
            info.setReturnValue(boundingBox.expand(REACH.hitboxAdd.getValue()));
        }
    }
}
