package appleclient.settings.impl;

import appleclient.settings.Setting;

public class SliderSetting extends Setting
{
    public float minValue, currentValue, maxValue;
    
    public SliderSetting(String name, float minValue, float currentValue, float maxValue)
    {
        super(name);
        this.minValue = minValue;
        this.currentValue = currentValue;
        this.maxValue = maxValue;
    }
}
