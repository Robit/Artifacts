package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.RewardBases.EternalFood;

public class EternalMilk extends EternalFood {

    @Override
    public String getName() {
        return "Eternal Milk";
    }

    @Override
    public String getDescription() {
        return "A milk bucket that does not disappear upon being drunk. (Main hand only)";
    }

    @Override
    public Tier getTier() {
        return Tier.RARE;
    }

    @Override
    public double getRarity() {
        return 0.25;
    }

    @Override
    public ItemStack[] getItem() {
        ItemStack[] item = super.getItem();
        item[0].setType(Material.MILK_BUCKET);
        return item;
        
    }
}
