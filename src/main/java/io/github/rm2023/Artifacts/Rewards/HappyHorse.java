package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.RewardBases.Item;

public class HappyHorse extends Item {

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.WHEAT, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "Happy Horse";
    }

    @Override
    public String getDescription() {
        return "Upon use, spawns a horse with speed/jump stats in the 90th percentile.";
    }

    @Override
    public Tier getTier() {
        return Tier.LEGENDARY;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @EventHandler(ignoreCancelled = true)
    public void spawnHappyHorse(PlayerInteractEvent event) {
        if (validateWorld(event.getPlayer().getWorld()) && event.getItem() != null && event.getClickedBlock() != null && getCustomData(event.getItem().getItemMeta()).equals(getID())) {
            ItemStack item = event.getItem().clone();
            item.setAmount(1);
            event.getPlayer().getInventory().removeItem(item);
            Horse horse = (Horse) event.getClickedBlock().getLocation().getWorld().spawnEntity(event.getClickedBlock().getLocation(), EntityType.HORSE);
            horse.setAdult();
            horse.setJumpStrength(0.94);
            horse.setDomestication(horse.getMaxDomestication());
            horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.315);
            event.setCancelled(true);
        }
    }
}
