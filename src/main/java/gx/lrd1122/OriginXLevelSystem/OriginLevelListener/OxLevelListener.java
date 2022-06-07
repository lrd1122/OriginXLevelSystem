package gx.lrd1122.OriginXLevelSystem.OriginLevelListener;

import gx.lrd1122.OriginXLevelSystem.OriginXConfigCore.OxConfigManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelControllerType;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelGroup;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelPlayer;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelSystemManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelSystem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class OxLevelListener implements Listener {
    @EventHandler
    public void onPickOrbListener(EntityTargetLivingEntityEvent event){
        if(event.getEntity().getType().equals(EntityType.EXPERIENCE_ORB)
        && event.getTarget().getType().equals(EntityType.PLAYER)) {
            ExperienceOrb experienceOrb = (ExperienceOrb) event.getEntity();
            if (experienceOrb.hasMetadata("OriginXLevelSystemExp")) {
                Player player = (Player) event.getTarget();
                experienceOrb.setExperience(0);
                MetadataValue value = experienceOrb.getMetadata("OriginXLevelSystemExp").get(0);
                OxLevelSystemManager.addQueue(player.getName(), OxLevelControllerType.ADDEXP, value.asDouble(), OxConfigManager.storageType);
                OxLevelSystemManager.addQueue(player.getName(), OxLevelControllerType.CALCULATE, 0, OxConfigManager.storageType);
            }
        }
    }
    @EventHandler
    public void onLevelChange(PlayerExpChangeEvent event) {
        OxLevelPlayer levelPlayer = OxLevelSystemManager.getPlayer(event.getPlayer().getName(), OxConfigManager.storageType);
        OxLevelGroup levelGroup = OxLevelSystemManager.groupMap.get(levelPlayer.getPlayerGroup());
        if (levelGroup.isDefaultPickup()) {
            double amount = event.getAmount();
            event.setAmount(0);
            OxLevelSystemManager.addQueue(levelPlayer.getName(), OxLevelControllerType.ADDEXP, amount, OxConfigManager.storageType);
            OxLevelSystemManager.addQueue(levelPlayer.getName(), OxLevelControllerType.CALCULATE, 0, OxConfigManager.storageType);
        }
    }
}
