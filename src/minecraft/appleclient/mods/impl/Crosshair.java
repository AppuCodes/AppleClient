package appleclient.mods.impl;

import appleclient.mods.Mod;
import appleclient.settings.impl.ToggleSetting;

public class Crosshair extends Mod
{
    public Crosshair()
    {
        super("Crosshair", "Changes how the crosshair looks.");
        setupSettings(2);
        addSetting(new ToggleSetting("Transparency", true));
        addSetting(new ToggleSetting("Show 3rd Person", false));
    }
}
