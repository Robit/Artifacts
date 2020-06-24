package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.RewardBases.EternalMaterial;

public class EternalFlintAndSteel extends EternalMaterial {

    @Override
    public String getName() {
        return "Eternal Flint and Steel";
    }

    @Override
    public String getDescription() {
        return "A flint and steel that doesn't break.";
    }

    @Override
    public Tier getTier() {
        return Tier.UNCOMMON;
    }

    @Override
    public double getRarity() {
        return 0.25;
    }

    @Override
    public ItemStack[] getItem() {
        ItemStack[] item = super.getItem();
        item[0].setType(Material.FLINT_AND_STEEL);
        return item;
    }
}
