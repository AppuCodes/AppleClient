package appleclient;

import java.util.regex.Pattern;

import org.lwjgl.input.Keyboard;

import appleclient.config.AppleConfig;
import appleclient.events.EventBus;
import appleclient.events.impl.EventKey;
import appleclient.events.impl.EventPacketReceive;
import appleclient.gui.GuiModsList;
import appleclient.interfaces.IMinecraft;
import appleclient.mods.ModsManager;
import net.minecraft.network.play.server.S02PacketChat;

public class Apple implements IMinecraft
{
    public static EventBus eventBus;
    public static AppleConfig config;
    public static ModsManager modsManager;
    public static Pattern pattern = Pattern.compile("^\\+\\d+ Gold(?: \\(Critical Hit\\))?$");
    
    public static void initialize()
    {
        eventBus = new EventBus();
        modsManager = new ModsManager();
        config = new AppleConfig();
        // eventBus.register(SprintDesync.FIX);
    }
    
    public static void onKey(EventKey e)
    {
        if (e.key == Keyboard.KEY_RSHIFT)
        {
            mc.displayGuiScreen(new GuiModsList());
        }
    }
    
    public static void onPacketReceive(EventPacketReceive e)
    {
        if (e.packet instanceof S02PacketChat && mc.getCurrentServerData() != null)
        {
            S02PacketChat packet = (S02PacketChat) e.packet;
            String message = packet.getChatComponent().getUnformattedText();
            e.cancelled = pattern.matcher(message).matches(); // cleaner chat
        }
    }
}
