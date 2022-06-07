package gx.lrd1122.OriginXLevelSystem.OriginLevelListener;

import gx.lrd1122.OriginXLevelSystem.OriginXConfigCore.OxConfigManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelPlayer;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelSystemManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class OxJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws IOException {
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
        OxLevelPlayer levelPlayer = new OxLevelPlayer(player);
        levelPlayer.setPlayerLevel(yaml.getInt("PlayerLevel"));
        levelPlayer.setPlayerExp(yaml.getInt("PlayerExp"));
        levelPlayer.setPlayerTotalExp(yaml.getInt("PlayerTotalExp"));
        levelPlayer.setPlayerGroup(yaml.getString("Group"));
        levelPlayer.setName(player.getName());
        levelPlayer.setDataYaml(yaml);
        levelPlayer.setDataFile(file);
        OxLevelSystemManager.playerMap.put(player, levelPlayer);

    }
}
