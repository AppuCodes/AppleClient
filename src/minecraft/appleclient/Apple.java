package appleclient;

import org.lwjgl.input.Keyboard;

import com.google.common.eventbus.Subscribe;

import appleclient.bugfixes.SprintDesync;
import appleclient.config.AppleConfig;
import appleclient.events.EventBus;
import appleclient.events.impl.EventKey;
import appleclient.events.impl.EventPacketReceive;
import appleclient.gui.GuiModsList;
import appleclient.interfaces.IMinecraft;
import appleclient.mods.ModsManager;
import net.minecraft.entity.DataWatcher;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;

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
        // eventBus.register(SprintDesync.FIX);
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
