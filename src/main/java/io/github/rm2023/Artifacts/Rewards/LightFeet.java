package io.github.rm2023.Artifacts.Rewards;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Passive;

public class LightFeet extends Passive {

    @Override
    public String getName() {
        return "Light Feet";
    }

    @Override
    public String getDescription() {
        return "Increases your speed by 10%. Incompatible with lighter/lightest feet.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        return new ItemStack(Material.RABBIT_FOOT, 1);
    }

    @Override
    public Tier getTier() {
        return Tier.UNCOMMON;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @Override
    public List<Passive> getIncompatiblePassives() {
        return new ArrayList<Passive>() {
            {
                add((Passive) Main.plugin.rewardMap.get("LIGHTER_FEET"));
                add((Passive) Main.plugin.rewardMap.get("LIGHTEST_FEET"));
            }
        };
    }

    @Override
    public void enableFor(Player p) {
        super.enableFor(p);
        p.setWalkSpeed(p.getWalkSpeed() * 11 / 10);
    }

    @Override
    public void disableFor(Player p) {
        super.disableFor(p);
        p.setWalkSpeed(p.getWalkSpeed() * 10 / 10);
    }
}

