package appleclient.utils;

import net.minecraft.client.Minecraft;

public class ServerUtil
{
    private static long lastPing = 0;
    
    public static long getPing()
    {
        if (Minecraft.getMinecraft().isIntegratedServerRunning() || Minecraft.getMinecraft().getCurrentServerData() == null)
            return 0;
        
        long ping = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().player.getGameProfile().getId()).getResponseTime();
        
        if (ping == 0)
            return lastPing;
        
        lastPing = ping;
        return ping;
    }
}
