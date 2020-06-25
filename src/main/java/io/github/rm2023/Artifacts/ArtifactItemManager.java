package io.github.rm2023.Artifacts;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.GUI.ArtifactItemGUI;
import io.github.rm2023.Artifacts.RewardBases.Reward;

public class ArtifactItemManager implements Listener {
    public LinkedList<Player> playersRedeemingArtifact = new LinkedList<Player>();

    public static final NamespacedKey ROLLS_KEY = new NamespacedKey(Main.plugin, "ROLLS");
    public static final NamespacedKey COMMON_CHANCE_KEY = new NamespacedKey(Main.plugin, "COMMON");
    public static final NamespacedKey UNCOMMON_CHANCE_KEY = new NamespacedKey(Main.plugin, "UNCOMMON");
    public static final NamespacedKey RARE_CHANCE_KEY = new NamespacedKey(Main.plugin, "RARE");
    public static final NamespacedKey LEGENDARY_CHANCE_KEY = new NamespacedKey(Main.plugin, "LEGENDARY");
    public static final NamespacedKey CURSE_CHANCE_KEY = new NamespacedKey(Main.plugin, "CURSE");
    public static final NamespacedKey PROPERTIES_KEY = new NamespacedKey(Main.plugin, "PROPERTIES");

    public ItemStack makeArtifactItem(String name, Material material, int rolls, int commonChance, int uncommonChance, int rareChance, int legendaryChance, int curseChance, String properties) {
        int total = commonChance + uncommonChance + rareChance + legendaryChance + curseChance;
        ItemStack result = new ItemStack(material);
        ItemMeta itemMeta = result.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.getPersistentDataContainer().set(ROLLS_KEY, PersistentDataType.INTEGER, rolls);
        itemMeta.getPersistentDataContainer().set(COMMON_CHANCE_KEY, PersistentDataType.INTEGER, commonChance);
        itemMeta.getPersistentDataContainer().set(UNCOMMON_CHANCE_KEY, PersistentDataType.INTEGER, uncommonChance);
        itemMeta.getPersistentDataContainer().set(RARE_CHANCE_KEY, PersistentDataType.INTEGER, rareChance);
        itemMeta.getPersistentDataContainer().set(LEGENDARY_CHANCE_KEY, PersistentDataType.INTEGER, legendaryChance);
        itemMeta.getPersistentDataContainer().set(CURSE_CHANCE_KEY, PersistentDataType.INTEGER, curseChance);
        itemMeta.getPersistentDataContainer().set(PROPERTIES_KEY, PersistentDataType.STRING, properties);
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

    public List<Reward> roll(ItemStack stack, Player player) {
        LinkedList<Reward> result = new LinkedList<Reward>();
        for(int i = 0 ; i < stack.getItemMeta().getPersistentDataContainer().getOrDefault(ROLLS_KEY, PersistentDataType.INTEGER, 0); i++) {
            Reward.Tier tier = Utils.weightedRandomValue(new LinkedList<Map.Entry<Reward.Tier, Double>>() {{
                    add(new AbstractMap.SimpleEntry<Reward.Tier, Double>(Reward.Tier.COMMON, (double) stack.getItemMeta().getPersistentDataContainer().getOrDefault(COMMON_CHANCE_KEY, PersistentDataType.INTEGER, 0)));
                    add(new AbstractMap.SimpleEntry<Reward.Tier, Double>(Reward.Tier.UNCOMMON, (double) stack.getItemMeta().getPersistentDataContainer().getOrDefault(UNCOMMON_CHANCE_KEY, PersistentDataType.INTEGER, 0)));
                    add(new AbstractMap.SimpleEntry<Reward.Tier, Double>(Reward.Tier.RARE, (double) stack.getItemMeta().getPersistentDataContainer().getOrDefault(RARE_CHANCE_KEY, PersistentDataType.INTEGER, 0)));
                    add(new AbstractMap.SimpleEntry<Reward.Tier, Double>(Reward.Tier.LEGENDARY, (double) stack.getItemMeta().getPersistentDataContainer().getOrDefault(LEGENDARY_CHANCE_KEY, PersistentDataType.INTEGER, 0)));
                    add(new AbstractMap.SimpleEntry<Reward.Tier, Double>(Reward.Tier.CURSE, (double) stack.getItemMeta().getPersistentDataContainer().getOrDefault(CURSE_CHANCE_KEY, PersistentDataType.INTEGER, 0)));
                }
            });
            List<Reward> possibleRewards = Main.plugin.rewardMap.values().parallelStream()
                    .filter(reward -> reward.getTier().equals(tier))
                    .filter(reward -> player.hasPermission("artifacts.rewards." + reward.getID().toLowerCase()))
                    .filter(reward -> !Main.plugin.rewardManager.listPassives(player).contains(reward))
                    .filter(reward -> !result.contains(reward))
                    .collect(Collectors.toList());
            if (possibleRewards.isEmpty()) {
                return null;
            }
            result.add(Utils.weightedRandomValue(new LinkedList<Map.Entry<Reward, Double>>() {
                {
                    possibleRewards.forEach(reward -> add(new AbstractMap.SimpleEntry<Reward, Double>(reward, reward.getRarity())));
                }
            }));
        }
        return result;
    }

    @EventHandler
    public void artifactRedeemEvent(PlayerInteractEvent event) {
        if (Main.plugin.enabledWorlds.contains(event.getPlayer().getWorld().getName()) && event.getItem() != null && event.getItem().getItemMeta().getPersistentDataContainer().getOrDefault(ROLLS_KEY, PersistentDataType.INTEGER, 0) > 0 && !playersRedeemingArtifact.contains(event.getPlayer())) {
            playersRedeemingArtifact.add(event.getPlayer());
            if(event.getItem().getItemMeta().getPersistentDataContainer().getOrDefault(ROLLS_KEY, PersistentDataType.INTEGER, 0) == 1) {
                event.getPlayer().sendMessage(Main.plugin.rewardManager.giveReward(event.getPlayer(), roll(event.getItem(), event.getPlayer()).get(0), false, event.getItem().getItemMeta().getPersistentDataContainer().getOrDefault(PROPERTIES_KEY, PersistentDataType.STRING, "").split(",")));
            } else {
                new ArtifactItemGUI(roll(event.getItem(), event.getPlayer()), event.getPlayer(), event.getItem().getItemMeta().getPersistentDataContainer().getOrDefault(PROPERTIES_KEY, PersistentDataType.STRING, "").split(","));
            }
            event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
            ItemStack item = event.getItem().clone();
            item.setAmount(1);
            event.getPlayer().getInventory().removeItem(item);
            event.setCancelled(true);
            Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> playersRedeemingArtifact.remove(event.getPlayer()), 20);
        }
    }
}
