package gx.lrd1122.OriginXLevelSystem.OriginXLevelCore;

import gx.lrd1122.OriginXLevelSystem.OriginXConfigCore.OxConfigManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelSystem;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class OxLevelMainTask implements Runnable {

    private List<OxLevelController> controllers = new ArrayList<>();
    private boolean continueTask = true;
    public OxLevelMainTask(){}
    @Override
    public void run() {
        if(controllers.size() > 0 && continueTask) {
            Queue<OxLevelController> tempQuene = new LinkedList<>(controllers);
            controllers = new ArrayList<>();
            continueTask = false;
            while (tempQuene.size() > 0) {
                OxLevelController key = tempQuene.poll();
                if (key.getControllerType().equals(OxLevelControllerType.ADDEXP)) {
                    OxLevelSystemManager.addTotalExp(key.getOxLevelPlayer().getName(), key.getValue(), OxConfigManager.storageType);
                }
                if (key.getControllerType().equals(OxLevelControllerType.REDUCEEXP)) {
                    OxLevelSystemManager.reduceTotalExp(key.getOxLevelPlayer().getName(), key.getValue(), OxConfigManager.storageType);
                }
                if (key.getControllerType().equals(OxLevelControllerType.SETGROUP)){
                    OxLevelSystemManager.setGroup(key.getOxLevelPlayer().getName(), key.getValueStr(), OxConfigManager.storageType);
                }
                if (key.getControllerType().equals(OxLevelControllerType.CALCULATE)) {
                    OxLevelSystemManager.calculateLevel(key.getOxLevelPlayer().getPlayer().getName(), OxConfigManager.storageType);
                }
            }
            continueTask = true;
        }
    }

    public List<OxLevelController> getControllers() {
        return controllers;
    }

    public void setControllers(List<OxLevelController> controllers) {
        this.controllers = controllers;
    }
    public void addController(OxLevelController controller){
        this.controllers.add(controller);
    }
}
