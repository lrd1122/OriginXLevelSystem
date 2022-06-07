package gx.lrd1122.OriginXLevelSystem.OriginXLevelCore;

import gx.lrd1122.OriginXLevelSystem.OriginXConfigCore.OxConfigManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelSystem;
import gx.lrd1122.OriginXLevelSystem.OriginXLoggerCore.OxLoggerManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OxLevelCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 0){
            if(args[0].equalsIgnoreCase("addexp") && sender.hasPermission("oxls.addexp")){
                if(args.length >= 2){
                    String target = sender.getName();
                    if(!isNumeric(args[1]) && sender instanceof Player) {
                        OxLoggerManager.sendMessage(sender, OxConfigManager.LangYaml.getString("Errors.InputError"));
                        return true;
                    }
                    if(args.length >= 3){
                        target = args[2];
                    }
                    OxLevelSystemManager.addQueue(target, OxLevelControllerType.ADDEXP, Double.parseDouble(args[1]), OxConfigManager.storageType);
                    OxLevelSystemManager.addQueue(target, OxLevelControllerType.CALCULATE, 0, OxConfigManager.storageType);
                    return true;
                }
            }
            if(args[0].equalsIgnoreCase("reduceexp") && sender.hasPermission("oxls.reduceexp")){
                if(args.length >= 2){
                    String target = sender.getName();
                    if(!isNumeric(args[1]) && sender instanceof Player) {
                        OxLoggerManager.sendMessage(sender, OxConfigManager.LangYaml.getString("Errors.InputError"));
                        return true;
                    }
                    if(args.length >= 3){
                        target = args[2];
                    }
                    OxLevelSystemManager.addQueue(target, OxLevelControllerType.REDUCEEXP, Double.parseDouble(args[1]), OxConfigManager.storageType);
                    OxLevelSystemManager.addQueue(target, OxLevelControllerType.CALCULATE, 0, OxConfigManager.storageType);

                    return true;
                }
            }
            if(args[0].equalsIgnoreCase("setgroup") && sender.hasPermission("oxls.setgroup")){
                if(args.length >= 2){
                    String target = sender.getName();
                    if(OxLevelSystemManager.groupMap.containsKey(args[1]) && sender instanceof Player) {
                        OxLoggerManager.sendMessage(sender, OxConfigManager.LangYaml.getStringList("Errors.UnknownGroup"));
                        return true;
                    }
                    if(args.length >= 3){
                        target = args[2];
                    }
                    OxLevelSystemManager.addQueue(target, OxLevelControllerType.SETGROUP, args[1], OxConfigManager.storageType);
                    OxLevelSystemManager.addQueue(target, OxLevelControllerType.CALCULATE, 0, OxConfigManager.storageType);
                    return true;
                }
            }
            if(args[0].equalsIgnoreCase("generateExp") && sender.hasPermission("oxls.generateexp")){
                if(args.length >= 2){
                    String target = sender.getName();
                    if(!isNumeric(args[1]) && sender instanceof Player) {
                        OxLoggerManager.sendMessage(sender, OxConfigManager.LangYaml.getString("Errors.InputError"));
                        return true;
                    }
                    if(args.length >= 3){
                        target = args[2];
                    }
                    OxLevelSystemManager.generateExpOrb(Bukkit.getPlayer(target).getLocation(), Double.parseDouble(args[1]));
                    return true;
                }
            }
            if(args[0].equalsIgnoreCase("calculate") && sender.hasPermission("oxls.calculate")){
                if(args.length >= 1){
                    String target = sender.getName();
                    if(args.length >= 2){
                        target = args[1];
                    }
                    OxLevelSystemManager.addQueue(target, OxLevelControllerType.CALCULATE, 0, OxConfigManager.storageType);
                    return true;
                }
            }
            if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("oxls.reload")){
                OriginXLevelSystem.mainTask.cancel();
                List<OxLevelController> controllers = OxLevelSystemManager.mainTask.getControllers();
                List<OxLevelController> temp = OxLevelSystemManager.mainTask.getControllers();
                OxLevelSystemManager.mainTask.setControllers(new ArrayList<>());
                OxLevelSystemManager.initialize();
                OxConfigManager.initialize();
                OxLevelSystemManager.initializeOnline(OxConfigManager.storageType);
                controllers.addAll(temp);
                OxLevelSystemManager.mainTask.setControllers(controllers);
                OriginXLevelSystem.placeholderSettings = new OxPlaceholderSettings();
                OriginXLevelSystem.placeholderSettings.register();
                OriginXLevelSystem.mainTask = Bukkit.getScheduler().runTaskTimerAsynchronously(OriginXLevelSystem.plugin, OxLevelSystemManager.mainTask, 20L, 0L);
                OxLoggerManager.sendMessage(sender, "重载成功!");
            }
            if(args[0].equalsIgnoreCase("getInfo") && sender.hasPermission("oxls.getinfo")){
                String target = sender.getName();
                target = args.length == 2 ? args[1] : target;
                OxLevelPlayer levelPlayer = OxLevelSystemManager.getPlayer(target, OxConfigManager.storageType);
                List<String> strings = OxConfigManager.LangYaml.getStringList("Commands.getInfo");
                String finalTarget = target;
                strings.replaceAll((str) -> PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(
                        Bukkit.getPlayerUniqueId(finalTarget)
                ), str.replace("%name%", finalTarget)));
                OxLoggerManager.sendMessage(sender, strings);
            }
            return true;

        }
        if(sender.hasPermission("oxls.help")){
            OxLoggerManager.sendMessage(sender, OxConfigManager.LangYaml.getStringList("Commands.help"));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        List<String> finalList = new ArrayList<>();
        if(args.length == 1){
            list.add("help");
            list.add("addExp");
            list.add("reduceExp");
            list.add("setGroup");
            list.add("generateExp");
            list.add("calculate");
            list.add("reload");
        }
        switch (args.length) {
            case 1:
                if (args[0].equalsIgnoreCase("")) {
                    finalList.addAll(list);
                } else {
                    for (String key : list) {
                        if (key.startsWith(args[0])) {
                            finalList.add(key);
                        }
                    }
                }
                break;
        }
        return finalList.size() > 0 ? finalList : null;
    }
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
