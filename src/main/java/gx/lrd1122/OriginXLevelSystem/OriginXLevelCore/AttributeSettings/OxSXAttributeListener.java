package gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.AttributeSettings;

import github.saukiya.sxattribute.event.SXLoadItemDataEvent;
import gx.lrd1122.OriginXLevelSystem.OriginXConfigCore.OxConfigManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelPlayer;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelSystemManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class OxSXAttributeListener implements Listener {
    @EventHandler
    public void onUpdate(SXLoadItemDataEvent event){
        if(event.getEntity().getType().equals(EntityType.PLAYER)) {
            Player player = (Player) event.getEntity();
            OxLevelPlayer levelPlayer = OxLevelSystemManager.getPlayer(player.getName(), OxConfigManager.storageType);
            OxAttributeSettings attributeSettings = OxLevelSystemManager.groupMap.get(levelPlayer.getPlayerGroup()).getAttributeSettings().get(
                    (int) levelPlayer.getPlayerLevel()
            );
            if (attributeSettings != null) {
                event.getAttributeData().loadFromList(attributeSettings.getValues());
            }
        }
    }
}
