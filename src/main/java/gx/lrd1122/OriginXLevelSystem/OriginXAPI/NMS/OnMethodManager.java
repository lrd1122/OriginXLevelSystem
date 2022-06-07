package gx.lrd1122.OriginXLevelSystem.OriginXAPI.NMS;

import org.bukkit.Bukkit;

public class OnMethodManager {
    public static String NMSVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    public static Class getNMSClass(String name){
        try {
            return Class.forName("net.minecraft.server." + NMSVersion + "." + name);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static Class getBukkitClass(String name){
        try {
            return Class.forName("org.bukkit.craftbukkit." + NMSVersion + "." + name);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
