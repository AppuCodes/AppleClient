package appleclient.utils;

import appleclient.Apple;
import appleclient.mods.impl.EntityPredictor;
import appleclient.settings.impl.SliderSetting;
import net.minecraft.client.Minecraft;

public class ServerUtil
{
    private static long lastPing = 0;
    
    public static long getPing()
    {
        if (Minecraft.getMinecraft().isIntegratedServerRunning() || Minecraft.getMinecraft().getCurrentServerData() == null)
            return 0;
        
        long ping = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().player.getGameProfile().getId()).getResponseTime();
        
        if (ping != 0)
            lastPing = ping;
        
        EntityPredictor predictor = (EntityPredictor) Apple.modsManager.getMod("Entity Predictor");
        return lastPing == 0 || lastPing == 1 ? (int) ((SliderSetting) predictor.getSetting("Ping")).currentValue : lastPing;
    }
}
