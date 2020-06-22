package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.RewardBases.EternalFood;

public class EternalBakedPotato extends EternalFood {

    @Override
    public String getName() {
        return "Eternal Baked Potato";
    }

    @Override
    public String getDescription() {
        return "A baked potato that does not disappear upon being eaten. (Main hand only)";
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
        item[0].setType(Material.BAKED_POTATO);
        return item;
        
    }
}
