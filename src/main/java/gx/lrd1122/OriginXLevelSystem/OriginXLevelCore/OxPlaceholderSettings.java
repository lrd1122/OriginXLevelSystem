package gx.lrd1122.OriginXLevelSystem.OriginXLevelCore;

import gx.lrd1122.OriginXLevelSystem.OriginXConfigCore.OxConfigManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class OxPlaceholderSettings extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "OriginXLevelSystem";
    }

    @Override
    public String getAuthor() {
        return "lrd1122";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        OxLevelPlayer levelPlayer = OxLevelSystemManager.getPlayer(player.getName(), OxConfigManager.storageType);
        if(levelPlayer != null) {
            String[] args = params.split("_");
            if (args[0].equalsIgnoreCase("level")) {
                if (args.length == 2) {
                    switch (args[1].toLowerCase()) {
                        case "level":
                            return String.valueOf(levelPlayer.getPlayerLevel());
                        case "exp":
                            return String.valueOf(levelPlayer.getPlayerExp());
                        case "totalexp":
                            return String.valueOf(levelPlayer.getPlayerTotalExp());
                        case "group":
                            return String.valueOf(levelPlayer.getPlayerGroup());
                    }
                }
                if (args.length == 3) {
                    OxLevelPlayer targetPlayer = OxLevelSystemManager.getPlayer(args[1], OxConfigManager.storageType);
                    switch (args[2].toLowerCase()) {
                        case "level":
                            return String.valueOf(targetPlayer.getPlayerLevel());
                        case "exp":
                            return String.valueOf(targetPlayer.getPlayerExp());
                        case "totalexp":
                            return String.valueOf(targetPlayer.getPlayerTotalExp());
                        case "group":
                            return String.valueOf(targetPlayer.getPlayerGroup());
                    }
                }
            }
            if (args[0].equalsIgnoreCase("group")) {
                if (args.length == 2) {
                    OxLevelGroup levelGroup = OxLevelSystemManager.groupMap.get(levelPlayer.getPlayerGroup());
                    switch (args[1].toLowerCase()) {
                        case "name":
                            return levelGroup.getName();
                        case "minlevel":
                            return String.valueOf(levelGroup.getLevelList().get(0));
                        case "maxlevel":
                            return String.valueOf(levelGroup.getLevelList().get(levelGroup.getLevelList().size()));
                        case "multiple":
                            return String.valueOf(levelGroup.getExpMutiply());
                    }
                }
                if (args.length == 3) {
                    OxLevelGroup levelGroup = OxLevelSystemManager.groupMap.get(OxLevelSystemManager.getPlayer(args[1], OxConfigManager.storageType));
                    switch (args[2].toLowerCase()) {
                        case "name":
                            return levelGroup.getName();
                        case "minlevel":
                            return String.valueOf(levelGroup.getLevelList().get(0));
                        case "maxlevel":
                            return String.valueOf(levelGroup.getLevelList().get(levelGroup.getLevelList().size()));
                        case "multiple":
                            return String.valueOf(levelGroup.getExpMutiply());
                    }
                }
            }
        }
        return "0";
    }
}
