package appleclient.settings.impl;

import appleclient.settings.Setting;

public class ToggleSetting extends Setting
{
    public boolean enabled;
    
    public ToggleSetting(String name, boolean enabled)
    {
        super(name);
        this.enabled = enabled;
    }
}
