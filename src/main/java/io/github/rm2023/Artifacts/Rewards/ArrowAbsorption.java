package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.RewardBases.Passive;

public class ArrowAbsorption extends Passive {

    @Override
    public String getName() {
        return "Arrow Absorption";
    }

    @Override
    public String getDescription() {
        return "When hit by an arrow, take 10% less damage and drop the arrow on the ground for pickup.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        ItemStack item = new ItemStack(Material.SPONGE);
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
    public void AbsorbArrowEvent(ProjectileHitEvent event) {
        if (validateWorld(event.getEntity().getWorld()) && event.getEntity() instanceof Arrow && enabledPlayers.contains(event.getHitEntity())) {
            ((Arrow) event.getEntity()).setDamage(((Arrow) event.getEntity()).getDamage() * 0.9);
            if (((Arrow) event.getEntity()).getPickupStatus().equals(PickupStatus.ALLOWED)) {
                event.getHitEntity().getLocation().getWorld().dropItem(event.getHitEntity().getLocation(), ((Arrow) event.getEntity()).getItemStack());
                event.getEntity().remove();
            }
        }
    }
}
