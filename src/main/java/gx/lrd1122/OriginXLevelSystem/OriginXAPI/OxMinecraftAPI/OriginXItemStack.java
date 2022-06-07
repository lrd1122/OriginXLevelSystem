package gx.lrd1122.OriginXLevelSystem.OriginXAPI.OxMinecraftAPI;

import org.bukkit.inventory.ItemStack;

public class OriginXItemStack {

    private String key;
    private ItemStack itemStack;

    public OriginXItemStack(){

    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
