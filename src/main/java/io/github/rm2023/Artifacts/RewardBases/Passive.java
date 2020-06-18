package io.github.rm2023.Artifacts.RewardBases;

import java.util.ArrayList;

import org.bukkit.event.Listener;

import io.github.rm2023.Artifacts.Main;

public abstract class Passive<Player> extends Reward implements Listener {
    public ArrayList<Player> enabledPlayers = new ArrayList<Player>();

    public Passive() {
        Main.plugin.getServer().getPluginManager().registerEvents(this, Main.plugin);
    }

    public void enableFor(Player p) {
        enabledPlayers.add(p);
        // TODO - permissions checks, logging, sticky?
    }

    public void disableFor(Player p) {
        enabledPlayers.remove(p);
        // TODO - permissions checks, logging, sticky?
    }
}
