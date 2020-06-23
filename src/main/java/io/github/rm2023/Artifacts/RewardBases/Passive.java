package io.github.rm2023.Artifacts.RewardBases;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

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

    protected boolean validatePlayerEvent(PlayerEvent event) {
        return validate(event.getPlayer());
    }

    protected boolean validate(Player player) {
        return enabledPlayers.contains(player);
    }
}
