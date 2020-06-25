package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.RewardBases.Item;

public class OneUp extends Item {

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.RED_MUSHROOM, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "1 UP";
    }

    @Override
    public String getDescription() {
        return "An item that gives you +2 max HP until next death.";
    }

    @Override
    public Tier getTier() {
        return Tier.LEGENDARY;
    }

    @Override
    public double getRarity() {
        return 0.5;
    }

    @EventHandler(ignoreCancelled = true)
    public void onUse(PlayerInteractEvent event) {
        if (validateWorld(event.getPlayer().getWorld()) && event.getItem() != null && getCustomData(event.getItem().getItemMeta()).contains(getID())) {
            ItemStack item = event.getItem().clone();
            item.setAmount(1);
            event.getPlayer().getInventory().removeItem(item);
            event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 2);
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void oneDown(PlayerDeathEvent event) {
        event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
    }
}
