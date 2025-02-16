package appleclient.mods;

import appleclient.Apple;
import appleclient.interfaces.IMinecraft;
import appleclient.settings.Setting;

public class Mod implements IMinecraft
{
    public String name, description;
    private boolean enabled = false;
    public Setting[] settings;
    private int index = 0;
    
    public Mod(String name, String description)
    {
        this.name = name;
        this.description = description;
    }
    
    public void onEnable()
    {
        enabled = true;
        Apple.eventBus.register(this);
    }
    
    public void onDisable()
    {
        enabled = false;
        Apple.eventBus.unregister(this);
    }
    
    public void toggle()
    {
        setEnabled(!isEnabled());
    }
    
    public boolean isEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(boolean enabled)
    {
        if (enabled && !this.enabled)
        {
            onEnable();
        }
        
        else if (!enabled && this.enabled)
        {
            onDisable();
        }
    }
    
    public Setting getSetting(String name)
    {
        Setting result = null;
        
        for (Setting setting : settings)
        {
            if (setting.name.equals(name))
            {
                result = setting;
                break;
            }
        }
        
        return result;
    }
    
    protected void setupSettings(int number)
    {
        settings = new Setting[number];
    }
    
    protected void addSetting(Setting setting)
    {
        settings[index] = setting;
        index++;
    }
}
