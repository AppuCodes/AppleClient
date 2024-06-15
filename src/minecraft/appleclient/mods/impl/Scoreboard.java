package appleclient.mods.impl;

import appleclient.mods.Mod;
import appleclient.mods.settings.ToggleSetting;

public class Scoreboard extends Mod
{
    public Scoreboard()
    {
        super("Scoreboard", "Customize the scoreboard!");
        setupSettings(2);
        addSetting(new ToggleSetting("Text Shadow", false));
        addSetting(new ToggleSetting("Show Numbers", true));
    }
}
