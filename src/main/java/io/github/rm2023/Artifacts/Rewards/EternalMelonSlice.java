package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.RewardBases.EternalFood;

public class EternalMelonSlice extends EternalFood {

    @Override
    public String getName() {
        return "Eternal Melon Slice";
    }

    @Override
    public String getDescription() {
        return "A melon slice that does not disappear upon being eaten (Main hand only).";
    }

    @Override
    public Tier getTier() {
        return Tier.COMMON;
    }

    @Override
    public double getRarity() {
        return 0.5;
    }

    @Override
    public ItemStack[] getItem() {
        ItemStack[] item = super.getItem();
        item[0].setType(Material.MELON_SLICE);
        return item;
        
    }
}
