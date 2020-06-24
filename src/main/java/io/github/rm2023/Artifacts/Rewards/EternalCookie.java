package io.github.rm2023.Artifacts.Rewards;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.EternalFood;

public class EternalCookie extends EternalFood {

    @Override
    public String getName() {
        return "Eternal Cookie";
    }

    @Override
    public String getDescription() {
        return "A cookie that does not disappear upon being eaten. (Main hand only)";
    }

    @Override
    public Tier getTier() {
        return Tier.COMMON;
    }

    @Override
    public double getRarity() {
        return 0.5;
    }

    @Override
    public ItemStack[] getItem() {
        ItemStack[] item = super.getItem();
        item[0].setType(Material.COOKIE);
        return item;
        
    }

    @EventHandler(ignoreCancelled = true)
    public void replenishFood(PlayerItemConsumeEvent event) {
        if (getCustomData(event.getItem().getItemMeta()).equals("eternal")) {
            if (event.getItem().getType().equals(Material.MILK_BUCKET)) {
                Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
                    if (event.getPlayer().getInventory().removeItem(new ItemStack(Material.BUCKET)).isEmpty()) {
                        event.getPlayer().getInventory().addItem(event.getItem());
                    }
                }, 1);
            } else if (event.getItem().getType().equals(Material.SUSPICIOUS_STEW)) {
                Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
                    if (event.getPlayer().getInventory().removeItem(new ItemStack(Material.BOWL)).isEmpty()) {
                        event.getPlayer().getInventory().addItem(event.getItem());
                    }
                }, 1);
            } else {
                event.getPlayer().getInventory().addItem(event.getItem());
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void dontCraftEvent(CraftItemEvent event) {
        if (Arrays.stream(event.getInventory().getContents()).anyMatch(item -> getCustomData(item.getItemMeta()).contains("eternal"))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void dontFeedEvent(PlayerInteractEntityEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand() != null && getCustomData(event.getPlayer().getInventory().getItemInMainHand().getItemMeta()).equals("eternal") || event.getPlayer().getInventory().getItemInOffHand() != null && getCustomData(event.getPlayer().getInventory().getItemInOffHand().getItemMeta()).equals("eternal")) {
            event.setCancelled(true);
        }
    }
}
