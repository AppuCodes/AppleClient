package appleclient.mods;

import java.util.Arrays;
import java.util.Comparator;

import appleclient.mods.impl.*;

public class ModsManager
{
    public Mod[] mods;
    
    public ModsManager()
    {
        setupMods(16);
    }
    
    public void setupMods(int count)
    {
        int i = 0;
        mods = new Mod[count];
        mods[i++] = new AutoGG();
        mods[i++] = new BlockSelection();
        mods[i++] = new Crosshair();
        mods[i++] = new DepthSkins();
        mods[i++] = new NoHurtCam();
        mods[i++] = new FOVChanger();
        mods[i++] = new FullBright();
        mods[i++] = new HotbarTweaks();
        mods[i++] = new Nametags();
        mods[i++] = new NoBobShake();
        mods[i++] = new PingIndicator();
        mods[i++] = new RawInput();
        mods[i++] = new Scoreboard();
        mods[i++] = new ShinyPots();
        mods[i++] = new ToggleSprint();
        mods[i++] = new SmoothChat();
    }
    
    public Mod getMod(String name)
    {
        switch (name)
        {
            case "Auto GG": return mods[0];
            case "Block Selection": return mods[1];
            case "Crosshair": return mods[2];
            case "3D Skins": return mods[3];
            case "No Hurt Cam": return mods[4];
            case "FOV Changer": return mods[5];
            case "Full Bright": return mods[6];
            case "Hotbar Tweaks": return mods[7];
            case "Nametags": return mods[8];
            case "No Bob Shake": return mods[9];
            case "Ping Indicator": return mods[10];
            case "Raw Input": return mods[11];
            case "Scoreboard": return mods[12];
            case "Shiny Pots": return mods[13];
            case "Toggle Sprint": return mods[14];
            case "Smooth Chat": return mods[15];
            default: return null;
        }
    }
}
