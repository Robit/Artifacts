package io.github.rm2023.Artifacts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ArtifactItemManager<Reward> implements Listener {
    public static final NamespacedKey ROLLS_KEY = new NamespacedKey(Main.plugin, "ROLLS");
    public static final NamespacedKey COMMON_CHANCE_KEY = new NamespacedKey(Main.plugin, "COMMON");
    public static final NamespacedKey UNCOMMON_CHANCE_KEY = new NamespacedKey(Main.plugin, "UNCOMMON");
    public static final NamespacedKey RARE_CHANCE_KEY = new NamespacedKey(Main.plugin, "RARE");
    public static final NamespacedKey LEGENDARY_CHANCE_KEY = new NamespacedKey(Main.plugin, "LEGENDARY");
    public static final NamespacedKey CURSE_CHANCE_KEY = new NamespacedKey(Main.plugin, "CURSE");
    public static final NamespacedKey PROPERTIES_KEY = new NamespacedKey(Main.plugin, "PROPERTIES");

    public ArtifactItemManager() {
        Main.plugin.getServer().getPluginManager().registerEvents(this, Main.plugin);
    }

    public ItemStack makeArtifactItem(String name, Material material, int rolls, int commonChance, int uncommonChance, int rareChance, int legendaryChance, int curseChance, String properties) {
        int total = commonChance + uncommonChance + rareChance + legendaryChance + curseChance;
        ItemStack result = new ItemStack(material);
        ItemMeta itemMeta = result.getItemMeta();
        itemMeta.setDisplayName(name);
        result.getItemMeta().getPersistentDataContainer().set(ROLLS_KEY, PersistentDataType.INTEGER, rolls);
        result.getItemMeta().getPersistentDataContainer().set(COMMON_CHANCE_KEY, PersistentDataType.INTEGER, commonChance);
        result.getItemMeta().getPersistentDataContainer().set(UNCOMMON_CHANCE_KEY, PersistentDataType.INTEGER, uncommonChance);
        result.getItemMeta().getPersistentDataContainer().set(RARE_CHANCE_KEY, PersistentDataType.INTEGER, rareChance);
        result.getItemMeta().getPersistentDataContainer().set(LEGENDARY_CHANCE_KEY, PersistentDataType.INTEGER, legendaryChance);
        result.getItemMeta().getPersistentDataContainer().set(CURSE_CHANCE_KEY, PersistentDataType.INTEGER, curseChance);
        result.getItemMeta().getPersistentDataContainer().set(PROPERTIES_KEY, PersistentDataType.STRING, properties);
        itemMeta.setLore(new ArrayList<String>() {
            {
                add(ChatColor.DARK_PURPLE + "Choose a reward from " + Integer.toString(rolls) + " roll" + (rolls != 1 ? "s" : ""));
                add(ChatColor.DARK_PURPLE + "Chances:");
                if (commonChance > 0) {
                    add(ChatColor.WHITE + "Common: " + commonChance * 100 / total + "%");
                }
                if (uncommonChance > 0) {
                    add(ChatColor.AQUA + "Uncommon: " + uncommonChance * 100 / total + "%");
                }
                if (rareChance > 0) {
                    add(ChatColor.YELLOW + "Rare: " + rareChance * 100 / total + "%");
                }
                if (legendaryChance > 0) {
                    add(ChatColor.GOLD + "Legendary: " + legendaryChance * 100 / total + "%");
                }
                if (curseChance > 0) {
                    add(ChatColor.RED + "Curse: " + curseChance * 100 / total + "%");
                }
                if (!properties.isEmpty()) {
                    add(ChatColor.LIGHT_PURPLE + "Extra Properties: " + properties);
                }
            }
        });
        result.setItemMeta(itemMeta);
        return result;
    }

    public List<Reward> roll(ItemStack stack) {
        return null;
    }

}
