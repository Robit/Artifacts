package io.github.rm2023.Artifacts.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import io.github.rm2023.Artifacts.DataManager;
import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Passive;
import net.md_5.bungee.api.ChatColor;

public class ArtifactAdminExecutor implements TabExecutor {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // TODO: Add artifact fabrication /artifactAdmin make <commonChance>
        // <uncommonChance> <rareChance> <legendaryChance> <curseChance> <properties>
        if (args.length == 0) {
            return new ArrayList<String>() {
                {
                    add("give");
                    add("remove");
                    add("enable");
                    add("disable");
                    add("list");
                    add("reload");
                }
            };
        }
        if (args.length == 1 && !args[0].equals("make") && !args[0].equals("reload")) {
            return Main.plugin.getServer().getOnlinePlayers().stream().map((player) -> player.getName()).collect(Collectors.toList());
        }
        if (args.length == 2) {
            if (Main.plugin.getServer().getPlayer(args[1]) != null) {
                if (args[0].equals("give")) {
                    return Main.plugin.rewardMap.values().stream().filter((reward) -> !(Main.plugin.rewardManager.listPassives(Main.plugin.getServer().getPlayer(args[1])).contains(reward))).map((reward) -> reward.getID()).collect(Collectors.toList());
                }
                if (args[0].equals("remove")) {
                    return Main.plugin.rewardManager.listPassives(Main.plugin.getServer().getPlayer(args[1])).stream().map((reward) -> reward.getID()).collect(Collectors.toList());
                }
                if (args[0].equals("enable")) {
                    return Main.plugin.rewardManager.listPassives(Main.plugin.getServer().getPlayer(args[1])).stream().map((reward) -> reward.getID()).collect(Collectors.toList());
                }
                if (args[0].equals("disable")) {
                    return Main.plugin.rewardManager.listPassives(Main.plugin.getServer().getPlayer(args[1])).stream().map((reward) -> reward.getID()).collect(Collectors.toList());
                }
            }
        }
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO: Add artifact fabrication /artifactAdmin make <commonChance>
        // <uncommonChance> <rareChance> <legendaryChance> <curseChance> <properties>
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
            if (args.length != 3) {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "/artifactsAdmin " + args[0] + " <player> <artifact>");
                return false;
            }
            switch (args[0]) {
            case "give":
                Main.plugin.rewardManager.giveReward(Main.plugin.getServer().getPlayer(args[1]), Main.plugin.rewardMap.get(args[2]), true, (args.length == 4) ? args[3] : "");
            case "remove":
                Main.plugin.rewardManager.removePassive(Main.plugin.getServer().getPlayer(args[1]), (Passive) Main.plugin.rewardMap.get(args[2]), true);
            case "enable":
                Main.plugin.rewardManager.enablePassive(Main.plugin.getServer().getPlayer(args[1]), (Passive) Main.plugin.rewardMap.get(args[2]), true);
            case "disable":
                Main.plugin.rewardManager.disablePassive(Main.plugin.getServer().getPlayer(args[1]), (Passive) Main.plugin.rewardMap.get(args[2]), true);
            }
            return true;
        case "reload":
            DataManager.reload();
            return true;
        // TODO reload other things
        case "list":
            if (args.length > 2) {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "/artifactsAdmin " + args[0] + " <player>");
                return false;
            }
            if (args.length == 1) {
                Main.plugin.rewardMap.values().forEach((reward) -> sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + reward.getName() + ChatColor.RESET + "" + ChatColor.BLUE + reward.getDescription()));
                return true;
            }
            Main.plugin.rewardManager.listPassives(Main.plugin.getServer().getPlayer(args[1])).forEach((reward) -> sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + reward.getName() + ChatColor.RESET + "" + ChatColor.BLUE + reward.getDescription()));
            return true;
        case "make":
            //TODO
            return true;
        }
        sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Options: give, remove, enable, disable, list, reload, make");
        return false;
    }
}

