package wtf.azure.asm.mixins.input;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.azure.core.cache.caches.ModuleCache;
import wtf.azure.core.features.module.impl.combat.Reach;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {
    private static final Reach REACH = ModuleCache.get().getModule(Reach.class);

    @Inject(method = "getReachDistance", at = @At("HEAD"), cancellable = true)
    public void hookGetReachDistance(CallbackInfoReturnable<Float> info) {
        if (REACH.isToggled()) {
            info.setReturnValue(REACH.reach.getValue());
        }
    }
}

