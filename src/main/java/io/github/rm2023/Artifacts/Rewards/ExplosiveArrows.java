package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.RewardBases.Passive;

public class ExplosiveArrows extends Passive {

    @Override
    public String getName() {
        return "Explosive Arrows";
    }

    @Override
    public String getDescription() {
        return "Arrows spend 5 gunpowder and make a tnt-sized explosion on landing.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        ItemStack item = new ItemStack(Material.TNT);
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
    public void ExplosiveArrowShoot(EntityShootBowEvent event) {
        if (validateWorld(event.getEntity().getWorld()) && enabledPlayers.contains(event.getEntity()) && event.getProjectile() instanceof Arrow) {
            if (((Player) event.getEntity()).getInventory().removeItem(new ItemStack(Material.GUNPOWDER, 5)).isEmpty()) {
                event.getProjectile().getPersistentDataContainer().set(KEY, PersistentDataType.STRING, event.getProjectile().getPersistentDataContainer().getOrDefault(KEY, PersistentDataType.STRING, "").concat(getID()));
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void ExplosiveArrowHit(ProjectileHitEvent event) {
        if (getCustomData(event.getEntity()).contains(getID())) {
            if (event.getHitEntity() != null) {
                event.getHitEntity().getLocation().getWorld().createExplosion(event.getHitEntity().getLocation(), 4);
            } else {
                event.getHitBlock().getLocation().getWorld().createExplosion(event.getHitBlock().getLocation(), 4);
            }
            event.getEntity().remove();
        }
    }
}
