package appleclient.events.impl;

import appleclient.events.Event;

public class EventTick extends Event
{
    private static final EventTick e = new EventTick();
    
    public static EventTick get()
    {
        e.cancelled = false;
        return e;
    }
}
