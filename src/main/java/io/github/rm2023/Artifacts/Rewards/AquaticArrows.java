package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import io.github.rm2023.Artifacts.RewardBases.Passive;

public class AquaticArrows extends Passive {

    @Override
    public String getName() {
        return "Aquatic Arrows";
    }

    @Override
    public String getDescription() {
        return "Arrows that are shot from the water gain a 250% speed-boost.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        ItemStack item = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta itemMeta = (PotionMeta) item.getItemMeta();
        itemMeta.setColor(Color.BLUE);
        item.setItemMeta(itemMeta);
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
    public void AquaticArrowShot(EntityShootBowEvent event) {
        if (validateWorld(event.getEntity().getWorld()) && enabledPlayers.contains(event.getEntity()) && event.getProjectile() instanceof Arrow && event.getProjectile().getLocation().getBlock().getType().equals(Material.WATER)) {
            event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(2.5));
        }
    }
}
