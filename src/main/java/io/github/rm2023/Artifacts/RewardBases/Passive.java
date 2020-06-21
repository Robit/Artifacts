package io.github.rm2023.Artifacts.RewardBases;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

import io.github.rm2023.Artifacts.Main;

public abstract class Passive extends Reward {
    public ArrayList<Player> enabledPlayers = new ArrayList<Player>();

    public void enableFor(Player player) {
        enabledPlayers.add(player);
    }

    public void disableFor(Player player) {
        enabledPlayers.remove(player);
    }

    public List<Passive> getIncompatiblePassives() {
        return new ArrayList<Passive>();
    }

    protected boolean validateEvent(PlayerEvent event) {
        return enabledPlayers.contains(event.getPlayer()) && Main.plugin.enabledWorlds.contains(event.getPlayer().getWorld().getName());
    }
}
