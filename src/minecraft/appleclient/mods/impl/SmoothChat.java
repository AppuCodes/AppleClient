package appleclient.mods.impl;

import com.google.common.eventbus.Subscribe;

import appleclient.events.impl.EventTick;
import appleclient.mods.Mod;

public class SmoothChat extends Mod
{
    public int chatTicks = 0;
    
    public SmoothChat()
    {
        super("Smooth Chat", "Makes the chat smooth.");
    }
    
    @Subscribe
    public void onTick(EventTick e)
    {
        if (chatTicks != 0)
        {
            chatTicks--;
        }
    }
}
