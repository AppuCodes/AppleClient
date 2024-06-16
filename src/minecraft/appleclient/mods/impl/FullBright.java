package appleclient.mods.impl;

import appleclient.mods.Mod;

public class FullBright extends Mod
{
    public FullBright()
    {
        super("Full Bright", "Disables lighting.");
    }
    
    @Override
    public void onEnable()
    {
        super.onEnable();
        mc.renderGlobal.loadRenderers();
    }
    
    @Override
    public void onDisable()
    {
        super.onDisable();
        mc.renderGlobal.loadRenderers();
    }
}
