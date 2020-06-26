package io.github.rm2023.Artifacts;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class Utils {
    static Random rng = new Random();

    public static void placeBlockSafely(Material type, Location location, Player player) {
        if (!location.getBlock().isEmpty()) {
            return;
        }
        if (canPlaceBlockSafely(type, location, player)) {
            location.getBlock().setType(type);
        }
    }

    public static boolean canPlaceBlockSafely(Material type, Location location, Player player) {
        BlockPlaceEvent event = new BlockPlaceEvent(location.getBlock(), location.getBlock().getState(), location.subtract(0, 0, 0).getBlock(), new ItemStack(type), player, true, EquipmentSlot.HAND);
        Main.plugin.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public static <T> T weightedRandomValue(List<Map.Entry<T, Double>> randomSet) {
        double total = 0;
        for (Map.Entry<T, Double> entry : randomSet) {
            entry.setValue(entry.getValue() + total);
            total = entry.getValue();
        }
        double random = rng.nextDouble() * total;
        for (Map.Entry<T, Double> entry : randomSet) {
            if (entry.getValue() > random) {
                return entry.getKey();
            }
        }
        return null;
    }
}
