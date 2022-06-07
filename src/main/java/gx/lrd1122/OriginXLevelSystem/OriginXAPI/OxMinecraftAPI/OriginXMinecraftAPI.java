package gx.lrd1122.OriginXLevelSystem.OriginXAPI.OxMinecraftAPI;

import gx.lrd1122.OriginXLevelSystem.OriginXAPI.OxDependsAPI.OxPlaceholderAPI;
import gx.lrd1122.OriginXLevelSystem.OriginXLoggerCore.OxLoggerManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class OriginXMinecraftAPI {

    public static String ColorString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static boolean CompareItem(ItemStack item, ItemStack toCompare) {
        boolean sendback = true;
        return sendback;
    }

    public static String LocToStr(Location location) {
        try {
            return location.getWorld().getName() + "," +
                    location.getX() + "," +
                    location.getY() + "," +
                    location.getZ() + "," +
                    location.getPitch() + "," +
                    location.getYaw();
        } catch (Exception e) {
            return null;
        }
    }

    public static Location StrToLoc(String s) {
        String[] strings = s.split(",");
        return new Location(
                Bukkit.getWorld(strings[0]),
                Float.valueOf(strings[1]),
                Float.valueOf(strings[2]),
                Float.valueOf(strings[3]),
                Float.valueOf(strings[4]),
                Float.valueOf(strings[5])
        );
    }

    public static List<Location> StrToLoc(List<String> s) {
        try {
            List<Location> ret = new ArrayList<>();
            for (int i = 0; i < s.size(); i++) {
                String[] strings = s.get(i).split(",");
                ret.add(new Location(
                        Bukkit.getWorld(strings[0]),
                        Float.valueOf(strings[1]),
                        Float.valueOf(strings[2]),
                        Float.valueOf(strings[3]),
                        Float.valueOf(strings[4]),
                        Float.valueOf(strings[5]))
                );
            }
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    public static Color getColor(String name) {
        try {
            return OriginXMinecraftColor.hashMap().get(name);
        } catch (Exception e) {
            return null;
        }
    }

    public static OriginXItemStack getItem(ConfigurationSection section) {
        OriginXItemStack item = new OriginXItemStack();
        item.setKey(section.getString("Key"));
        ItemStack i = new ItemStack(Material.DIAMOND);
        List<String> keys = new ArrayList<>(section.getKeys(false));
        for (int c = 0; c < keys.size(); c++) {
            if (keys.get(c).equalsIgnoreCase("ItemStack")) {
                ConfigurationSection itemsec = section.getConfigurationSection(keys.get(c));
                Material material = Material.getMaterial(itemsec.getString("Material").toUpperCase());
                i = new ItemStack(material);
                ItemMeta m = i.getItemMeta();
                if (itemsec.get("Display") != null) {
                    m.setDisplayName(itemsec.getString("Display"));
                }
                if (itemsec.get("Lore") != null) {
                    m.setLore(itemsec.getStringList("Lore"));
                }
                if (itemsec.get("Unbreakable") != null) {
                    m.setUnbreakable(itemsec.getBoolean("Unbreakable"));
                }
                if (itemsec.get("Data") != null) {
                    i.setData(new MaterialData(material, Byte.parseByte(itemsec.getString("Data"))));
                }
                if (itemsec.get("Amount") != null) {
                    i.setAmount(itemsec.getInt("Amount"));
                }
                if (itemsec.get("Durability") != null) {
                    i.setDurability((Short) itemsec.get("Durability"));
                }
                if (itemsec.get("Enchants") != null) {
                    List<String> strings = section.getStringList("Enchants");
                    for (int p = 0; p < strings.size(); p++) {
                        String[] str = strings.get(p).split(",");
                        i.addUnsafeEnchantment(Enchantment.getByName(str[0].toUpperCase()), Integer.parseInt(str[1]));
                    }
                }
                i.setItemMeta(m);
                item.setItemStack(i);
            }
            if (keys.get(c).equalsIgnoreCase("MythicItem")){
            }
        }
        item.setItemStack(i);
        return item;
    }
    public static HoverEvent analysisHoverStr(Player player, String key){
        String[] strings = key.split(":");
        return new HoverEvent(HoverEvent.Action.valueOf(strings[1].toUpperCase()), TextComponent.fromLegacyText(OxLoggerManager.$string(player, strings[2])));
    }
    public static ClickEvent analysisClickStr(Player player, String key){
        String[] strings = key.split(":");
        return new ClickEvent(ClickEvent.Action.valueOf(strings[1].toUpperCase()), OxPlaceholderAPI.setPlaceholder(player, strings[2]));
    }
}
