package io.github.rm2023.Artifacts.RewardBases;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Item extends Reward {

    @Override
    public void giveReward(Player p) {
        HashMap<Integer, ItemStack> failedItems = p.getInventory().addItem(getItem());
        for (ItemStack failedItem : failedItems.values()) {
            p.getLocation().getWorld().dropItemNaturally(p.getLocation(), failedItem);
        }
    }

    @Override
    public Material getRepresentationMaterial() {
        return (getItem()[0]).getType();
    }

    public abstract ItemStack[] getItem();
}
