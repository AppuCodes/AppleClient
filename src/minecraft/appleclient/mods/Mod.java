package appleclient.mods;

import appleclient.Apple;
import appleclient.interfaces.IMinecraft;

public class Mod implements IMinecraft
{
    private boolean enabled = false;
    public String name;
    
    public Mod(String name)
    {
        this.name = name;
    }
    
    public void onEnable()
    {
        enabled = true;
        Apple.CLIENT.eventBus.register(this);
    }
    
    public void onDisable()
    {
        enabled = false;
        Apple.CLIENT.eventBus.unregister(this);
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
}