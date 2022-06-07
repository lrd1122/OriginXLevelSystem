package gx.lrd1122.OriginXLevelSystem.OriginXConfigCore;

import gx.lrd1122.OriginXLevelSystem.OriginXAPI.OriginXJavaAPI;
import gx.lrd1122.OriginXLevelSystem.OriginXAPI.OriginXWindowsAPI;
import gx.lrd1122.OriginXLevelSystem.OriginXAPI.OxMinecraftAPI.OriginXMinecraftAPI;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.AttributeSettings.OxAttributeManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.AttributeSettings.OxAttributeSettings;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelGroup;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelSettings;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelStorageType;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelSystemManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLoggerCore.OxLoggerManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelSystem;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OxConfigManager {
    public static String prefix;
    public static String lang;

    public static File ConfigFile;
    public static YamlConfiguration ConfigYaml;
    public static File LangFile;
    public static YamlConfiguration LangYaml;

    public static File playerDataDir;
    public static OxLevelStorageType storageType;



    public static void initialize(){
        JavaPlugin plugin = OriginXLevelSystem.plugin;
        File dataFolder = plugin.getDataFolder();
        //config
        boolean setLang = false;
        File configFile = new File(dataFolder, "config.yml");
        if(!configFile.exists()) {
            setLang = true;
        }
        OxConfigUtils.CreateDefaultFile(dataFolder, "config.yml", "config.yml");
        OxConfigManager.ConfigFile = configFile;
        OxConfigManager.ConfigYaml = YamlConfiguration.loadConfiguration(configFile);
        if(setLang){
            ConfigYaml.set("Language", OriginXWindowsAPI.getWindowsLanguage().name());
            try {
                ConfigYaml.save(ConfigFile);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        OxConfigManager.prefix = OriginXMinecraftAPI.ColorString(ConfigYaml.getString("Prefix"));
        OxConfigManager.lang = ConfigYaml.getString("Language");
        OxLoggerManager.info("&aLoaded config.yml √");
        //lang
        File langParent = new File(dataFolder, "Languages");
        if(!langParent.exists()) {
            langParent.mkdir();
            OxConfigUtils.CreateDefaultFile(langParent, lang + ".yml", "Languages\\" + lang + ".yml");
        }
        File langFile = new File(langParent, lang + ".yml");
        OxConfigManager.LangFile = langFile;
        OxConfigManager.LangYaml = YamlConfiguration.loadConfiguration(langFile);
        OxLoggerManager.info("&aLoaded " + lang + ".yml √");
        //levelGroup
        File groupParent = new File(dataFolder, "LevelGroups");
        if(!groupParent.exists()) {
            groupParent.mkdir();
            OxConfigUtils.CreateDefaultFile(langParent, "ExampleGroup.yml", "LevelGroups\\ExampleGroup.yml");
        }
        for(File file: groupParent.listFiles()){
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
            for(String key : yaml.getKeys(false)) {
                ConfigurationSection section = yaml.getConfigurationSection(key);
                OxLevelGroup group = new OxLevelGroup();
                group.setName(section.getString("Group"));
                group.setExpMutiply(section.getDouble("ExpMultiply"));
                group.setReplaceOrigin(section.getBoolean("ReplaceOrigin"));
                group.setDefaultPickup(section.getBoolean("DefaultPickUp"));
                HashMap<Integer, OxLevelSettings> levelSettings = new HashMap<>();
                HashMap<Integer, OxAttributeSettings> attributeSettings = new HashMap<>();
                for (String level : section.getConfigurationSection("LevelSettings").getKeys(false)) {
                    String value = section.getConfigurationSection("LevelSettings").getString(level);
                    int minAdd;
                    int maxAdd;
                    if (level.contains("-")) {
                        String[] wholeLevel = level.split("-");
                        minAdd = Integer.parseInt(wholeLevel[0]);
                        maxAdd = Integer.parseInt(wholeLevel[1]);
                    } else {
                        minAdd = Integer.parseInt(level);
                        maxAdd = Integer.parseInt(level);
                    }
                    OxLevelSettings settings = new OxLevelSettings();
                    HashMap<String, String> args = OriginXJavaAPI.getArgs(value);
                    String parseValue = value.replaceAll("[\\(|（].*[\\)|）]$", "");
                    if (args != null && args.containsKey("Permission")) {
                        List<String> array = new ArrayList<>();
                        array.add(args.get("Permission"));
                        settings.setPermission(array);
                    }

                    for(int i = minAdd; i <= maxAdd ; i++){
                        ScriptEngineManager manager = new ScriptEngineManager();
                        ScriptEngine scriptEngine = manager.getEngineByName("js");
                        try {
                            parseValue = String.valueOf(scriptEngine.eval(parseValue.replace("$level$", String.valueOf(i)).replace(" ", "")));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        settings.setNeedExp(Integer.parseInt(parseValue));
                        levelSettings.put(i, settings);
                    }
                }
                for (String attr : section.getConfigurationSection("AttributeSettings").getKeys(false)) {
                    List<String> value = section.getConfigurationSection("AttributeSettings").getStringList(attr);
                    int minAdd;
                    int maxAdd;
                    if (attr.contains("-")) {
                        String[] wholeLevel = attr.split("-");
                        minAdd = Integer.parseInt(wholeLevel[0]);
                        maxAdd = Integer.parseInt(wholeLevel[1]);
                    } else {
                        minAdd = Integer.parseInt(attr);
                        maxAdd = Integer.parseInt(attr);
                    }
                    OxAttributeSettings settings = new OxAttributeSettings();
                    settings.setValues(value);
                    for(int i = minAdd; i <= maxAdd ; i++){
                        attributeSettings.put(i, settings);
                    }
                }
                group.setLevelSettings(levelSettings);
                group.setAttributeSettings(attributeSettings);
                List<Integer> list = new ArrayList<>(group.getLevelSettings().keySet());
                Collections.sort(list);
                group.setLevelList(list);

                OxLevelSystemManager.groupMap.put(group.getName(), group);
            }
        }

        //PlayerData
        File dataParent = new File(dataFolder, "PlayerData");
        if(!dataParent.exists()) {
            dataParent.mkdir();
        }
        playerDataDir = dataParent;
        //mysql
        storageType = OxLevelStorageType.valueOf(ConfigYaml.getString("Storage").toUpperCase());
        if(storageType.equals(OxLevelStorageType.MYSQL)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        Class.forName(ConfigYaml.getString("Mysql.Class"));
                        OxLevelSystemManager.mysqlHost = ConfigYaml.getString("Mysql.Host");
                        OxLevelSystemManager.mysqlPort = ConfigYaml.getString("Mysql.Port");
                        OxLevelSystemManager.mysqlDatabase = ConfigYaml.getString("Mysql.Database");
                        OxLevelSystemManager.mysqlUser = ConfigYaml.getString("Mysql.User");
                        OxLevelSystemManager.mysqlPassword = ConfigYaml.getString("Mysql.Password");
                        OxLevelSystemManager.mysqlUseSSL = String.valueOf(ConfigYaml.getBoolean("Mysql.useSSL"));
                        OxLevelSystemManager.mysqlAddress = "jdbc:mysql://"
                                + OxLevelSystemManager.mysqlHost
                                + ":"
                                + OxLevelSystemManager.mysqlPort
                                + "/"
                                + OxLevelSystemManager.mysqlDatabase
                                + "?useSSL="
                                + OxLevelSystemManager.mysqlUseSSL
                                + "&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                        Connection connection = DriverManager.getConnection(
                                OxLevelSystemManager.mysqlAddress, OxLevelSystemManager.mysqlUser, OxLevelSystemManager.mysqlPassword
                        );
                        Statement statement = connection.createStatement();
                        statement.execute(
                                "CREATE TABLE IF NOT EXISTS originxlevelsystem(" +
                                        "UUID VARCHAR(255) PRIMARY KEY NOT NULL, " +
                                        "Name TEXT NOT NULL, " +
                                        "PlayerTotalExp DOUBLE NOT NULL DEFAULT 0, " +
                                        "PlayerExp DOUBLE NOT NULL DEFAULT 0, " +
                                        "PlayerLevel DOUBLE NOT NULL DEFAULT 0, " +
                                        "PlayerGroup TEXT NOT NULL" +
                                        ");");
                        statement.close();
                        connection.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        OxLoggerManager.info("数据库连接失败,已切换为YAML存储");
                        storageType = OxLevelStorageType.YAML;
                    }
                }
            }.runTaskAsynchronously(OriginXLevelSystem.plugin);
        }
        //Attribute
        if(OxConfigManager.ConfigYaml.getBoolean("AttributePlugin.SXAttribute"))
            OxAttributeManager.isSXAttribute = Bukkit.getPluginManager().isPluginEnabled("SX-Attribute");
        if(OxConfigManager.ConfigYaml.getBoolean("AttributePlugin.AttributePlus"))
            OxAttributeManager.isAttributePlus = Bukkit.getPluginManager().isPluginEnabled("AttributePlus");
    }
    public static String readConfigString(YamlConfiguration config, String key){
        if(config.get(key) == null){
            OxLoggerManager.warn("[%prefix%] Can't get from: " + config.getName() + " key: " + key + "'s value");
            return "null";
        }
        else {
            return config.getString(key);
        }
    }
    public static String readConfigString(ConfigurationSection config, String key){
        if(config.get(key) == null){
            OxLoggerManager.warn("[%prefix%] Can't get from: " + config.getName() + " key: " + key + "'s value");
            return "null";
        }
        else {
            return config.getString(key);
        }
    }
    public static List<String> readConfigStringList(ConfigurationSection config, String key){
        if(config.get(key) == null){
            OxLoggerManager.warn("[%prefix%] Can't get from: " + config.getName() + " key: " + key + "'s value");
            return null;
        }
        else {
            return config.getStringList(key);
        }
    }
    public static boolean readConfigBoolean(YamlConfiguration config, String key){
        if(config.get(key) == null){
            OxLoggerManager.warn("[%prefix%] Can't get from: " + config.getName() + " key: " + key + "'s value");
            return false;
        }
        else {
            return config.getBoolean(key);
        }
    }
    public static int readConfigInt(YamlConfiguration config, String key){
        if(config.get(key) == null){
            OxLoggerManager.warn("[%prefix%] Can't get from: " + config.getName() + " key: " + key + "'s value");
            return 0;
        }
        else {
            return config.getInt(key);
        }
    }
    public static int readConfigInt(ConfigurationSection config, String key){
        if(config.get(key) == null){
            OxLoggerManager.warn("[%prefix%] Can't get from: " + config.getName() + " key: " + key + "'s value");
            return 0;
        }
        else {
            return config.getInt(key);
        }
    }
}
