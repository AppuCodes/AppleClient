package appleclient.mods.impl;

import com.google.common.eventbus.Subscribe;

import appleclient.events.impl.EventTick;
import appleclient.mods.Mod;
import appleclient.settings.impl.ToggleSetting;

public class HotbarTweaks extends Mod
{
    private int currentItem = 0;
    public boolean flag = false;
    public int ticks = 0;
    
    public HotbarTweaks()
    {
        super("Hotbar Tweaks", "Tweaks for the hotbar.");
        setupSettings(2);
        addSetting(new ToggleSetting("No Hunger", false));
        addSetting(new ToggleSetting("Automatically Hide Hotbar", false));
    }
    
    @Subscribe
    public void onTick(EventTick e)
    {
        ToggleSetting autoHideHotbar = (ToggleSetting) getSetting("Automatically Hide Hotbar");
        
        if (autoHideHotbar.enabled)
        {
            if (currentItem != mc.player.inventory.currentItem || flag)
            {
                ticks = 60;
                flag = false;
            }
            
            if (ticks != 0)
            {
                ticks--;
            }
            
            currentItem = mc.player.inventory.currentItem;
        }
    }
}
