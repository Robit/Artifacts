package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.RewardBases.Passive;

public class ThickSkin extends Passive {

    @Override
    public String getName() {
        return "Thick Skin";
    }

    @Override
    public String getDescription() {
        return "Take no damage from cacti.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        return new ItemStack(Material.CACTUS);
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
    public void ElasticJumpEvent(EntityDamageByBlockEvent event) {
        if (validateWorld(event.getEntity().getWorld()) && enabledPlayers.contains(event.getEntity()) && event.getDamager().getType().equals(Material.CACTUS)) {
            event.setCancelled(true);
        }
    }
}

