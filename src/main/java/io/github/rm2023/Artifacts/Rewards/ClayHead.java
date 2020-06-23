package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.RewardBases.Item;

public class ClayHead extends Item {

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "Clay Head";
    }

    @Override
    public String getDescription() {
        return "Gives the player a copy of their own head when right clicked.";
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
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwningPlayer(event.getPlayer());
            meta.getPersistentDataContainer().remove(KEY);
            item.setItemMeta(meta);
            event.getPlayer().getInventory().addItem(item);
            event.getPlayer().getLocation().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
            event.setCancelled(true);
        }
    }
}
