package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.Utils;
import io.github.rm2023.Artifacts.RewardBases.Passive;

public class ZeusArrows extends Passive {

    @Override
    public String getName() {
        return "Zeus Arrows";
    }

    @Override
    public String getDescription() {
        return "Arrows strike lightning where they land.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        ItemStack item = new ItemStack(Material.REDSTONE_LAMP);
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
    public void ZeusArrowShot(EntityShootBowEvent event) {
        if (validateWorld(event.getEntity().getWorld()) && enabledPlayers.contains(event.getEntity()) && event.getProjectile() instanceof Arrow) {
            event.getProjectile().getPersistentDataContainer().set(KEY, PersistentDataType.STRING, event.getProjectile().getPersistentDataContainer().getOrDefault(KEY, PersistentDataType.STRING, "").concat(getID()));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void ZeusArrowHit(ProjectileHitEvent event) {
        if (getCustomData(event.getEntity()).contains(getID()) && Utils.canPlaceBlockSafely(Material.AIR, event.getEntity().getLocation(), (Player) event.getEntity().getShooter())) {
            if (event.getHitEntity() != null) {
                event.getHitEntity().getLocation().getWorld().strikeLightning(event.getHitEntity().getLocation());
            } else {
                event.getHitBlock().getLocation().getWorld().strikeLightning(event.getHitBlock().getLocation());
            }
        }
    }
}
