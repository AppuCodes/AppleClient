package appleclient.mods.impl;

import org.lwjgl.opengl.Display;

import appleclient.mods.Mod;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Mouse;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.Util;

public class RawInput extends Mod
{
    private volatile boolean enabled = false;
    private float deltaX = 0, deltaY = 0;
    private Controller[] controllers;
    private Mouse mouse;
    
    public RawInput()
    {
        super("Raw Input", "Disables mouse acceleration. Recommended on.");
        toggle();
    }
    
    @Override
    public void onEnable()
    {
        super.onEnable();
        setupInput();
    }
    
    public void setupInput()
    {
        if (!enabled && !Util.getOSType().equals(Util.EnumOS.UNKNOWN))
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
                        
                        if (mc.currentScreen == null && Display.isActive())
                        {
                            deltaX += mouse.getX().getPollData();
                            deltaY += mouse.getY().getPollData();
                            
                            /* framerate independent */
                            if (mc.inGameHasFocus && !mc.options.smoothCamera && (deltaX != 0 || deltaY != 0))
                            {
                                mc.mouseHelper.mouseXYChange();
                                
                                float f = (mc.options.mouseSensitivity * 0.6F) + 0.2F,
                                      f1 = f * f * f * 8,
                                      f2 = mc.mouseHelper.deltaX * f1,
                                      f3 = mc.mouseHelper.deltaY * f1;
                                
                                mc.entityRenderer.smoothCamYaw = mc.entityRenderer.smoothCamPitch = 0;
                                mc.player.setAngles(f2, mc.options.invertMouse ? (f3 * -1) : f3);
                            }
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
                    }
                    catch (InterruptedException e) {}
                }
            }).start();
        }
    }
    
    @Override
    public void onDisable()
    {
        super.onDisable();
        
        if (enabled)
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
