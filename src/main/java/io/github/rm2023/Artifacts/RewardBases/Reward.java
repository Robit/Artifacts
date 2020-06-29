package io.github.rm2023.Artifacts.RewardBases;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

import de.themoep.inventorygui.GuiElement;
import de.themoep.inventorygui.StaticGuiElement;
import io.github.rm2023.Artifacts.DataManager;
import io.github.rm2023.Artifacts.Main;

public abstract class Reward implements Listener, Comparable<Reward> {

    public static final NamespacedKey KEY = new NamespacedKey(Main.plugin, "RewardData");

    DataManager data;

    public enum Tier {
        UNIQUE, LEGENDARY, RARE, UNCOMMON, COMMON, CURSE;

        public ChatColor color() {
            switch(this) {
            case UNIQUE:
                return ChatColor.DARK_RED;
            case LEGENDARY:
                return ChatColor.GOLD;
            case RARE:
                return ChatColor.YELLOW;
            case UNCOMMON:
                return ChatColor.AQUA;
            case COMMON:
                return ChatColor.WHITE;
            case CURSE:
                return ChatColor.RED;
            default:
                return null;
            }
        }
    }

    public static String getCustomData(PersistentDataHolder holder) {
        return holder == null ? "" : holder.getPersistentDataContainer().getOrDefault(KEY, PersistentDataType.STRING, "");
    }

    public void enable() {
        Main.plugin.getServer().getPluginManager().registerEvents(this, Main.plugin);
        Main.plugin.getServer().getPluginManager().addPermission(new Permission("artifacts.rewards." + getID().toLowerCase(), "Allows getting the reward" + getID() + " from an artfact roll."));
    }

    public abstract String getName();

    public String getID() {
        return getName().toUpperCase().replace(' ', '_');
    }

    public abstract String getDescription();

    public abstract ItemStack getRepresentationStack();

    public abstract Tier getTier();

    public abstract double getRarity();

    public GuiElement getRepresentation(boolean enabled) {
        ItemStack itemRepresentation = new ItemStack(getRepresentationStack());
        ItemMeta itemRepresentationMeta = itemRepresentation.getItemMeta();
        itemRepresentationMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES);
        itemRepresentation.setItemMeta(itemRepresentationMeta);
        if (enabled) {
            itemRepresentation.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        }
        List<String> description = new ArrayList<String>();
        description.add(getName());
        description.add(getTier().color() + "" + ChatColor.BOLD + getTier().toString());
        for (String descString : getDescription().split("(?<=\\G.{20,}\\s)")) {
            description.add(ChatColor.LIGHT_PURPLE + descString);
        }
        description.add(ChatColor.WHITE + "Click to " + (enabled ? "disable" : "enable") + ".");
        return new StaticGuiElement('r', itemRepresentation, description.toArray(new String[description.size()]));
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

    @Override
    public int compareTo(Reward reward) {
        return this.getTier().compareTo(reward.getTier()) == 0 ? this.getName().compareTo(reward.getName()) : this.getTier().compareTo(reward.getTier());
    }

    public boolean validateWorld(World world) {
        return Main.plugin.enabledWorlds.contains(world.getName());
    }
}
