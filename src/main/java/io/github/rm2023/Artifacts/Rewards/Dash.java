package io.github.rm2023.Artifacts.Rewards;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.permissions.Permission;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Passive;

public class Dash extends Passive {

    ItemStack cachedItem;

    @Override
    public void enable() {
        Main.plugin.getServer().getPluginManager().registerEvents(this, Main.plugin);
        Main.plugin.getServer().getPluginManager().addPermission(new Permission("artifacts.rewards." + getID().toLowerCase(), "Allows getting the reward" + getID() + " from an artfact roll."));
        cachedItem = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) cachedItem.getItemMeta();
        meta.setOwningPlayer(Main.plugin.getServer().getOfflinePlayer(UUID.fromString("0feafa42-0bf5-4f82-bd08-70deb16903ae")));
        meta.setDisplayName(getName());
        cachedItem.setItemMeta(meta);
    }

    @Override
    public ItemStack getRepresentationStack() {
        return cachedItem;
    }

    @Override
    public String getName() {
        return "Dash";
    }

    @Override
    public String getDescription() {
        return "Shift/left click with an empty hand to us 2 bars of hunger and give yourself a burst of velocity in that direction. Can't be used under 5 bars of hunger.";
    }

    @Override
    public Tier getTier() {
        return Tier.RARE;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @EventHandler(ignoreCancelled = false)
    public void DashEvent(PlayerInteractEvent event) {
        if (validatePlayerEvent(event) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null && event.getPlayer().isSneaking() && event.getAction().equals(Action.LEFT_CLICK_AIR) && event.getPlayer().getFoodLevel() >= 10) {
            event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() - 4);
            event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection());
        }
    }
}
