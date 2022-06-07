package gx.lrd1122.OriginXLevelSystem;

import gx.lrd1122.OriginXLevelSystem.OriginLevelListener.OxJoinListener;
import gx.lrd1122.OriginXLevelSystem.OriginLevelListener.OxLevelListener;
import gx.lrd1122.OriginXLevelSystem.OriginLevelListener.OxMythicListener;
import gx.lrd1122.OriginXLevelSystem.OriginLevelListener.OxQuitListener;
import gx.lrd1122.OriginXLevelSystem.OriginXAPI.OxMinecraftAPI.OriginXMinecraftColor;
import gx.lrd1122.OriginXLevelSystem.OriginXConfigCore.OxConfigManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.AttributeSettings.OxAPAttributeListener;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.AttributeSettings.OxAttributeManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.AttributeSettings.OxSXAttributeListener;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelCommand;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelPlayer;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelSystemManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxPlaceholderSettings;
import gx.lrd1122.OriginXLevelSystem.OriginXLoggerCore.OxLoggerManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;

public final class OriginXLevelSystem extends JavaPlugin {

    public static JavaPlugin plugin;
    public static BukkitTask mainTask;
    public static OxPlaceholderSettings placeholderSettings;
    @Override
    public void onEnable() {
        plugin = this;
        OxLevelSystemManager.initialize();
        OxConfigManager.initialize();
        mainTask = Bukkit.getScheduler().runTaskTimerAsynchronously(this, OxLevelSystemManager.mainTask, 20L, 0L);
        Bukkit.getPluginManager().registerEvents(new OxJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new OxLevelListener(), this);
        Bukkit.getPluginManager().registerEvents(new OxQuitListener(), this);
        if(OxAttributeManager.isAttributePlus)
            Bukkit.getPluginManager().registerEvents(new OxAPAttributeListener(), this);
        if(OxAttributeManager.isSXAttribute)
            Bukkit.getPluginManager().registerEvents(new OxSXAttributeListener(), this);
        if(Bukkit.getPluginManager().isPluginEnabled("MythicMobs"))
            Bukkit.getPluginManager().registerEvents(new OxMythicListener(), this);
        Bukkit.getPluginCommand("OriginXLevelSystem").setExecutor(new OxLevelCommand());
        Bukkit.getPluginCommand("OriginXLevelSystem").setTabCompleter(new OxLevelCommand());
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            placeholderSettings = new OxPlaceholderSettings();
            placeholderSettings.register();
        }
        OxLevelSystemManager.initializeOnline(OxConfigManager.storageType);
        OxLoggerManager.info("Author: lrd1122 Discord:lrd1122#9401");
        OxLoggerManager.info("Welcome to use OriginXLevelSystem Minecraft Plugin");
        OxLoggerManager.info("To get some supports, join our discord https://discord.gg/Ytuf2wAg8v");
        OxLoggerManager.info("QQ 交流讨论群 676396354 请勿发送任何广告");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
