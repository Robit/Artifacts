package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.RewardBases.EternalMaterial;

public class EternalDiamondBoots extends EternalMaterial {

    @Override
    public String getName() {
        return "Eternal Diamond Boots";
    }

    @Override
    public String getDescription() {
        return "Diamond boots that don't break.";
    }

    @Override
    public Tier getTier() {
        return Tier.LEGENDARY;
    }

    @Override
    public double getRarity() {
        return 0.25;
    }

    @Override
    public ItemStack[] getItem() {
        ItemStack[] item = super.getItem();
        item[0].setType(Material.DIAMOND_BOOTS);
        return item;
    }
}
