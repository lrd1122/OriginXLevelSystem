package gx.lrd1122.OriginXLevelSystem.OriginXAPI.OxMinecraftAPI;

import org.bukkit.Color;

import java.util.HashMap;

import static org.bukkit.Color.fromRGB;

public class OriginXMinecraftColor {
    public static final Color WHITE = fromRGB(16777215);
    public static final Color SILVER = fromRGB(12632256);
    public static final Color GRAY = fromRGB(8421504);
    public static final Color BLACK = fromRGB(0);
    public static final Color RED = fromRGB(16711680);
    public static final Color MAROON = fromRGB(8388608);
    public static final Color YELLOW = fromRGB(16776960);
    public static final Color OLIVE = fromRGB(8421376);
    public static final Color LIME = fromRGB(65280);
    public static final Color GREEN = fromRGB(32768);
    public static final Color AQUA = fromRGB(65535);
    public static final Color TEAL = fromRGB(32896);
    public static final Color BLUE = fromRGB(255);
    public static final Color NAVY = fromRGB(128);
    public static final Color FUCHSIA = fromRGB(16711935);
    public static final Color PURPLE = fromRGB(8388736);
    public static final Color ORANGE = fromRGB(16753920);

    public static HashMap<String, Color> hashMap(){
        HashMap<String, Color> out = new HashMap<>();
        out.put("WHITE", WHITE);
        out.put("SILVER", SILVER);
        out.put("GRAY", GRAY);
        out.put("BLACK", BLACK);
        out.put("RED", RED);
        out.put("MAROON", MAROON);
        out.put("YELLOW", YELLOW);
        out.put("OLIVE", OLIVE);
        out.put("LIME", LIME);
        out.put("GREEN", GREEN);
        out.put("AQUA", AQUA);
        out.put("TEAL", TEAL);
        out.put("BLUE", BLUE);
        out.put("NAVY", NAVY);
        out.put("FUCHSIA", FUCHSIA);
        out.put("PURPLE", PURPLE);
        out.put("ORANGE", ORANGE);
        return out;
    }
}
