package gx.lrd1122.OriginXLevelSystem.OriginXAPI.NMS.Methods;

import gx.lrd1122.OriginXLevelSystem.OriginXAPI.NMS.OnMethodManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class OnmsSendTitle {
    public static void sendTitle(Player player, String title, String subtitle, int fadein, int time, int fadeout) throws NoSuchMethodException {
        try {
            if (OnMethodManager.NMSVersion.contains("v1_8")) {
                Class playOutClass = OnMethodManager.getNMSClass("PacketPlayOutTitle");
                Class enumTitles = OnMethodManager.getNMSClass("PacketPlayOutTitle$EnumTitleAction");
                Class iChatBaseComponet = OnMethodManager.getNMSClass("IChatBaseComponent");
                Class packet = OnMethodManager.getNMSClass("Packet");

                Object finalTitle = OnMethodManager.getNMSClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke("a", ChatColor.translateAlternateColorCodes('&', "{\"text\": \"" + title + "\"}"));
                Object finalSubTitle = OnMethodManager.getNMSClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke("a", ChatColor.translateAlternateColorCodes('&', "{\"text\": \"" + subtitle + "\"}"));

                Object craftPlayer = OnMethodManager.getBukkitClass("entity.CraftPlayer").cast(player);
                Object entityPlayer = craftPlayer.getClass().getMethod("getHandle").invoke(craftPlayer);
                Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
                Object titleEnum = enumTitles.getMethod("a", String.class).invoke(playOutClass.getConstructor().newInstance(), "TITLE");
                Object subtitleEnum = enumTitles.getMethod("a", String.class).invoke(playOutClass.getConstructor().newInstance(), "SUBTITLE");

                Object sendtitle = playOutClass.getConstructor(enumTitles, iChatBaseComponet, int.class, int.class, int.class).newInstance(titleEnum, finalTitle, fadein, time, fadeout);
                Object sendsubtitle = playOutClass.getConstructor(enumTitles, iChatBaseComponet, int.class, int.class, int.class).newInstance(subtitleEnum, finalSubTitle, fadein, time, fadeout);
                playerConnection.getClass().getMethod("sendPacket", packet).invoke(playerConnection,sendsubtitle);
                playerConnection.getClass().getMethod("sendPacket", packet).invoke(playerConnection,sendtitle);
            } else {
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
