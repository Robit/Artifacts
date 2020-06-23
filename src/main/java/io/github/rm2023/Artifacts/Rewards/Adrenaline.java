package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.rm2023.Artifacts.RewardBases.Item;

public class Adrenaline extends Item {

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.SPLASH_POTION, 1);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 60, 9), true);
        meta.setColor(Color.ORANGE);
        meta.setDisplayName(getName());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "Adrenaline";
    }

    @Override
    public String getDescription() {
        return "Custom splash potion that gives speed 10 for 3 seconds.";
    }

    @Override
    public Tier getTier() {
        return Tier.COMMON;
    }

    @Override
    public double getRarity() {
        return 1;
    }
}
