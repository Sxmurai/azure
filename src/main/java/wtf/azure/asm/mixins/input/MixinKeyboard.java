package wtf.azure.asm.mixins.input;

import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.azure.core.Azure;
import wtf.azure.core.events.KeyInputEvent;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void hookOnKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo info) {
        Azure.EVENT_BUS.post(new KeyInputEvent(key, action, modifiers, window));
    }
}
