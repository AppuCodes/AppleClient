package appleclient.mods;

import java.util.ArrayList;

import appleclient.mods.impl.*;

public class ModsManager
{
    public ArrayList<Mod> mods = new ArrayList<>();
    
    public ModsManager()
    {
        setupMods();
    }
    
    public void setupMods()
    {
        mods.add(new ToggleSprint());
        mods.add(new RawInput());
        mods.add(new Crosshair());
        mods.add(new BlockSelection());
    }
    
    public Mod getMod(String name)
    {
        Mod result = null;
        
        for (Mod mod : mods)
        {
            if (mod.name.equals(name))
            {
                result = mod;
                break;
            }
        }
        
        return result;
    }
}
