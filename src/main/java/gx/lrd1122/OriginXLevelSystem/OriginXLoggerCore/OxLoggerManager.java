package gx.lrd1122.OriginXLevelSystem.OriginXLoggerCore;

import gx.lrd1122.OriginXLevelSystem.OriginXAPI.OxDependsAPI.OxPlaceholderAPI;
import gx.lrd1122.OriginXLevelSystem.OriginXAPI.OxMinecraftAPI.OriginXMinecraftAPI;
import gx.lrd1122.OriginXLevelSystem.OriginXConfigCore.OxConfigManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class OxLoggerManager {
    public static void info(String s){
        Bukkit.getServer().getConsoleSender().sendMessage($prefix("&a[" + randomColor("OriginXLevelSystem") +"] &a" + s));
    }
    public static void warn(String s){
        Bukkit.getServer().getConsoleSender().sendMessage($prefix("&a[" + randomColor("OriginXLevelSystem") +"] &a" + s));
    }
    public static void sendMessage(Player player, String s){
        player.sendMessage($string(player, s));
    }
    public static void sendMessage(CommandSender player, String s){
        if(player instanceof Player) {
            player.sendMessage($string((Player) player, s));
        }
        else {
            player.sendMessage($prefix(s));
        }
    }
    public static void sendMessage(Player player, List<String> s){
        for(int i = 0 ; i < s.size(); i++) {
            player.sendMessage($string(player, s.get(i)));
        }
    }
    public static void sendMessage(CommandSender player, List<String> s){
        if(player instanceof Player) {
            for (int i = 0; i < s.size(); i++) {
                player.sendMessage($string((Player) player, s.get(i)));
            }
        }
        else{
            for (int i = 0; i < s.size(); i++) {
                player.sendMessage($prefix(s.get(i)));
            }
        }

    }
    private static String $prefix(String s){
        return OriginXMinecraftAPI.ColorString(s.replace("%prefix%", OxConfigManager.prefix));
    }
    public static String $string(Player player, String s){
        return OxPlaceholderAPI.setPlaceholder(player, $prefix(s));
    }
    public static String $string(String s){
        return $prefix(s);
    }
    public static List<String> $stringList(Player player, List<String> s){
        List<String> out = new ArrayList<>();
        for(String value : s)
            out.add($string(value));
        return out;
    }
    public static List<String> $stringList(List<String> s){
        List<String> out = new ArrayList<>();
        for(String value : s)
            out.add($string(value));
        return out;
    }
    public static String randomColor(String s){
        String output = "";
        for(int i = 0 ; i < s.length(); i++){
            int f = new Random().nextInt(colors().length());
            output = output + "&" + colors().charAt(f) + s.charAt(i);
        }
        return ChatColor.translateAlternateColorCodes('&', output);
    }
    public static String colors(){
        return "abcdef123456789";
    }
}
