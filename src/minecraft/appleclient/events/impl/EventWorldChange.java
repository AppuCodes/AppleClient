package appleclient.events.impl;

import appleclient.events.Event;

public class EventWorldChange extends Event
{
    private static final EventWorldChange e = new EventWorldChange();
    
    public static EventWorldChange get()
    {
        e.cancelled = false;
        return e;
    }
}
