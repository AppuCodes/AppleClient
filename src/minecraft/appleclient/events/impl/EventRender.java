package appleclient.events.impl;

import appleclient.events.Event;

public class EventRender extends Event
{
    private static final EventRender e = new EventRender();
    
    public static EventRender get()
    {
        e.cancelled = false;
        return e;
    }
}
