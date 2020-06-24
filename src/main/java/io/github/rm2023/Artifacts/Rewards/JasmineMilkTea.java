package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.rm2023.Artifacts.RewardBases.Item;

public class JasmineMilkTea extends Item {

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.POTION, 3);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0), true);
        meta.setDisplayName(getName());
        meta.setColor(Color.OLIVE);
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "Jasmine Milk Tea";
    }

    @Override
    public String getDescription() {
        return "Custom potions that give regeneration until next death/effect clear.";
    }

    @Override
    public Tier getTier() {
        return Tier.RARE;
    }

    @Override
    public double getRarity() {
        return 0.5;
    }
}
