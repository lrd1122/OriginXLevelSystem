package gx.lrd1122.OriginXLevelSystem.OriginXLevelCore;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class OxLevelPlayer {

    private Player player;
    private String name;
    private double playerTotalExp;
    private double playerExp;
    private double playerLevel;
    private String playerGroup;
    private YamlConfiguration dataYaml;
    private File dataFile;
    public OxLevelPlayer(Player player){
        this.player = player;
    }
    public OxLevelPlayer(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getDataFile() {
        return dataFile;
    }

    public void setDataFile(File dataFile) {
        this.dataFile = dataFile;
    }

    public YamlConfiguration getDataYaml() {
        return dataYaml;
    }

    public void setDataYaml(YamlConfiguration dataYaml) {
        this.dataYaml = dataYaml;
    }

    public String getPlayerGroup() {
        return playerGroup;
    }

    public void setPlayerGroup(String playerGroup) {
        this.playerGroup = playerGroup;
    }

    public Player getPlayer() {
        return player;
    }

    public double getPlayerTotalExp() {
        return playerTotalExp;
    }

    public void setPlayerTotalExp(double playerTotalExp) {
        this.playerTotalExp = playerTotalExp;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public double getPlayerExp() {
        return playerExp;
    }

    public void setPlayerExp(double playerExp) {
        this.playerExp = playerExp;
    }

    public double getPlayerLevel() {
        return playerLevel;
    }

    public void setPlayerLevel(double playerLevel) {
        this.playerLevel = playerLevel;
    }
}
