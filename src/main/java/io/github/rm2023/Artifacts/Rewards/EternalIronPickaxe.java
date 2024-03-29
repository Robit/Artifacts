package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.RewardBases.EternalMaterial;

public class EternalIronPickaxe extends EternalMaterial {

    @Override
    public String getName() {
        return "Eternal Iron Pickaxe";
    }

    @Override
    public String getDescription() {
        return "An iron pickaxe that doesn't break.";
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
        item[0].setType(Material.IRON_PICKAXE);
        return item;
    }
}
