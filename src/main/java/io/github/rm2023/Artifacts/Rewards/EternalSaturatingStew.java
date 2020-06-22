package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.rm2023.Artifacts.RewardBases.EternalFood;

public class EternalSaturatingStew extends EternalFood {

    @Override
    public String getName() {
        return "Eternal Saturating Stew";
    }

    @Override
    public String getDescription() {
        return "A version of mysterious stew that does not disappear when eaten. (Main hand only)";
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
        item[0].setType(Material.SUSPICIOUS_STEW);
        SuspiciousStewMeta meta = (SuspiciousStewMeta) item[0].getItemMeta();
        meta.addCustomEffect(new PotionEffect(PotionEffectType.SATURATION, 2, 0), true);
        item[0].setItemMeta(meta);
        return item;
    }
}
