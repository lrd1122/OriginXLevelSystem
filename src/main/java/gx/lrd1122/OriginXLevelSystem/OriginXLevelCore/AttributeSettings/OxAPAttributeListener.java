package gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.AttributeSettings;

import gx.lrd1122.OriginXLevelSystem.OriginXConfigCore.OxConfigManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelMainTask;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelPlayer;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelSystemManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.serverct.ersha.api.AttributeAPI;
import org.serverct.ersha.api.event.AttrAttributeReadEvent;
import org.serverct.ersha.api.event.AttrUpdateAttributeEvent;
import org.serverct.ersha.api.event.attribute.AttrUpdateRuntimeEvent;
import org.serverct.ersha.attribute.data.AttributeData;
import org.serverct.ersha.attribute.data.AttributeSource;

import java.util.ArrayList;
import java.util.List;

public class OxAPAttributeListener implements Listener {
    @EventHandler
    public void onUpdate(AttrAttributeReadEvent event){
        if(event.getSource().getEntity().getType().equals(EntityType.PLAYER)) {
            AttributeSource source = event.getSource();
            Player player = (Player) source.getEntity();
            OxLevelPlayer levelPlayer = OxLevelSystemManager.getPlayer(player.getName(), OxConfigManager.storageType);
            ArrayList<String> lore = source.getLore();
            OxAttributeSettings attributeSettings = OxLevelSystemManager.groupMap.get(levelPlayer.getPlayerGroup()).getAttributeSettings().get(
                    (int) levelPlayer.getPlayerLevel()
            );
            if(attributeSettings != null) {
                lore.addAll(attributeSettings.getValues());
                event.getSource().setLore(lore);
            }
        }
    }
}
