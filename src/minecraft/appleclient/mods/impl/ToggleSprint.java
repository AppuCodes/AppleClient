package appleclient.mods.impl;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.google.common.eventbus.Subscribe;

import appleclient.mods.Mod;
import appleclient.mods.events.impl.EventKey;
import appleclient.mods.events.impl.EventRender;
import appleclient.mods.events.impl.EventTick;

public class ToggleSprint extends Mod
{
    private boolean toggled = true, flag = true;
    
    public ToggleSprint()
    {
        super("Toggle Sprint");
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
            mc.fontRendererObj.drawStringWithShadow("�7[           �7] �rToggled", 2, 2, -1);
            mc.fontRendererObj.drawStringWithShadow("Sprinting", 6, 2, Color.ORANGE.getRGB());
        }
        
        else if (mc.thePlayer.isSprinting())
        {
            mc.fontRendererObj.drawStringWithShadow("�7[           �7] �rVanilla", 2, 2, -1);
            mc.fontRendererObj.drawStringWithShadow("Sprinting", 6, 2, Color.ORANGE.getRGB());
        }
    }
}