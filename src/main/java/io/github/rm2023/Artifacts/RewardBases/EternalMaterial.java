package io.github.rm2023.Artifacts.RewardBases;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class EternalMaterial extends Item {
    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getName());
        itemMeta.setLore(new ArrayList<String>() {
            {
                add(getDescription());
            }
        });
        itemMeta.setUnbreakable(true);
        item.setItemMeta(itemMeta);
        return new ItemStack[] { item };
    }
}
