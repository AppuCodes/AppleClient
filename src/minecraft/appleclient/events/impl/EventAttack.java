package appleclient.events.impl;

import appleclient.events.Event;

public class EventAttack extends Event
{
    private static final EventAttack e = new EventAttack();
    
    public static EventAttack get()
    {
        e.cancelled = false;
        return e;
    }
}
