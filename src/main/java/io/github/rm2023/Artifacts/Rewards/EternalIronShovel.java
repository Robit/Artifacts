package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.RewardBases.EternalMaterial;

public class EternalIronShovel extends EternalMaterial {

    @Override
    public String getName() {
        return "Eternal Iron Shovel";
    }

    @Override
    public String getDescription() {
        return "An iron shovel that doesn't break.";
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
        item[0].setType(Material.IRON_SHOVEL);
        return item;
    }
}
