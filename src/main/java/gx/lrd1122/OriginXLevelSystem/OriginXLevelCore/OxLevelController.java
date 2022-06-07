package gx.lrd1122.OriginXLevelSystem.OriginXLevelCore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class OxLevelController {

    private OxLevelControllerType controllerType;
    private OxLevelPlayer oxLevelPlayer;
    private double value;
    private String valueStr;
    public OxLevelController(){

    }
    public OxLevelController(OxLevelPlayer oxLevelPlayer, OxLevelControllerType type, double value){
        this.controllerType = type;
        this.oxLevelPlayer = oxLevelPlayer;
        this.value = value;
    }
    public OxLevelController(OxLevelPlayer oxLevelPlayer, OxLevelControllerType type, String valueStr){
        this.controllerType = type;
        this.oxLevelPlayer = oxLevelPlayer;
        this.valueStr = valueStr;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public OxLevelControllerType getControllerType() {
        return controllerType;
    }

    public void setControllerType(OxLevelControllerType controllerType) {
        this.controllerType = controllerType;
    }

    public OxLevelPlayer getOxLevelPlayer() {
        return oxLevelPlayer;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setOxLevelPlayer(OxLevelPlayer oxLevelPlayer) {
        this.oxLevelPlayer = oxLevelPlayer;
    }

    @Override
    public String toString() {
        return oxLevelPlayer.getPlayer().getName() + "||" + controllerType.name() + "||" + value;
    }
}
