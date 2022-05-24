package wtf.azure.asm.mixins.net;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.azure.core.Azure;
import wtf.azure.core.events.PacketEvent.Inbound;
import wtf.azure.core.events.PacketEvent.Outbound;

@Mixin(ClientConnection.class)
public class MixinClientConnection {
    @Inject(method = "send(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void hookSend(Packet<?> packet, CallbackInfo info) {
        Outbound event = new Outbound(packet);
        Azure.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void hookChannelRead0(ChannelHandlerContext ctx, Packet<?> packet, CallbackInfo info) {
        Inbound event = new Inbound(packet);
        Azure.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
}
