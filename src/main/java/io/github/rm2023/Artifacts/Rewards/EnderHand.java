package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.RewardBases.Passive;

public class EnderHand extends Passive {

    @Override
    public String getName() {
        return "Ender Hand";
    }

    @Override
    public String getDescription() {
        return "Fire ender pearls from your empty hand when it's left clicked in the air.";
    }

    @Override
    public Tier getTier() {
        return Tier.LEGENDARY;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @Override
    public ItemStack getRepresentationStack() {
        return new ItemStack(Material.ENDER_PEARL);
    }

    @EventHandler(ignoreCancelled = false)
    public void EnderHandEvent(PlayerInteractEvent event) {
        if (validatePlayerEvent(event) && event.getAction().equals(Action.LEFT_CLICK_AIR) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null && !event.getPlayer().isSneaking()) {
            event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ENDER_PEARL_THROW, 1, 1);
            EnderPearl pearl = (EnderPearl) event.getPlayer().launchProjectile(EnderPearl.class);
            pearl.setVelocity(event.getPlayer().getLocation().getDirection().normalize());
            pearl.setShooter(event.getPlayer());
        }
    }
}
