package gx.lrd1122.OriginXLevelSystem.OriginXAPI.OxDependsAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public class OxPlaceholderAPI {
    public static boolean isPlaceholderAPI = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
    public static String setPlaceholder(Player player, String s){
        String value = s;
        if(OxPlaceholderAPI.isPlaceholderAPI) {
            try {
                Class<?> c = Class.forName("me.clip.placeholderapi.PlaceholderAPI");
                Method method = c.getMethod("setPlaceholders", Player.class, String.class);
                value = (String) method.invoke("setPlaceholders", player, value);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return value;
    }
}
