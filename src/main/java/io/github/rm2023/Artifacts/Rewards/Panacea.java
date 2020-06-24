package io.github.rm2023.Artifacts.Rewards;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import io.github.rm2023.Artifacts.RewardBases.Item;

public class Panacea extends Item {

    private ArrayList<PotionEffectType> validEffects = new ArrayList<PotionEffectType>() {
        {
            add(PotionEffectType.BAD_OMEN);
            add(PotionEffectType.BLINDNESS);
            add(PotionEffectType.CONFUSION);
            add(PotionEffectType.GLOWING);
            add(PotionEffectType.HARM);
            add(PotionEffectType.HUNGER);
            add(PotionEffectType.LEVITATION);
            add(PotionEffectType.POISON);
            add(PotionEffectType.SLOW);
            add(PotionEffectType.SLOW_DIGGING);
            add(PotionEffectType.UNLUCK);
            add(PotionEffectType.WEAKNESS);
            add(PotionEffectType.WITHER);
        }
    };

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.POTION, 3);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setColor(Color.WHITE);
        meta.setDisplayName("Panacea");
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "Panacea";
    }

    @Override
    public String getDescription() {
        return "Custom potions that remove all negative effects on drink.";
    }

    @Override
    public Tier getTier() {
        return Tier.COMMON;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @EventHandler(ignoreCancelled = true)
    public void potionDrinkEvent(PlayerItemConsumeEvent event) {
        if (validateWorld(event.getPlayer().getWorld()) && getCustomData(event.getItem().getItemMeta()).contains(getID())) {
            event.getPlayer().getActivePotionEffects().stream().filter(effect -> validEffects.contains(effect.getType())).forEach(effect -> event.getPlayer().removePotionEffect(effect.getType()));
        }
    }
}
