package wtf.azure.core.events;

import me.bush.eventbus.event.Event;
import net.minecraft.network.Packet;

public class PacketEvent extends Event {
    private final Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public <T extends Packet<?>> T getPacket() {
        return (T) packet;
    }

    @Override
    protected boolean isCancellable() {
        return true;
    }

    public static class Outbound extends PacketEvent {
        public Outbound(Packet<?> packet) {
            super(packet);
        }
    }

    public static class Inbound extends PacketEvent {
        public Inbound(Packet<?> packet) {
            super(packet);
        }
    }
}
