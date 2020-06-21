package io.github.rm2023.Artifacts.RewardBases;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;

import io.github.rm2023.Artifacts.DataManager;
import io.github.rm2023.Artifacts.Main;

public abstract class Reward implements Listener {

    DataManager data;

    public enum Tier {
        UNIQUE, LEGENDARY, RARE, UNCOMMON, COMMON, CURSE
    }

    public void enable() {
        Main.plugin.getServer().getPluginManager().registerEvents(this, Main.plugin);
        Main.plugin.getServer().getPluginManager().addPermission(new Permission("artifacts.rewards." + getID(), "Allows getting the reward" + getID() + " from an artfact roll."));
    }

    public abstract String getName();

    public String getID() {
        return getName().toUpperCase().replace(' ', '_');
    }

    public abstract String getDescription();

    public abstract Material getRepresentationMaterial();

    public abstract Tier getTier();

    public abstract double getRarity();

    public ItemStack getRepresentation() {
        // TODO cobble the name, material, tier, and description into a fancy lil
        // item representation of the artifact
        return null;
    }

    public void giveReward(Player player) {
        Main.plugin.getLogger().info("Artifact " + getID() + " granted to " + player.getName());
    }

    public DataManager getData() {
        if (data == null) {
            data = DataManager.getRewardData(this);
        }
        return data;
    }
}
