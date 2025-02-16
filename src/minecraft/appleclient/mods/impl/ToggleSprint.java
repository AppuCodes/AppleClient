package appleclient.mods.impl;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.google.common.eventbus.Subscribe;

import appleclient.events.impl.EventKey;
import appleclient.events.impl.EventRender;
import appleclient.events.impl.EventTick;
import appleclient.mods.Mod;
import appleclient.settings.impl.ToggleSetting;
import net.minecraft.client.gui.Gui;

public class ToggleSprint extends Mod
{
    private boolean toggled = true, flag = true;
    
    public ToggleSprint()
    {
        super("Toggle Sprint", "Allows you to toggle sprinting.");
        setupSettings(2);
        addSetting(new ToggleSetting("Show Status", true));
        addSetting(new ToggleSetting("Background", true));
    }
    
    @Subscribe
    public void onTick(EventTick e)
    {
        if (toggled)
        {
            if (!mc.options.keyBindSprint.isKeyDown())
            {
                mc.options.keyBindSprint.setKeyBindState(mc.options.keyBindSprint.getKeyCode(), true);
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
                if (mc.options.keyBindSprint.isKeyDown() && !Keyboard.isKeyDown(mc.options.keyBindSprint.getKeyCode()))
                {
                    mc.options.keyBindSprint.setKeyBindState(mc.options.keyBindSprint.getKeyCode(), false);
                }
                
                flag = false;
            }
        }
    }
    
    @Subscribe
    public void onKey(EventKey e)
    {
        if (e.key == mc.options.keyBindSprint.getKeyCode())
        {
            toggled = !toggled;
        }
    }
    
    @Subscribe
    public void onRender(EventRender e)
    {
        ToggleSetting showStatus = (ToggleSetting) getSetting("Show Status");
        
        if (showStatus.enabled)
        {
            ToggleSetting background = (ToggleSetting) getSetting("Background");
            
            if (toggled)
            {
                int width = mc.fontRendererObj.getStringWidth("§7[§6Sprinting§7] §rToggled");
                
                if (background.enabled)
                {
                    Gui.drawRect(0, 0, width + 4, 12, new Color(0, 0, 0, 128).getRGB());
                }
                
                mc.fontRendererObj.drawStringWithShadow("§7[§6Sprinting§7] §rToggled", 2, 2, -1);
            }
            
            else if (mc.player.isSprinting())
            {
                int width = mc.fontRendererObj.getStringWidth("§7[§6Sprinting§7] §rVanilla");
                
                if (background.enabled)
                {
                    Gui.drawRect(0, 0, width + 4, 12, new Color(0, 0, 0, 128).getRGB());
                }
                
                mc.fontRendererObj.drawStringWithShadow("§7[§6Sprinting§7] §rVanilla", 2, 2, -1);
            }
        }
    }
}
