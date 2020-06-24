package io.github.rm2023.Artifacts.Rewards;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.Utils;
import io.github.rm2023.Artifacts.RewardBases.Item;

public class ExplosiveWeb extends Item {

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.COBWEB, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "Explosive Web";
    }

    @Override
    public String getDescription() {
        return "When placed, spawns a 3*3*3 cube of cobwebs.";
    }

    @Override
    public Tier getTier() {
        return Tier.COMMON;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @EventHandler(ignoreCancelled = true)
    public void onUse(PlayerInteractEvent event) {
        if (validateWorld(event.getPlayer().getWorld()) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock() != null && event.getItem() != null && getCustomData(event.getItem().getItemMeta()).contains(getID())) {
            ItemStack item = event.getItem().clone();
            item.setAmount(1);
            event.getPlayer().getInventory().removeItem(item);
            LinkedList<Location> locations = new LinkedList<Location>();
            int[] units = new int[] { -1, 0, 1 };
            for (int x : units) {
                for (int y : units) {
                    for (int z : units) {
                        locations.add(event.getClickedBlock().getLocation().clone().add(x, y, z));
                    }
                }
            }
            for (Location location : locations) {
                Utils.placeBlockSafely(Material.COBWEB, location, event.getPlayer());
            }
            event.getPlayer().getLocation().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_SLIME_BLOCK_PLACE, 1, 1);
            event.setCancelled(true);
        }
    }
}
