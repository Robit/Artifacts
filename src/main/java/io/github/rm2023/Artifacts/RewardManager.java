package io.github.rm2023.Artifacts;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import io.github.rm2023.Artifacts.RewardBases.Passive;
import io.github.rm2023.Artifacts.RewardBases.Reward;
import net.md_5.bungee.api.ChatColor;

public class RewardManager implements Listener {
    public Map<String, Reward> rewardMap;

    public RewardManager(Map<String, Reward> rewardMap) {
        this.rewardMap = rewardMap;
        rewardMap.values().forEach((reward) -> reward.enable());
    }

    public String giveReward(Reward reward, Player player, boolean force, String... properties) {
        reward.giveReward(player);
        if (reward instanceof Passive) {
            return addPassive(player, (Passive) reward, force);
        }
        return ChatColor.GREEN + "" + ChatColor.BOLD + "Reward granted: " + reward.getName() + " -\n " + reward.getDescription();
    }

    public boolean isEnabled(Passive passive, Player player) {
        return getPassiveSection(player, passive) == null ? false : getPassiveSection(player, passive).getBoolean("enabled");
    }

    public String addPassive(Player player, Passive passive, boolean force, String... properties) {
        if (getPassiveSection(player, passive) != null) {
            return ChatColor.RED + "" + ChatColor.BOLD + "That passive already exists!";
        }
        if (!force) {
            if (!player.hasPermission("artifacts.rewards." + passive.getID())) {
                return ChatColor.RED + "" + ChatColor.BOLD + "Invalid permissions!";
            }
        }
        ConfigurationSection newPassive = getPassiveSection(player).createSection(passive.getID());
        enablePassive(player, passive, true);
        newPassive.set("properties", properties);
        DataManager.getPlayerData(player).save();
        return ChatColor.GREEN + "" + ChatColor.BOLD + "Reward granted: " + passive.getName() + " -\n " + passive.getDescription() + "\n You can manage this reward by using /passives";
    }

    public String removePassive(Player player, Passive passive, boolean force) {
        ConfigurationSection section = getPassiveSection(player, passive);
        if (section == null) {
            return ChatColor.RED + "" + ChatColor.BOLD + "That passive doesn't exist!";
        }
        if (!force) {
            if (section.getStringList("properties").contains("permanant")) {
                return ChatColor.RED + "" + ChatColor.BOLD + "This artifact is permanant!";
            }
        }
        disablePassive(player, passive, true);
        getPassiveSection(player).set(passive.getID(), null);
        DataManager.getPlayerData(player).save();
        return ChatColor.GREEN + "" + ChatColor.BOLD + "Reward removed: " + passive.getName();
    }

    public String enablePassive(Player player, Passive passive, boolean force) {
        ConfigurationSection section = getPassiveSection(player, passive);
        if (section == null) {
            return ChatColor.RED + "" + ChatColor.BOLD + "That passive doesn't exist!";
        }
        if (section.getBoolean("enabled")) {
            return ChatColor.RED + "" + ChatColor.BOLD + "That passive is already enabled!";
        }
        // Disable any enabled passives that conflict with this one. If any of them fail
        // to disable due to sticky, fail.
        if (listEnabledPassives(player).stream().filter(passive.getIncompatiblePassives()::contains).map((p) -> disablePassive(player, p, force)).anyMatch((message) -> message.contains("sticky"))) {
            return ChatColor.RED + "" + ChatColor.BOLD + "An error occured while trying to disable rewards that are incompatible with this one because the incompatible reward is sticky.";
        }
        passive.enableFor(player);
        section.set("enabled", true);
        DataManager.getPlayerData(player).save();
        return ChatColor.GREEN + "" + ChatColor.BOLD + "Passive enabled: " + passive.getName();
    }

    public String disablePassive(Player player, Passive passive, boolean force) {
        ConfigurationSection section = getPassiveSection(player, passive);
        if (section == null) {
            return ChatColor.RED + "" + ChatColor.BOLD + "That passive doesn't exist!";
        }
        if (!section.getBoolean("enabled")) {
            return ChatColor.RED + "" + ChatColor.BOLD + "That passive is already disabled!";
        }
        if (!force) {
            if (section.getStringList("properties").contains("sticky")) {
                return ChatColor.RED + "" + ChatColor.BOLD + "This reward is sticky and cannot be disabled!";
            }
        }
        section.set("enabled", false);
        passive.disableFor(player);
        DataManager.getPlayerData(player).save();
        return ChatColor.GREEN + "" + ChatColor.BOLD + "Passive disabled: " + passive.getName();
    }

    public String togglePassive(Player player, Passive passive, boolean force) {
        ConfigurationSection section = getPassiveSection(player, passive);
        if (section == null) {
            return ChatColor.RED + "" + ChatColor.BOLD + "That passive doesn't exist!";
        }
        if(section.getBoolean("enabled")) {
            return disablePassive(player, passive, force);
        }
        return enablePassive(player, passive, force);
    }

    private ConfigurationSection getPassiveSection(Player player) {
        return DataManager.getPlayerData(player).getData().getConfigurationSection("passives");
    }

    private ConfigurationSection getPassiveSection(Player player, Passive passive) {
        return DataManager.getPlayerData(player).getData().getConfigurationSection("passives/" + passive.getID());
    }

    public List<Passive> listPassives(Player player) {
        return getPassiveSection(player).getKeys(false).stream().map((key) -> (Passive) rewardMap.get(key)).collect(Collectors.toList());
    }

    public List<Passive> listEnabledPassives(Player player) {
        return getPassiveSection(player).getKeys(false).stream().map((key) -> (Passive) rewardMap.get(key)).filter((p) -> isEnabled(p, player)).collect(Collectors.toList());
    }

    public List<Passive> listDisabledPassives(Player player) {
        return getPassiveSection(player).getKeys(false).stream().map((key) -> (Passive) rewardMap.get(key)).filter((p) -> !isEnabled(p, player)).collect(Collectors.toList());
    }

    @EventHandler
    public void loadPlayerOnLogin(PlayerLoginEvent event) {
        DataManager.getPlayerData(event.getPlayer());
        Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> listEnabledPassives(event.getPlayer()).forEach((passive) -> passive.enableFor(event.getPlayer())), 10);
    }

    @EventHandler
    public void unloadPlayerOnLogout(PlayerQuitEvent event) {
        listEnabledPassives(event.getPlayer()).forEach((passive) -> passive.disableFor(event.getPlayer()));
    }
}
