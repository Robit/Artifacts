package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.RewardBases.Passive;

public class PopcornChicken extends Passive {

    @Override
    public String getName() {
        return "Popcorn Chicken";
    }

    @Override
    public String getDescription() {
        return "Killing baby chickens drops cooked chicken.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        return new ItemStack(Material.COOKED_CHICKEN);
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
    public void PopcornChickenEvent(EntityDeathEvent event) {
        if (validateWorld(event.getEntity().getWorld()) && enabledPlayers.contains(event.getEntity().getKiller()) && event.getEntity() instanceof Chicken && !((Chicken) event.getEntity()).isAdult()) {
            event.getDrops().clear();
            event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.COOKED_CHICKEN));
        }
    }
}

