package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.rm2023.Artifacts.RewardBases.Passive;

public class Stealth extends Passive {

    @Override
    public String getName() {
        return "Stealth";
    }

    @Override
    public String getDescription() {
        return "Shift to go invisible.";
    }

    @Override
    public Material getRepresentationMaterial() {
        return Material.POTION;
    }

    @Override
    public Tier getTier() {
        return Tier.RARE;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @EventHandler
    public void shiftEvent(PlayerToggleSneakEvent event) {
        if (!validateEvent(event)) {
            return;
        }
        if (event.isSneaking() && event.getPlayer().getPotionEffect(PotionEffectType.INVISIBILITY) == null) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1));
        }
    }
}
