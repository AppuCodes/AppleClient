package appleclient;

import org.lwjgl.input.Keyboard;

import com.google.common.eventbus.Subscribe;

import appleclient.config.AppleConfig;
import appleclient.gui.GuiModsList;
import appleclient.interfaces.IMinecraft;
import appleclient.mods.ModsManager;
import appleclient.mods.events.EventBus;
import appleclient.mods.events.impl.EventKey;

public enum Apple implements IMinecraft
{
    CLIENT;
    
    public EventBus eventBus;
    public AppleConfig config;
    public ModsManager modsManager;
    
    public void initialize()
    {
        eventBus = new EventBus();
        eventBus.register(this);
        modsManager = new ModsManager();
        config = new AppleConfig();
    }
    
    @Subscribe
    public void onKey(EventKey e)
    {
        if (e.key == Keyboard.KEY_RSHIFT)
        {
            mc.displayGuiScreen(new GuiModsList());
        }
    }
}
