package gx.lrd1122.OriginXLevelSystem.OriginXLevelCore;

import gx.lrd1122.OriginXLevelSystem.OriginXAPI.OxDependsAPI.OxPlaceholderAPI;
import gx.lrd1122.OriginXLevelSystem.OriginXConfigCore.OxConfigManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelSystem;
import gx.lrd1122.OriginXLevelSystem.OriginXLoggerCore.OxLoggerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.sql.*;
import java.util.HashMap;

public class OxLevelSystemManager{

    public static HashMap<Player, OxLevelPlayer> playerMap;
    public static HashMap<String, OxLevelGroup> groupMap;
    public static OxLevelMainTask mainTask = new OxLevelMainTask();
    //mysql
    public static String mysqlHost;
    public static String mysqlPort;
    public static String mysqlUser;
    public static String mysqlPassword;
    public static String mysqlDatabase;
    public static String mysqlUseSSL;
    public static String mysqlAddress;


    public static void initialize(){
        playerMap = new HashMap<>();
        groupMap = new HashMap<>();
    }

    public static void calculateLevel(String name, OxLevelStorageType storageType){
        if (storageType.equals(OxLevelStorageType.YAML)) {
            OxLevelPlayer levelPlayer = getPlayer(name, storageType);
            OxLevelGroup levelGroup = groupMap.get(levelPlayer.getPlayerGroup());
            int current = 0;
            int playerLevel = 0;
            double playerExpToLevelUp = 0;
            double playerTotalExp = levelPlayer.getPlayerTotalExp();
            double playerCurrentExpMaxLevel = 0;
            for (int number = 0; number < levelGroup.getLevelList().size(); number++) {
                Integer integer = levelGroup.getLevelList().get(number);
                int currentNeedExp = levelGroup.getLevelSettings().get(integer).getNeedExp();
                if (playerTotalExp < currentNeedExp + current
                        || number == levelGroup.getLevelList().size() - 1) {
                    playerLevel = integer;
                    playerExpToLevelUp = playerTotalExp - current;
                    playerCurrentExpMaxLevel = currentNeedExp;
                    break;
                }
                current += currentNeedExp;
            }
            levelPlayer.setPlayerLevel(playerLevel);
            levelPlayer.setPlayerExp(playerExpToLevelUp);
            try {
                levelPlayer.getDataYaml().set("PlayerExp", playerExpToLevelUp);
                levelPlayer.getDataYaml().set("PlayerLevel", playerLevel);
                levelPlayer.getDataYaml().save(levelPlayer.getDataFile());
            }
            catch (Exception e){
                e.printStackTrace();
            }
            if (levelGroup.isReplaceOrigin() && Bukkit.getPlayer(name) != null) {
                Player player = Bukkit.getPlayer(name);
                player.setLevel(playerLevel);
                float finalExp = (float) (playerExpToLevelUp / playerCurrentExpMaxLevel);
                player.setExp(finalExp > 1 ? 1 : finalExp < 0 ? 0 : finalExp);
            }
        }
        else if(storageType.equals(OxLevelStorageType.MYSQL)) {
            OxLevelPlayer levelPlayer = getPlayer(name, storageType);
            OxLevelGroup levelGroup = groupMap.get(levelPlayer.getPlayerGroup());
            int current = 0;
            int playerLevel = 0;
            double playerExpToLevelUp = 0;
            double playerTotalExp = levelPlayer.getPlayerTotalExp();
            double playerCurrentExpMaxLevel = 0;
            for (int number = 0; number < levelGroup.getLevelList().size(); number++) {
                Integer integer = levelGroup.getLevelList().get(number);
                int currentNeedExp = levelGroup.getLevelSettings().get(integer).getNeedExp();
                if (playerTotalExp < currentNeedExp + current
                        || number == levelGroup.getLevelList().size() - 1) {
                    playerLevel = integer;
                    playerExpToLevelUp = playerTotalExp - current;
                    playerCurrentExpMaxLevel = currentNeedExp;
                    break;
                }
                current += currentNeedExp;
            }
            if (levelGroup.isReplaceOrigin() && Bukkit.getPlayer(name) != null) {
                Player player = Bukkit.getPlayer(name);
                player.setLevel(playerLevel);
                float finalExp = (float) (playerExpToLevelUp / playerCurrentExpMaxLevel);
                player.setExp(finalExp > 1 ? 1 : finalExp < 0 ? 0 : finalExp);
            }
            try {
                Connection connection = DriverManager.getConnection(
                        OxLevelSystemManager.mysqlAddress, OxLevelSystemManager.mysqlUser, OxLevelSystemManager.mysqlPassword
                );
                PreparedStatement insertData = connection.prepareStatement("" +
                        "INSERT INTO originxlevelsystem " +
                        "(UUID, Name, PlayerTotalExp, PlayerExp, PlayerLevel, PlayerGroup)" +
                        "VALUES " +
                        "(?, ?, ?, ?, ?, ?)" +
                        "ON DUPLICATE KEY UPDATE PlayerTotalExp=?,PlayerExp=?,PlayerLevel=?,PlayerGroup=?;");
                insertData.setString(1, Bukkit.getPlayerUniqueId(name).toString());
                insertData.setString(2, name);
                insertData.setDouble(3, playerTotalExp);
                insertData.setDouble(4, playerExpToLevelUp);
                insertData.setDouble(5, playerLevel);
                insertData.setString(6, levelPlayer.getPlayerGroup());
                insertData.setDouble(7, playerTotalExp);
                insertData.setDouble(8, playerExpToLevelUp);
                insertData.setDouble(9, playerLevel);
                insertData.setString(10, levelPlayer.getPlayerGroup());
                insertData.execute();
                insertData.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (levelGroup.isReplaceOrigin() && Bukkit.getPlayer(name) != null) {
                Player player = Bukkit.getPlayer(name);
                player.setLevel(playerLevel);
                float finalExp = (float) (playerExpToLevelUp / playerCurrentExpMaxLevel);
                player.setExp(finalExp > 1 ? 1 : finalExp < 0 ? 0 : finalExp);
            }
        }
    }
    public static void setGroup(String player, String group, OxLevelStorageType storageType) {
        if (storageType.equals(OxLevelStorageType.YAML)) {
            try {
                OxLevelPlayer levelPlayer = getPlayer(player, storageType);
                assert levelPlayer != null;
                levelPlayer.setPlayerGroup(group);
                YamlConfiguration yaml = levelPlayer.getDataYaml();
                yaml.set("Group", group);
                yaml.save(levelPlayer.getDataFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (storageType.equals(OxLevelStorageType.MYSQL)) {
            try {
                OxLevelPlayer levelPlayer = getPlayer(player, storageType);
                assert levelPlayer != null;
                levelPlayer.setPlayerGroup(group);
                Connection connection = DriverManager.getConnection(
                        OxLevelSystemManager.mysqlAddress, OxLevelSystemManager.mysqlUser, OxLevelSystemManager.mysqlPassword
                );
                PreparedStatement insertData = connection.prepareStatement("" +
                        "INSERT INTO originxlevelsystem " +
                        "(UUID, Name, PlayerTotalExp, PlayerExp, PlayerLevel, PlayerGroup)" +
                        "VALUES " +
                        "(?, '', 0, 0, 0, ?)" +
                        "ON DUPLICATE KEY UPDATE PlayerGroup=?;");
                insertData.setString(1, Bukkit.getPlayerUniqueId(player).toString());
                insertData.setString(2, levelPlayer.getPlayerGroup());
                insertData.setString(3, levelPlayer.getPlayerGroup());
                insertData.execute();
                insertData.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void addTotalExp(String player, double exp, OxLevelStorageType storageType){
        if (storageType.equals(OxLevelStorageType.YAML)) {
            try {
                OxLevelPlayer levelPlayer = getPlayer(player, storageType);
                double finalExp = levelPlayer.getPlayerTotalExp() + exp;
                levelPlayer.setPlayerTotalExp(finalExp);
                YamlConfiguration yaml = levelPlayer.getDataYaml();
                yaml.set("PlayerTotalExp", finalExp);
                yaml.save(levelPlayer.getDataFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (storageType.equals(OxLevelStorageType.MYSQL)){
            try {
                OxLevelPlayer levelPlayer = getPlayer(player, storageType);
                double finalExp = levelPlayer.getPlayerTotalExp() + exp;
                levelPlayer.setPlayerTotalExp(finalExp);
                Connection connection = DriverManager.getConnection(
                        OxLevelSystemManager.mysqlAddress, OxLevelSystemManager.mysqlUser, OxLevelSystemManager.mysqlPassword
                );
                PreparedStatement insertData = connection.prepareStatement("" +
                        "INSERT INTO originxlevelsystem " +
                        "(UUID, Name, PlayerTotalExp, PlayerExp, PlayerLevel, PlayerGroup)" +
                        "VALUES " +
                        "(?, '', ?, 0, 0, '')" +
                        "ON DUPLICATE KEY UPDATE PlayerTotalExp=?;");
                insertData.setString(1, Bukkit.getPlayerUniqueId(player).toString());
                insertData.setDouble(2, levelPlayer.getPlayerTotalExp());
                insertData.setDouble(3, levelPlayer.getPlayerTotalExp());
                insertData.execute();
                insertData.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void reduceTotalExp(String player, double exp, OxLevelStorageType storageType){
        if(storageType.equals(OxLevelStorageType.YAML)) {
            try {
                OxLevelPlayer levelPlayer = getPlayer(player, storageType);
                OxLevelGroup levelGroup = groupMap.get(levelPlayer.getPlayerGroup());
                double finalExp = levelPlayer.getPlayerTotalExp() - exp;
                if (finalExp < 0)
                    finalExp = 0;
                levelPlayer.setPlayerTotalExp(finalExp);
                YamlConfiguration yaml = levelPlayer.getDataYaml();
                yaml.set("PlayerTotalExp", finalExp);
                yaml.save(levelPlayer.getDataFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (storageType.equals(OxLevelStorageType.MYSQL)){
            try {
                OxLevelPlayer levelPlayer = getPlayer(player, storageType);
                OxLevelGroup levelGroup = groupMap.get(levelPlayer.getPlayerGroup());
                double finalExp = levelPlayer.getPlayerTotalExp() - exp;
                if (finalExp < 0)
                    finalExp = 0;
                levelPlayer.setPlayerTotalExp(finalExp);
                Connection connection = DriverManager.getConnection(
                        OxLevelSystemManager.mysqlAddress, OxLevelSystemManager.mysqlUser, OxLevelSystemManager.mysqlPassword
                );
                PreparedStatement insertData = connection.prepareStatement("" +
                        "INSERT INTO originxlevelsystem " +
                        "(UUID, Name, PlayerTotalExp, PlayerExp, PlayerLevel, PlayerGroup)" +
                        "VALUES " +
                        "(?, '', ?, 0, 0, '')" +
                        "ON DUPLICATE KEY UPDATE PlayerTotalExp=?;");
                insertData.setString(1, Bukkit.getPlayerUniqueId(player).toString());
                insertData.setDouble(2, levelPlayer.getPlayerTotalExp());
                insertData.setDouble(3, levelPlayer.getPlayerTotalExp());
                insertData.execute();
                insertData.close();
                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static OxLevelController getControlByStr(String name, OxLevelStorageType storageType){
        OxLevelController controller = new OxLevelController();
        String[] strings = name.split("\\|\\|");
        controller.setOxLevelPlayer(getPlayer(name, storageType));
        controller.setControllerType(OxLevelControllerType.valueOf(strings[1].toUpperCase()));
        controller.setValue(Double.parseDouble(strings[2]));
        return controller;
    }

    public static OxLevelPlayer getPlayer(String name, OxLevelStorageType storageType) {
        if (storageType.equals(OxLevelStorageType.YAML)) {
            try {
                File file = new File(OxConfigManager.playerDataDir, name + ".yml");
                boolean exist = file.exists();
                if (!exist) {
                    file.createNewFile();
                }
                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
                if (!exist) {
                    yaml.set("name", name);
                    yaml.set("PlayerTotalExp", 0);
                    yaml.set("PlayerExp", 0);
                    yaml.set("PlayerLevel", 0);
                    yaml.set("Group", "default");
                    yaml.save(file);
                }
                OxLevelPlayer levelPlayer = new OxLevelPlayer(name);
                levelPlayer.setPlayerLevel(yaml.getInt("PlayerLevel"));
                levelPlayer.setPlayerExp(yaml.getInt("PlayerExp"));
                levelPlayer.setPlayerTotalExp(yaml.getInt("PlayerTotalExp"));
                levelPlayer.setPlayerGroup(yaml.getString("Group"));
                if(Bukkit.getPlayer(name) != null) levelPlayer.setPlayer(Bukkit.getPlayer(name));
                levelPlayer.setDataYaml(yaml);
                levelPlayer.setDataFile(file);
                return levelPlayer;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (OxConfigManager.storageType.equals(OxLevelStorageType.MYSQL)) {
            try {
                Connection connection = DriverManager.getConnection(
                        OxLevelSystemManager.mysqlAddress, OxLevelSystemManager.mysqlUser, OxLevelSystemManager.mysqlPassword
                );
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT COUNT(UUID) FROM originxlevelsystem WHERE UUID=?"
                );
                preparedStatement.setString(1, Bukkit.getPlayerUniqueId(name).toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.first();
                OxLevelPlayer oxLevelPlayer = new OxLevelPlayer(name);
                if (resultSet.getInt(1) == 0) {
                    PreparedStatement insertData = connection.prepareStatement("" +
                            "INSERT INTO originxlevelsystem " +
                            "(UUID, Name, PlayerTotalExp, PlayerExp, PlayerLevel, PlayerGroup)" +
                            "VALUES " +
                            "(?, ?, 0, 0, 0, ?)" +
                            "ON DUPLICATE KEY UPDATE UUID=?,Name=?,PlayerGroup=?;");
                    insertData.setString(1, Bukkit.getPlayerUniqueId(name).toString());
                    insertData.setString(2, name);
                    insertData.setString(3, "default");
                    insertData.setString(4, Bukkit.getPlayerUniqueId(name).toString());
                    insertData.setString(5, name);
                    insertData.setString(6, "default");
                    insertData.execute();
                    resultSet.close();
                    insertData.close();
                    preparedStatement.close();
                    connection.close();
                }
                PreparedStatement getData = connection.prepareStatement(
                        "SELECT * FROM originxlevelsystem WHERE UUID=?;"
                );
                getData.setString(1, Bukkit.getPlayerUniqueId(name).toString());
                ResultSet playerData = getData.executeQuery();
                playerData.first();
                int playerLevel = playerData.getInt("PlayerLevel");
                double playerTotalExp = playerData.getDouble("PlayerTotalExp");
                double playerExp = playerData.getDouble("PlayerExp");
                String group = playerData.getString("PlayerGroup");
                oxLevelPlayer.setName(name);
                oxLevelPlayer.setPlayerLevel(playerLevel);
                oxLevelPlayer.setPlayerGroup(group);
                oxLevelPlayer.setPlayerExp(playerExp);
                oxLevelPlayer.setPlayerTotalExp(playerTotalExp);
                oxLevelPlayer.setPlayer(Bukkit.getPlayer(name) == null
                ? null : Bukkit.getPlayer(name));
                connection.close();
                return oxLevelPlayer;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void addQueue(String name, OxLevelControllerType type, double value, OxLevelStorageType storageType) {
        OxLevelSystemManager.mainTask.addController(new OxLevelController(getPlayer(name, storageType), type, value));
    }
    public static void addQueue(String name, OxLevelControllerType type, String value, OxLevelStorageType storageType) {
        OxLevelSystemManager.mainTask.addController(new OxLevelController(getPlayer(name, storageType), type, value));
    }
    public static void initializeOnline(OxLevelStorageType storageType){
        for(Player player : Bukkit.getOnlinePlayers()){
            try {
                OxLevelSystemManager.playerMap.put(player, getPlayer(player.getName(), storageType));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void generateExpOrb(Location location, double value){
        ExperienceOrb experienceOrb = (ExperienceOrb) location.getWorld().spawnEntity(
                location, EntityType.EXPERIENCE_ORB
        );
        experienceOrb.setMetadata("OriginXLevelSystemExp", new FixedMetadataValue(
                OriginXLevelSystem.plugin, value
        ));
        experienceOrb.setExperience(value > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) value);
    }
}
