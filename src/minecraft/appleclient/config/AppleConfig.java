package appleclient.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import appleclient.Apple;
import appleclient.mods.Mod;
import appleclient.settings.Setting;
import appleclient.settings.impl.ColorSetting;
import appleclient.settings.impl.SliderSetting;
import appleclient.settings.impl.TextSetting;
import appleclient.settings.impl.ToggleSetting;

public class AppleConfig
{
    private Gson gson = new Gson(), prettyGson = new GsonBuilder().setPrettyPrinting().create();
    private File config = new File("optionsac.txt");
    private JsonParser jsonParser = new JsonParser();
    
    public AppleConfig()
    {
        if (config.exists())
        {
            loadMods();
        }
    }
    
    public void saveMods()
    {
        if (!config.exists())
        {
            try
            {
                config.createNewFile();
            }
            
            catch (IOException e)
            {
            }
        }
        
        JsonObject jsonObject = new JsonObject();
        
        for (Mod mod : Apple.modsManager.mods)
        {
            JsonObject jsonMod = new JsonObject();
            jsonMod.addProperty("Enabled", mod.isEnabled());
            jsonObject.add(mod.name, jsonMod);
            
            if (mod.settings != null)
            {
                for (Setting setting : mod.settings)
                {
                    if (setting instanceof ToggleSetting)
                    {
                        ToggleSetting toggleSetting = (ToggleSetting) setting;
                        jsonMod.addProperty(setting.name, toggleSetting.enabled);
                    }

                    else if (setting instanceof SliderSetting)
                    {
                        SliderSetting sliderSetting = (SliderSetting) setting;
                        jsonMod.addProperty(setting.name, sliderSetting.currentValue);
                    }
                    
                    else if (setting instanceof ColorSetting)
                    {
                        ColorSetting colorSetting = (ColorSetting) setting;
                        jsonMod.addProperty(setting.name, colorSetting.red + ";" + colorSetting.green + ";" + colorSetting.blue + ";" + colorSetting.alpha);
                    }
                    
                    else if (setting instanceof TextSetting)
                    {
                        TextSetting textSetting = (TextSetting) setting;
                        jsonMod.addProperty(setting.name, textSetting.text);
                    }
                }
            }
        }
        
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(config)))
        {
            printWriter.println(prettyGson.toJson(jsonObject));
        }
        
        catch (IOException e)
        {
        }
    }
    
    public void loadMods()
    {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(config)))
        {
            JsonObject jsonObject = (JsonObject) jsonParser.parse(bufferedReader);
            Iterator iterator = jsonObject.entrySet().iterator();
            
            while (iterator.hasNext())
            {
                Entry entry = (Entry) iterator.next();
                String modName = (String) entry.getKey();
                Mod mod = Apple.modsManager.getMod(modName);
                JsonObject jsonMod = (JsonObject) entry.getValue();
                
                if (jsonMod.get("Enabled").getAsBoolean())
                {
                    mod.setEnabled(true);
                }
                
                else if (mod.isEnabled())
                {
                    mod.setEnabled(false);
                }
                
                if (mod.settings != null)
                {
                    for (Setting setting : mod.settings)
                    {
                        JsonElement element = jsonMod.get(setting.name);
                        
                        if (setting instanceof ToggleSetting)
                        {
                            ToggleSetting toggleSetting = (ToggleSetting) setting;
                            toggleSetting.enabled = element.getAsBoolean();
                        }

                        else if (setting instanceof SliderSetting)
                        {
                            SliderSetting sliderSetting = (SliderSetting) setting;
                            sliderSetting.currentValue = element.getAsFloat();
                        }
                        
                        else if (setting instanceof ColorSetting)
                        {
                            ColorSetting colorSetting = (ColorSetting) setting;
                            String[] colors = element.getAsString().split(";");
                            colorSetting.red = Integer.parseInt(colors[0]);
                            colorSetting.green = Integer.parseInt(colors[1]);
                            colorSetting.blue = Integer.parseInt(colors[2]);
                            colorSetting.alpha = Integer.parseInt(colors[3]);
                        }
                        
                        else if (setting instanceof TextSetting)
                        {
                            TextSetting textSetting = (TextSetting) setting;
                            textSetting.text = element.getAsString();
                        }
                    }
                }
            }
        }
        
        catch (Exception e)
        {
        }
    }
}
