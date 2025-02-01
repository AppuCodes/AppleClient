package appleclient.mods.impl;

import appleclient.mods.Mod;
import appleclient.settings.impl.SliderSetting;

/* useful for zombies */
public class EntityPredictor extends Mod
{
    public EntityPredictor()
    {
        super("Entity Predictor", "Shows entities' true positions, based on your ping.");
        setupSettings(1);
        addSetting(new SliderSetting("Ping", 0, 150, 500));
    }
}
