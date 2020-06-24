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

public class OverclockedArrows extends Passive {

    @Override
    public String getName() {
        return "Overclocked Arrows";
    }

    @Override
    public String getDescription() {
        return "Arrows fired have 1.25x speed. Incompatible with no-fall arrows.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        ItemStack item = new ItemStack(Material.CLOCK);
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

    @Override
    public List<Passive> getIncompatiblePassives() {
        return new ArrayList<Passive>() {
            {
                add((Passive) Main.plugin.rewardMap.get("NO_FALL_ARROWS"));
            }
        };
    }

    @EventHandler(ignoreCancelled = true)
    public void OverclockedArrowShot(EntityShootBowEvent event) {
        if (validateWorld(event.getEntity().getWorld()) && enabledPlayers.contains(event.getEntity()) && event.getProjectile() instanceof Arrow) {
            event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(1.25));
        }
    }
}
