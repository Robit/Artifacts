package io.github.rm2023.Artifacts;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class Utils {
    public static void placeBlockSafely(Material type, Location location, Player player) {
        if (!location.getBlock().isEmpty()) {
            return;
        }
        BlockPlaceEvent event = new BlockPlaceEvent(location.getBlock(), location.getBlock().getState(), location.subtract(0, 0, 0).getBlock(), new ItemStack(type), player, true, EquipmentSlot.HAND);
        Main.plugin.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            location.getBlock().setType(type);
        }
    }
}
