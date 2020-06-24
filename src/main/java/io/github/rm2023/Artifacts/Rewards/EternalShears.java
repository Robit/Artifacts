package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.RewardBases.EternalMaterial;

public class EternalShears extends EternalMaterial {

    @Override
    public String getName() {
        return "Eternal Shears";
    }

    @Override
    public String getDescription() {
        return "A set of shears that don't break.";
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
        item[0].setType(Material.SHEARS);
        return item;
    }
}
