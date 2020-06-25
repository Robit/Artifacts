package io.github.rm2023.Artifacts.Rewards;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Passive;

public class LighterFeet extends Passive {

    @Override
    public String getName() {
        return "Lighter Feet";
    }

    @Override
    public String getDescription() {
        return "Increases your speed by 15%. Incompatible with light/lightest feet.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        return new ItemStack(Material.RABBIT_FOOT, 2);
    }

    @Override
    public Tier getTier() {
        return Tier.RARE;
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
                add((Passive) Main.plugin.rewardMap.get("LIGHTEST_FEET"));
            }
        };
    }

    @Override
    public void enableFor(Player p) {
        super.enableFor(p);
        p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getDefaultValue() * 1.15);
    }

    @Override
    public void disableFor(Player p) {
        super.disableFor(p);
        p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getDefaultValue());
    }
}

