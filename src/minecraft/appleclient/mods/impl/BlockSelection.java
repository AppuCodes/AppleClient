package appleclient.mods.impl;

import appleclient.mods.Mod;
import appleclient.mods.settings.ColorSetting;
import appleclient.mods.settings.SliderSetting;

public class BlockSelection extends Mod
{
    public BlockSelection()
    {
        super("Block Selection", "Customize the block selection!");
        setupSettings(2);
        addSetting(new SliderSetting("Outline Thickness", 1, 2, 5));
        addSetting(new ColorSetting("Outline Color", 0, 0, 0, 102));
    }
}
