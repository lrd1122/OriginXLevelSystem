package gx.lrd1122.OriginXLevelSystem.OriginXLevelCore;

import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.AttributeSettings.OxAttributeSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OxLevelGroup {


    private String name;
    private boolean replaceOrigin;
    private double expMutiply;
    private boolean defaultPickup;
    private HashMap<Integer, OxLevelSettings> levelSettings;
    private HashMap<Integer, OxAttributeSettings> attributeSettings;
    private List<Integer> levelList;
    public OxLevelGroup(){
        levelSettings = new HashMap<>();
        attributeSettings = new HashMap<>();
        levelList = new ArrayList<>();
    }

    public HashMap<Integer, OxAttributeSettings> getAttributeSettings() {
        return attributeSettings;
    }

    public void setAttributeSettings(HashMap<Integer, OxAttributeSettings> attributeSettings) {
        this.attributeSettings = attributeSettings;
    }

    public List<Integer> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<Integer> levelList) {
        this.levelList = levelList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReplaceOrigin() {
        return replaceOrigin;
    }

    public void setReplaceOrigin(boolean replaceOrigin) {
        this.replaceOrigin = replaceOrigin;
    }

    public boolean isDefaultPickup() {
        return defaultPickup;
    }

    public void setDefaultPickup(boolean defaultPickup) {
        this.defaultPickup = defaultPickup;
    }

    public double getExpMutiply() {
        return expMutiply;
    }

    public void setExpMutiply(double expMutiply) {
        this.expMutiply = expMutiply;
    }

    public HashMap<Integer, OxLevelSettings> getLevelSettings() {
        return levelSettings;
    }

    public void setLevelSettings(HashMap<Integer, OxLevelSettings> levelSettings) {
        this.levelSettings = levelSettings;
    }
}
