package io.github.rm2023.Artifacts.RewardBases;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Reward {

    public abstract String getName();

    public abstract String getDescription();

    public abstract Material getRepresentationMaterial();

    public abstract Tier getTier();

    public ItemStack getRepresentation() {
        // TODO cobble the name, material, tier, and description into a fancy lil
        // item representation of the artifact
        return null;
    }

    public abstract void giveReward(Player player);

    public enum Tier {
        UNIQUE, LEGENDARY, RARE, UNCOMMON, COMMON, CURSE
    }
}
