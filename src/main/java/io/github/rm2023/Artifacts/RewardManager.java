package io.github.rm2023.Artifacts;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import io.github.rm2023.Artifacts.RewardBases.Passive;
import io.github.rm2023.Artifacts.RewardBases.Reward;

public class RewardManager {
    public Map<String, Reward> rewardMap;

    public RewardManager(Map<String, Reward> rewardMap) {
        this.rewardMap = rewardMap;
        rewardMap.values().forEach((reward) -> reward.enable());
    }

    public void giveReward(Reward reward, Player player, boolean force) {
        reward.giveReward(player);
        if (reward instanceof Passive) {
            addPassive((Passive) reward, player, force);
        }
    }

    public void addPassive(Passive passive, Player player, boolean force, String... properties) {

    }

    public void removePassive(Passive passive, Player player, boolean force) {

    }

    public void enablePassive(Passive passive, Player player, boolean force) {

    }

    public void disablePassive(Passive passive, Player player, boolean force) {

    }

    public void togglePassive(Passive passive, Player player, boolean force) {

    }

    public List<Passive> listPassives(Player player) {

    }

    public List<Passive> listEnabledPassives(Player player) {

    }

    public List<Passive> listDisabledPassives(Player player) {

    }
}
