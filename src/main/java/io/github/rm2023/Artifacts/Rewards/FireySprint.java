package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.Utils;
import io.github.rm2023.Artifacts.RewardBases.Passive;

public class FireySprint extends Passive {

    @Override
    public String getName() {
        return "Firey Sprint";
    }

    @Override
    public String getDescription() {
        return "Leave a trail of fire behind you as you sprint.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        ItemStack item = new ItemStack(Material.BLAZE_POWDER);
        return item;
    }

    @Override
    public Tier getTier() {
        return Tier.UNCOMMON;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @EventHandler(ignoreCancelled = true)
    public void FireSprintEvent(PlayerMoveEvent event) {
        if (validatePlayerEvent(event) && event.getPlayer().isSprinting() && !event.getFrom().getBlock().equals(event.getTo().getBlock())) {
            Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> Utils.placeBlockSafely(Material.FIRE, event.getFrom(), event.getPlayer()), 10);
        }
    }
}
