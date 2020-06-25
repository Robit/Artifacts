package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Llama;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.RewardBases.Item;

public class HappyLlama extends Item {

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.HAY_BLOCK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "Happy Llama";
    }

    @Override
    public String getDescription() {
        return "Upon use, spawns a llama with speed/jump stats in the 90th percentile.";
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
            Llama llama = (Llama) event.getClickedBlock().getLocation().getWorld().spawnEntity(event.getClickedBlock().getLocation(), EntityType.LLAMA);
            llama.setAdult();
            llama.setStrength(5);
            llama.setDomestication(llama.getMaxDomestication());
            event.setCancelled(true);
        }
    }
}
