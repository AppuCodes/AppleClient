package appleclient.mods.impl;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.google.common.eventbus.Subscribe;

import appleclient.events.impl.EventKey;
import appleclient.events.impl.EventRender;
import appleclient.events.impl.EventTick;
import appleclient.mods.Mod;

public class ToggleSprint extends Mod
{
    private boolean toggled = true, flag = true;
    
    public ToggleSprint()
    {
        super("Toggle Sprint", "Allows you to toggle sprinting.");
    }
    
    @Subscribe
    public void onTick(EventTick e)
    {
        if (toggled)
        {
            if (!mc.gameSettings.keyBindSprint.isKeyDown())
            {
                mc.gameSettings.keyBindSprint.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
            }
            
            if (!flag)
            {
                flag = true;
            }
        }
        
        else
        {
            if (flag)
            {
                if (mc.gameSettings.keyBindSprint.isKeyDown() && !Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode()))
                {
                    mc.gameSettings.keyBindSprint.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
                }
                
                flag = false;
            }
        }
    }
    
    @Subscribe
    public void onKey(EventKey e)
    {
        if (e.key == mc.gameSettings.keyBindSprint.getKeyCode())
        {
            toggled = !toggled;
        }
    }
    
    @Subscribe
    public void onRender(EventRender e)
    {
        if (toggled)
        {
            mc.fontRendererObj.drawStringWithShadow("§7[§6Sprinting§7] §rToggled", 2, 2, -1);
        }
        
        else if (mc.thePlayer.isSprinting())
        {
            mc.fontRendererObj.drawStringWithShadow("§7[§6Sprinting§7] §rVanilla", 2, 2, -1);
        }
    }
}
