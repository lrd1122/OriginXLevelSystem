package gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxDependencies;

import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelPlayer;
import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelSystemManager;
import gx.lrd1122.OriginXLevelSystem.OriginXLoggerCore.OxLoggerManager;
import io.lumine.xikage.mythicmobs.adapters.AbstractItemStack;
import io.lumine.xikage.mythicmobs.adapters.AbstractLocation;
import io.lumine.xikage.mythicmobs.adapters.AbstractPlayer;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitItemStack;
import io.lumine.xikage.mythicmobs.drops.*;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.utils.numbers.RandomDouble;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class OxMythicMobsExpDrop extends Drop implements IItemDrop {

    public OxMythicMobsExpDrop(String line, MythicLineConfig config) {
        super(line, config);
    }

    @Override
    public AbstractItemStack getDrop(DropMetadata dropMetadata) {
        Location location = dropMetadata.getCaster().getLocation().toPosition().toLocation();
        String[] args = getLine().split(" ");
        Double value = Double.parseDouble(args[1]);
        OxLevelSystemManager.generateExpOrb(location, value);
        return null;
    }
}
