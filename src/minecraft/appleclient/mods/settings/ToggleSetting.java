package appleclient.mods.settings;

public class ToggleSetting extends Setting
{
    public boolean enabled;
    
    public ToggleSetting(String name, boolean enabled)
    {
        super(name);
        this.enabled = enabled;
    }
}
