package appleclient.mods.impl;

import com.google.common.eventbus.Subscribe;

import appleclient.events.impl.EventPacketReceive;
import appleclient.events.impl.EventWorldChange;
import appleclient.mods.Mod;
import appleclient.mods.settings.TextSetting;
import net.minecraft.network.play.server.S02PacketChat;

public class AutoGG extends Mod
{
    private String[] triggers = new String[] {"1st Killer - ", "1st Place - ", "Winner: ", " - Damage Dealt - ", "Winning Team -", "1st - ", "Winners: ", "Winner: ", "Winning Team: ", " won the game!", "Top Seeker: ", "1st Place: ", "Last team standing!", "Winner #1 (", "Top Survivors", "Winners - ", "Sumo Duel - "};
    private boolean canGG = true;
    
    public AutoGG()
    {
        super("Auto GG", "Automatically says GG in chat after a game ends.");
        setupSettings(1);
        addSetting(new TextSetting("GG Message", "GG"));
    }
    
    @Subscribe
    public void onPacketReceive(EventPacketReceive e)
    {
        if (e.packet instanceof S02PacketChat && mc.getCurrentServerData() != null && canGG)
        {
            S02PacketChat packet = (S02PacketChat) e.packet;
            String message = packet.getChatComponent().getUnformattedText();
            
            if (message.startsWith(" "))
            {
                for (String trigger : triggers)
                {
                    if (message.contains(trigger))
                    {
                        TextSetting textSetting = (TextSetting) getSetting("GG Message");
                        mc.thePlayer.sendChatMessage("/ac " + textSetting.text);
                        canGG = false;
                        break;
                    }
                }
            }
        }
    }
    
    @Subscribe
    public void onWorldChange(EventWorldChange e)
    {
        canGG = true;
    }
}