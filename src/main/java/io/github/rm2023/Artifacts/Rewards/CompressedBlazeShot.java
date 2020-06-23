package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Item;

public class CompressedBlazeShot extends Item {

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.BLAZE_POWDER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "Compressed Blaze Shot";
    }

    @Override
    public String getDescription() {
        return "Shoots 3 blaze fireballs when clicked where player is aiming cursor. Consumed on use.";
    }

    @Override
    public Tier getTier() {
        return Tier.COMMON;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        if (validateWorld(event.getPlayer().getWorld()) && event.getItem() != null && getCustomData(event.getItem().getItemMeta()).contains(getID())) {
            ItemStack item = event.getItem().clone();
            item.setAmount(1);
            event.getPlayer().getInventory().removeItem(item);
            event.getPlayer().launchProjectile(SmallFireball.class, event.getPlayer().getLocation().getDirection());
            event.getPlayer().getLocation().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 1);
            Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> event.getPlayer().launchProjectile(SmallFireball.class, event.getPlayer().getLocation().getDirection()), 7);
            Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> event.getPlayer().getLocation().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 1), 7);
            Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> event.getPlayer().launchProjectile(SmallFireball.class, event.getPlayer().getLocation().getDirection()), 14);
            Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> event.getPlayer().getLocation().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 1), 14);
            event.setCancelled(true);
        }
    }
}
