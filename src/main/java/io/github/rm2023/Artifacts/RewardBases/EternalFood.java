package io.github.rm2023.Artifacts.RewardBases;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public abstract class EternalFood extends Item {
    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, "eternal");
        itemMeta.setDisplayName(getName());
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemMeta);
        item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        return new ItemStack[] { item };
    }
}
