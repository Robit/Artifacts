package io.github.rm2023.Artifacts.Rewards;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.RewardBases.Item;

public class EnderPie extends Item {

    Random rng = new Random();

    @Override
    public String getName() {
        return "Ender Pie";
    }

    @Override
    public String getDescription() {
        return "Pumpkin pie that teleports you to a random (potentially unsafe) location within XZ 10k.";
    }

    @Override
    public Tier getTier() {
        return Tier.UNCOMMON;
    }

    @Override
    public double getRarity() {
        return 0.5;
    }

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.PUMPKIN_PIE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
        
    }

    @EventHandler(ignoreCancelled = true)
    public void PieTeleportEvent(PlayerItemConsumeEvent event) {
        if (validateWorld(event.getPlayer().getWorld()) && getCustomData(event.getItem().getItemMeta()).equals(getID())) {
            event.getPlayer().teleport(event.getPlayer().getWorld().getHighestBlockAt(new Location(event.getPlayer().getWorld(), rng.nextInt(10000), 0, rng.nextInt(10000))).getLocation());
        }
    }
}
