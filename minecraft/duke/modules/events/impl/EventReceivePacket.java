package duke.modules.events.impl;

import net.minecraft.network.Packet;

public final class EventReceivePacket extends Event
{
    public static Packet<?> packet;

    public EventReceivePacket(final Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    public void setPacket(final Packet<?> packet) {
        this.packet = packet;
    }
}
