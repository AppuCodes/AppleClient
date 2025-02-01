package appleclient.mods.impl;

import appleclient.mods.Mod;
import appleclient.settings.impl.ToggleSetting;

public class FOVChanger extends Mod
{
    public FOVChanger()
    {
        super("FOV Changer", "Allows you to set when to change the FOV.");
        setupSettings(1);
        addSetting(new ToggleSetting("Bow Pull Change", true));
    }
}
