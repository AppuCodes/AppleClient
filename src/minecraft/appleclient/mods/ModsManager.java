package appleclient.mods;

import java.util.ArrayList;

import appleclient.mods.impl.RawInput;
import appleclient.mods.impl.ToggleSprint;

public class ModsManager
{
    public ArrayList<Mod> mods = new ArrayList<>();
    
    public void setupMods()
    {
        mods.add(new ToggleSprint());
        mods.add(new RawInput());
        mods.get(0).setEnabled(true);
        mods.get(1).setEnabled(true);
    }
    
    public Mod findMod(String name)
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