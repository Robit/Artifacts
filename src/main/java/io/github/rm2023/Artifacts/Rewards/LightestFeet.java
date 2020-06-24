package io.github.rm2023.Artifacts.Rewards;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Passive;

public class LightestFeet extends Passive {

    @Override
    public String getName() {
        return "Lightest Feet";
    }

    @Override
    public String getDescription() {
        return "Increases your speed by 20%. Incompatible with light/lighter feet.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        return new ItemStack(Material.RABBIT_FOOT, 3);
    }

    @Override
    public Tier getTier() {
        return Tier.LEGENDARY;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @Override
    public List<Passive> getIncompatiblePassives() {
        return new ArrayList<Passive>() {
            {
                add((Passive) Main.plugin.rewardMap.get("LIGHT_FEET"));
                add((Passive) Main.plugin.rewardMap.get("LIGHTER_FEET"));
            }
        };
    }

    @Override
    public void enableFor(Player p) {
        super.enableFor(p);
        p.setWalkSpeed(p.getWalkSpeed() * 6 / 5);
    }

    @Override
    public void disableFor(Player p) {
        super.disableFor(p);
        p.setWalkSpeed(p.getWalkSpeed() * 5 / 6);
    }
}

