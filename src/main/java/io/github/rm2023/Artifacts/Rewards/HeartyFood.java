package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.RewardBases.Item;

public class HeartyFood extends Item {

    @Override
    public String getName() {
        return "Hearty Meal";
    }

    @Override
    public String getDescription() {
        return "Mushroom stew that when eaten restores hunger bar completely.";
    }

    @Override
    public Tier getTier() {
        return Tier.COMMON;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.MUSHROOM_STEW, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
        
    }

    @EventHandler(ignoreCancelled = true)
    public void fillHunger(PlayerItemConsumeEvent event) {
        if (getCustomData(event.getItem().getItemMeta()).equals(getID())) {
            event.getPlayer().setFoodLevel(20);
            event.getPlayer().setSaturation(20);
        }
    }
}
