package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.RewardBases.EternalMaterial;

public class EternalIronSword extends EternalMaterial {

    @Override
    public String getName() {
        return "Eternal Iron Sword";
    }

    @Override
    public String getDescription() {
        return "An iron sword that doesn't break.";
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
        item[0].setType(Material.IRON_SWORD);
        return item;
    }
}
