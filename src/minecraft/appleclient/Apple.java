package appleclient;

import org.lwjgl.input.Keyboard;

import com.google.common.eventbus.Subscribe;

import appleclient.gui.GuiModsList;
import appleclient.interfaces.IMinecraft;
import appleclient.mods.ModsManager;
import appleclient.mods.events.EventBus;
import appleclient.mods.events.impl.EventKey;

public enum Apple implements IMinecraft
{
    CLIENT;
    
    public EventBus eventBus;
    public ModsManager modsManager;
    
    public void initialize()
    {
        modsManager = new ModsManager();
        eventBus = new EventBus();
        modsManager.setupMods();
        eventBus.register(this);
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