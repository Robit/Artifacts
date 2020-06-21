package io.github.rm2023.Artifacts.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Passive;
import net.md_5.bungee.api.ChatColor;

public class ArtifactAdminExecutor implements TabExecutor {
    // TODO: Add artifact fabrication /artifactAdmin make <commonChance>
    // <uncommonChance> <rareChance> <legendaryChance> <curseChance> <properties>
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return new ArrayList<String>() {
                {
                    add("give");
                    add("remove");
                    add("enable");
                    add("disable");
                    add("toggle");
                    add("list");
                    add("reload");
                }
            }.stream().filter((entry) -> entry.startsWith(args[0])).collect(Collectors.toList());
        }
        if (args.length == 2 && !args[0].equals("make") && !args[0].equals("reload")) {
            return Main.plugin.getServer().getOnlinePlayers().stream().map((player) -> player.getName()).filter((entry) -> entry.startsWith(args[1])).collect(Collectors.toList());
        }
        if (args.length == 3) {
            if (Main.plugin.getServer().getPlayer(args[1]) != null) {
                if (args[0].equals("give")) {
                    return Main.plugin.rewardMap.values().stream().filter((reward) -> !(Main.plugin.rewardManager.listPassives(Main.plugin.getServer().getPlayer(args[1])).contains(reward))).map((reward) -> reward.getID()).filter((entry) -> entry.startsWith(args[2])).collect(Collectors.toList());
                }
                if (args[0].equals("remove")) {
                    return Main.plugin.rewardManager.listPassives(Main.plugin.getServer().getPlayer(args[1])).stream().map((reward) -> reward.getID()).filter((entry) -> entry.startsWith(args[2])).collect(Collectors.toList());
                }
                if (args[0].equals("enable")) {
                    return Main.plugin.rewardManager.listPassives(Main.plugin.getServer().getPlayer(args[1])).stream().map((reward) -> reward.getID()).filter((entry) -> entry.startsWith(args[2])).collect(Collectors.toList());
                }
                if (args[0].equals("disable")) {
                    return Main.plugin.rewardManager.listPassives(Main.plugin.getServer().getPlayer(args[1])).stream().map((reward) -> reward.getID()).filter((entry) -> entry.startsWith(args[2])).collect(Collectors.toList());
                }
            }
        }
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender.hasPermission("artifacts.admin"))) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid Permissions!");
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Options: give, remove, enable, disable, list, reload, make");
        }
        switch(args[0]) {
        case "give":
        case "remove":
        case "enable":
        case "disable":
            if (args.length != 3 && args.length != 4) {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "/artifactsAdmin " + args[0] + " <player> <artifact>");
                return false;
            }
            switch (args[0]) {
            case "give":
                sender.sendMessage(Main.plugin.rewardManager.giveReward(Main.plugin.getServer().getPlayer(args[1]), Main.plugin.rewardMap.get(args[2]), true, (args.length == 4) ? args[3].split(",") : new String[0]));
                return true;
            case "remove":
                sender.sendMessage(Main.plugin.rewardManager.removePassive(Main.plugin.getServer().getPlayer(args[1]), (Passive) Main.plugin.rewardMap.get(args[2]), true));
                return true;
            case "enable":
                sender.sendMessage(Main.plugin.rewardManager.enablePassive(Main.plugin.getServer().getPlayer(args[1]), (Passive) Main.plugin.rewardMap.get(args[2]), true));
                return true;
            case "disable":
                sender.sendMessage(Main.plugin.rewardManager.disablePassive(Main.plugin.getServer().getPlayer(args[1]), (Passive) Main.plugin.rewardMap.get(args[2]), true));
                return true;
            }
        case "reload":
            Main.plugin.onReload();
            sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Reloaded.");
            return true;
        case "list":
            if (args.length > 2) {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "/artifactsAdmin " + args[0] + " <player>");
                return false;
            }
            if (args.length == 1) {
                Main.plugin.rewardMap.values().forEach((reward) -> sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + reward.getName() + " - " + ChatColor.RESET + "" + ChatColor.BLUE + reward.getDescription()));
                return true;
            }
            Main.plugin.rewardManager.listPassives(Main.plugin.getServer().getPlayer(args[1])).forEach((reward) -> sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + reward.getName() + " - " + ChatColor.RESET + "" + ChatColor.BLUE + reward.getDescription()));
            return true;
        case "make":
            //TODO
            return true;
        }
        sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Options: give, remove, enable, disable, list, reload, make");
        return false;
    }
}

