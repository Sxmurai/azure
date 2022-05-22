package wtf.azure.asm.mixins.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.azure.core.Azure;
import wtf.azure.core.events.TickEvent;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Shadow @Nullable public ClientPlayerEntity player;

    @Shadow @Nullable public ClientWorld world;

    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
    public void hookGetWindowTitle(CallbackInfoReturnable<String> info) {
        info.setReturnValue(Azure.NAME + " v" + Azure.VERSION);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void hookTick(CallbackInfo info) {
        if (player != null && world != null) {
            Azure.EVENT_BUS.post(new TickEvent());
        }
    }
}
