package appleclient.mods;

import appleclient.mods.impl.*;

public class ModsManager
{
    public static final int COUNT = 17;
    public Mod[] mods = new Mod[COUNT];
    private int i = 0;
    
    public void setupMods()
    {
        add(new AutoGG(), new BlockSelection(), new Crosshair(),
            new DepthSkins(), new NoHurtCam(), new FOVChanger(),
            new FullBright(), new HotbarTweaks(), new Nametags(),
            new NoBobShake(), new PingIndicator(), new RawInput(),
            new Scoreboard(), new ShinyPots(), new ToggleSprint(),
            new SmoothChat(), new EntityPredictor());
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
            case "Entity Predictor": return mods[16];
            default: return null;
        }
    }
    
    private void add(Mod... mods)
    {
        for (Mod mod : mods)
        {
            this.mods[i++] = mod;
        }
    }
    
    public ModsManager() { setupMods(); }
}
