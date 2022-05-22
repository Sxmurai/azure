package wtf.azure.asm.mixins.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.azure.core.cache.Caches;
import wtf.azure.core.cache.caches.ModuleCache;
import wtf.azure.core.features.module.impl.other.UnfocusedCPU;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    private static final UnfocusedCPU UNFOCUSEDCPU = ModuleCache.get().getModule(UnfocusedCPU.class);

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(float tickDelta, long startTime, boolean tick, CallbackInfo info) {
        if (UNFOCUSEDCPU.isToggled() && !client.isWindowFocused()) {
            info.cancel();
        }
    }
}
