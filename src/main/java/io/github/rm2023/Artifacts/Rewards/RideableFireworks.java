package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.RewardBases.Item;

public class RideableFireworks extends Item {

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.FIREWORK_ROCKET, 8);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "Rideable Fireworks";
    }

    @Override
    public String getDescription() {
        return "Fireworks that you ride when firing.";
    }

    @Override
    public Tier getTier() {
        return Tier.COMMON;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @EventHandler(ignoreCancelled = false)
    public void onUse(PlayerInteractEvent event) {
        if (validateWorld(event.getPlayer().getWorld()) && event.getItem() != null && getCustomData(event.getItem().getItemMeta()).contains(getID())) {
            ItemStack item = event.getItem().clone();
            item.setAmount(1);
            event.getPlayer().getInventory().removeItem(item);
            Firework firework = (Firework) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.FIREWORK);
            firework.addPassenger(event.getPlayer());
            event.setCancelled(true);
        }
    }
}
