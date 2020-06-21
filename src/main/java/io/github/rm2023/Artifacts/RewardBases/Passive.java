package io.github.rm2023.Artifacts.RewardBases;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

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
}
