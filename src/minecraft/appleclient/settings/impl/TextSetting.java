package appleclient.settings.impl;

import appleclient.settings.Setting;

public class TextSetting extends Setting
{
    public String text;
    
    public TextSetting(String name, String text)
    {
        super(name);
        this.text = text;
    }
}
