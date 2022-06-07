package gx.lrd1122.OriginXLevelSystem.OriginLevelListener;

import gx.lrd1122.OriginXLevelSystem.OriginXConfigCore.OxConfigManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelSettings;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelSystemManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;

public class OxQuitListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) throws IOException {
        Player player = event.getPlayer();
        File file = new File(OxConfigManager.playerDataDir, player.getName() + ".yml");
        boolean exist = file.exists();
        if(!exist){
            file.createNewFile();
        }
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        if(!exist){
            yaml.set("name", player.getName());
            yaml.set("PlayerTotalExp", 0);
            yaml.set("PlayerExp", 0);
            yaml.set("PlayerLevel", 0);
            yaml.set("Group", "default");
            yaml.save(file);
        }
        OxLevelSystemManager.playerMap.remove(player);
    }
}
