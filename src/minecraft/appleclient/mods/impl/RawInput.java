package appleclient.mods.impl;

import com.google.common.eventbus.Subscribe;

import appleclient.mods.Mod;
import appleclient.mods.events.impl.EventWorldChange;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Mouse;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.Util;

public class RawInput extends Mod
{
    private volatile boolean enabled = false;
    private int deltaX = 0, deltaY = 0;
    private Controller[] controllers;
    private Mouse mouse;
    
    public RawInput()
    {
        super("Raw Input");
    }
    
    @Override
    public void onEnable()
    {
        super.onEnable();
        setupInput();
    }
    
    @Subscribe
    public void onWorldChange(EventWorldChange e)
    {
        setupInput();
    }
    
    public void setupInput()
    {
        if (!enabled && !Util.getOSType().equals(Util.EnumOS.UNKNOWN) && mc.thePlayer != null)
        {
            enabled = true;
            mc.mouseHelper = new RawMouseHelper();
            controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
            
            new Thread(() ->
            {
                while (enabled)
                {
                    if (mouse != null)
                    {
                        mouse.poll();
                        
                        if (mc.currentScreen == null)
                        {
                            deltaX += (int) mouse.getX().getPollData();
                            deltaY += (int) mouse.getY().getPollData();
                        }
                    }
                    
                    else
                    {
                        for (Controller controller : controllers)
                        {
                            if (controller.getType() == Type.MOUSE)
                            {
                                Mouse controllerMouse = (Mouse) controller;
                                controllerMouse.poll();
                                
                                if (controllerMouse.getX().getPollData() != 0.0F || controllerMouse.getY().getPollData() != 0.0F)
                                {
                                    mouse = controllerMouse;
                                    break;
                                }
                            }
                        }
                    }
                    
                    try
                    {
                        Thread.sleep(1);
                    } catch (Exception e) {}
                }
            }).start();
        }
    }
    
    @Override
    public void onDisable()
    {
        super.onDisable();
        
        if (enabled && !Util.getOSType().equals(Util.EnumOS.UNKNOWN))
        {
            mc.mouseHelper = new MouseHelper();
            enabled = false;
        }
    }
    
    public class RawMouseHelper extends MouseHelper
    {
        private RawInput rawInput = RawInput.this;
        
        @Override
        public void mouseXYChange()
        {
           deltaX = rawInput.deltaX;
           rawInput.deltaX = 0;
           deltaY = -rawInput.deltaY;
           rawInput.deltaY = 0;
        }
     }
}