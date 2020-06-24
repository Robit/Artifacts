package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import io.github.rm2023.Artifacts.RewardBases.Passive;

public class HeavyArrows extends Passive {

    @Override
    public String getName() {
        return "Heavy Arrows";
    }

    @Override
    public String getDescription() {
        return "Arrows cancel any momentum of entities they hit.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        ItemStack item = new ItemStack(Material.ANVIL);
        return item;
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
    public void HeavyArrowShot(EntityShootBowEvent event) {
        if (validateWorld(event.getEntity().getWorld()) && enabledPlayers.contains(event.getEntity()) && event.getProjectile() instanceof Arrow) {
            event.getProjectile().getPersistentDataContainer().set(KEY, PersistentDataType.STRING, event.getProjectile().getPersistentDataContainer().getOrDefault(KEY, PersistentDataType.STRING, "").concat(getID()));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void HeavyArrowHit(ProjectileHitEvent event) {
        if (getCustomData(event.getEntity()).contains(getID()) && event.getHitEntity() instanceof LivingEntity) {
            ((Arrow) event.getEntity()).setKnockbackStrength(0);
            event.getHitEntity().setVelocity(new Vector(0, 0, 0));
        }
    }
}
