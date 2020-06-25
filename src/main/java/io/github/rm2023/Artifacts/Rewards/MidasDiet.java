package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.RewardBases.Passive;

public class MidasDiet extends Passive {

    @Override
    public String getName() {
        return "Midas Diet";
    }

    @Override
    public String getDescription() {
        return "Eating golden apples and carrots does not consume them.";
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
    public ItemStack getRepresentationStack() {
        return new ItemStack(Material.GOLDEN_APPLE);
    }

    @EventHandler(ignoreCancelled = true)
    public void replenishGapple(PlayerItemConsumeEvent event) {
        if (validatePlayerEvent(event) && event.getItem().getType().equals(Material.GOLDEN_APPLE) || event.getItem().getType().equals(Material.GOLDEN_CARROT)) {
            event.getPlayer().getInventory().addItem(event.getItem().asOne());
        }
    }
}
