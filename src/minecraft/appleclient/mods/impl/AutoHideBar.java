package appleclient.mods.impl;

import com.google.common.eventbus.Subscribe;

import appleclient.events.impl.EventTick;
import appleclient.mods.Mod;

public class AutoHideBar extends Mod
{
    private int currentItem = 0;
    public int ticks = 0;
    
    public AutoHideBar()
    {
        super("Auto Hide Bar", "Automatically hides the hotbar when not in use. Similar to Immersive HUD in games.");
    }
    
    @Subscribe
    public void onTick(EventTick e)
    {
        if (currentItem != mc.thePlayer.inventory.currentItem)
        {
            ticks = 60;
        }
        
        if (ticks != 0)
        {
            ticks--;
        }
        
        currentItem = mc.thePlayer.inventory.currentItem;
    }
}