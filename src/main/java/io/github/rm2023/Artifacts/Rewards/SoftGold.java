package io.github.rm2023.Artifacts.Rewards;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import com.destroystokyo.paper.event.entity.EntityKnockbackByEntityEvent;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Passive;

public class SoftGold extends Passive {

    @Override
    public String getName() {
        return "Soft Gold";
    }

    @Override
    public String getDescription() {
        return "Absorbs 25% of the knockback you take. Incompatible with softer/softest gold.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        return new ItemStack(Material.GOLD_NUGGET);
    }

    @Override
    public Tier getTier() {
        return Tier.UNCOMMON;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @Override
    public List<Passive> getIncompatiblePassives() {
        return new ArrayList<Passive>() {
            {
                add((Passive) Main.plugin.rewardMap.get("SOFTER_GOLD"));
                add((Passive) Main.plugin.rewardMap.get("SOFTEST_GOLD"));
            }
        };
    }

    @EventHandler
    public void onKb(EntityKnockbackByEntityEvent event) {
        if (event.getEntity() instanceof Player && validate((Player) event.getEntity())) {
            event.getAcceleration().multiply(0.75);
        }
    }
}
