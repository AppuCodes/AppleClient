package appleclient.mods.events.impl;

import appleclient.mods.events.Event;

public class EventKey extends Event
{
    public int key;
    
    public EventKey(int key)
    {
        this.key = key;
    }
}
