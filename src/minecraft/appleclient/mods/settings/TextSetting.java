package appleclient.mods.settings;

public class TextSetting extends Setting
{
    public String text;
    
    public TextSetting(String name, String text)
    {
        super(name);
        this.text = text;
    }
}