package appleclient.events.impl;

import appleclient.events.Event;
import net.minecraft.network.Packet;

public class EventPacketReceive extends Event
{
    private static final EventPacketReceive e = new EventPacketReceive();
    public Packet<?> packet;
    
    public static EventPacketReceive get(Packet<?> packet)
    {
        e.cancelled = false;
        e.packet = packet;
        return e;
    }
}
