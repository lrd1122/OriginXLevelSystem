package gx.lrd1122.OriginXLevelSystem.OriginXLevelCore;

import java.util.ArrayList;
import java.util.List;

public class OxLevelSettings {

    private List<String> permission;
    private int needExp;
    public OxLevelSettings(){
        this.permission = new ArrayList<>();
    }
    public OxLevelSettings(int needExp, List<String> permission){
        this.needExp = needExp;
        this.permission = permission;
    }
    public List<String> getPermission() {
        return permission;
    }

    public void setPermission(List<String> permission) {
        this.permission = permission;
    }

    public int getNeedExp() {
        return needExp;
    }

    public void setNeedExp(int needExp) {
        this.needExp = needExp;
    }
}
