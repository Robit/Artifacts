package io.github.rm2023.Artifacts.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Passive;
import net.md_5.bungee.api.ChatColor;

public class PassivesExecutor implements TabExecutor {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }
        if (!(sender.hasPermission("artifacts.user"))) {
            return null;
        }
        if (args.length == 0) {
            return new ArrayList<String>() {
                {
                    add("enable");
                    add("disable");
                    add("info");
                }
            };
        }
        if (args.length == 1) {
            if(args[0].equals("enable")) {
                return Main.plugin.rewardManager.listDisabledPassives((Player) sender).stream().map((passive) -> passive.getID()).collect(Collectors.toList());
            }
            if(args[0].equals("disable")) {
                return Main.plugin.rewardManager.listEnabledPassives((Player) sender).stream().map((passive) -> passive.getID()).collect(Collectors.toList());
            }
            if(args[0].equals("info")) {
                return Main.plugin.rewardManager.listPassives((Player) sender).stream().map((passive) -> passive.getID()).collect(Collectors.toList());
            }
        }
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.RED + "Please run this command as a player.");
            return false;
        }
        if (!(sender.hasPermission("artifacts.user"))) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid Permissions!");
            return false;
        }
        if (args.length == 0) {
            // TODO GUI!
            return false;
        }
        if (args[0].equals("enable")) {
            if (args.length != 2) {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "/passives enable <PASSIVE_ID>");
                return false;
            }
            if (!Main.plugin.rewardMap.containsKey(args[1])) {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "This passive ID does not exist!");
                return false;
            }
            sender.sendMessage(Main.plugin.rewardManager.enablePassive((Player) sender, (Passive) Main.plugin.rewardMap.get(args[1]), false));
            return true;
        }
        if (args[0].equals("disable")) {
            if (args.length != 2) {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "/passives disable <PASSIVE_ID>");
                return false;
            }
            if (!Main.plugin.rewardMap.containsKey(args[1])) {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "This passive ID does not exist!");
                return false;
            }
            sender.sendMessage(Main.plugin.rewardManager.disablePassive((Player) sender, (Passive) Main.plugin.rewardMap.get(args[1]), false));
            return true;
        }
        if (args[0].equals("info")) {
            if (args.length != 2) {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "/passives info <PASSIVE_ID>");
                return false;
            }
            if (!Main.plugin.rewardMap.containsKey(args[1])) {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "This passive ID does not exist!");
                return false;
            }
            sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + Main.plugin.rewardMap.get(args[1]).getName() + " -\n " + Main.plugin.rewardMap.get(args[1]).getDescription());
            return true;
        }
        sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid command");
        return false;
    }
}
