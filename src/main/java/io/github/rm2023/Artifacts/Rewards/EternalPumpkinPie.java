package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.RewardBases.EternalFood;

public class EternalPumpkinPie extends EternalFood {

    @Override
    public String getName() {
        return "Eternal Pumpkin Pie";
    }

    @Override
    public String getDescription() {
        return "A pumpkin pie that does not disappear upon being eaten. (Main hand only)";
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
        item[0].setType(Material.PUMPKIN_PIE);
        return item;
        
    }
}
