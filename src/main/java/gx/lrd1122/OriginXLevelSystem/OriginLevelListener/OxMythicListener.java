package gx.lrd1122.OriginXLevelSystem.OriginLevelListener;

import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxDependencies.OxMythicMobsExpDrop;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelPlayer;
import gx.lrd1122.OriginXLevelSystem.OriginXLoggerCore.OxLoggerManager;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicDropLoadEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobLootDropEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OxMythicListener implements Listener {
    @EventHandler
    public void onRegisterDrop(MythicDropLoadEvent event){
        if(event.getDropName().equalsIgnoreCase("OxExp")){
            event.register(new OxMythicMobsExpDrop(event.getContainer().getConfigLine(), event.getConfig()));
        }
    }
}
