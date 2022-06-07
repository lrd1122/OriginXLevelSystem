package gx.lrd1122.OriginXLevelSystem.OriginXAPI.OxMinecraftAPI;

import java.util.ArrayList;
import java.util.List;

public class OriginXMinecraftGUIAPI {

    public static List<String> AnalysisSlots(List<String> list){
        List<String> output = new ArrayList<>();
        for(String s : list){
            for(String key : s.replace(" ", "").split(",")){
                output.add(key);
            }
        }
        return output;
    }
}
