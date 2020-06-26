package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.Utils;
import io.github.rm2023.Artifacts.RewardBases.Passive;

public class FireworkArrows extends Passive {

    @Override
    public String getName() {
        return "Firework Arrows";
    }

    @Override
    public String getDescription() {
        return "Arrows spend 1 gunpowder and explode (firework style) on landing.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        ItemStack item = new ItemStack(Material.FIREWORK_STAR);
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
    public void FireworkArrowShot(EntityShootBowEvent event) {
        if (validateWorld(event.getEntity().getWorld()) && enabledPlayers.contains(event.getEntity()) && event.getProjectile() instanceof Arrow) {
            if (((Player) event.getEntity()).getInventory().removeItem(new ItemStack(Material.GUNPOWDER, 1)).isEmpty()) {
                event.getProjectile().getPersistentDataContainer().set(KEY, PersistentDataType.STRING, event.getProjectile().getPersistentDataContainer().getOrDefault(KEY, PersistentDataType.STRING, "").concat(getID()));
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void FireworkArrowHit(ProjectileHitEvent event) {
        if (getCustomData(event.getEntity()).contains(getID()) && Utils.canPlaceBlockSafely(Material.AIR, event.getEntity().getLocation(), (Player) event.getEntity().getShooter())) {
            Firework firework;
            if (event.getHitEntity() != null) {
                firework = (Firework) event.getHitEntity().getLocation().getWorld().spawnEntity(event.getHitEntity().getLocation(), EntityType.FIREWORK);
            } else {
                firework = (Firework) event.getHitBlock().getLocation().getWorld().spawnEntity(event.getHitBlock().getLocation(), EntityType.FIREWORK);
            }
            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            fireworkMeta.addEffect(FireworkEffect.builder().with(Type.BURST).withColor(Color.YELLOW).build());
            firework.setFireworkMeta(fireworkMeta);
            firework.detonate();
            event.getEntity().remove();
        }
    }
}
