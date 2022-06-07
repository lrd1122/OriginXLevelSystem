package gx.lrd1122.OriginXLevelSystem.OriginXAPI;

import gx.lrd1122.OriginXLevelSystem.OriginXConfigCore.OxConfigManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OriginXYamlAPI {
    public static void setObject(YamlConfiguration config, File file, Object value, String key){
        config.set(key, value);
        try {
            config.save(file);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void setObject(YamlConfiguration config, File file, Object value, String key, String... section){
        ConfigurationSection yamlSection = config;
        for(int i = 0 ; i < section.length; i++){
            yamlSection = yamlSection.getConfigurationSection(section[i]);
        }
        yamlSection.set(key, value);
        try {
            config.save(file);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static String getLangString(String key){
        return OxConfigManager.readConfigString(OxConfigManager.LangYaml, key);
    }
    public static List<String> getLangStringList(String key){
        return OxConfigManager.readConfigStringList(OxConfigManager.LangYaml, key);
    }
}
