package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.RewardBases.EternalFood;

public class EternalRottenFlesh extends EternalFood {

    @Override
    public String getName() {
        return "Eternal Rotten Flesh";
    }

    @Override
    public String getDescription() {
        return "Rotten flesh that does not disappear upon being eaten. (Main hand only)";
    }

    @Override
    public Tier getTier() {
        return Tier.CURSE;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @Override
    public ItemStack[] getItem() {
        ItemStack[] item = super.getItem();
        item[0].setType(Material.ROTTEN_FLESH);
        return item;
        
    }
}
