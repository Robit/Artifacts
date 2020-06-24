package io.github.rm2023.Artifacts.Rewards;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Passive;

public class NoFallArrows extends Passive {

    @Override
    public String getName() {
        return "No Fall Arrows";
    }

    @Override
    public String getDescription() {
        return "Arrows fired have 0.75x speed and no gravity. Incompatible with overclocked arrows.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        ItemStack item = new ItemStack(Material.DRAGON_BREATH);
        return item;
    }

    @Override
    public Tier getTier() {
        return Tier.RARE;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @Override
    public List<Passive> getIncompatiblePassives() {
        return new ArrayList<Passive>() {
            {
                add((Passive) Main.plugin.rewardMap.get("OVERCLOCKED_ARROWS"));
            }
        };
    }

    @EventHandler(ignoreCancelled = true)
    public void NoFallArrowShot(EntityShootBowEvent event) {
        if (validateWorld(event.getEntity().getWorld()) && enabledPlayers.contains(event.getEntity()) && event.getProjectile() instanceof Arrow) {
            event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(0.75));
            event.getProjectile().setGravity(false);
            Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                event.getProjectile().remove();
            }, 200);
        }
    }
}
