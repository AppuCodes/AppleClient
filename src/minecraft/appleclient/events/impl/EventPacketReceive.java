package appleclient.events.impl;

import appleclient.events.Event;
import net.minecraft.network.Packet;

public class EventPacketReceive extends Event
{
    public Packet packet;
    
    public EventPacketReceive(Packet packet)
    {
        this.packet = packet;
    }
}
