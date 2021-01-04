package io.github.rm2023.Artifacts.Rewards;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Passive;

public class SpringloadedShoes extends Passive {

    @Override
    public String getName() {
        return "Springloaded Shoes";
    }

    @Override
    public String getDescription() {
        return "Jump up to 2 blocks into the air instead of 1.25. Incompatible with elastic shoes.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        return new ItemStack(Material.CHAINMAIL_BOOTS);
    }

    @Override
    public Tier getTier() {
        return Tier.LEGENDARY;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @Override
    public List<Passive> getIncompatiblePassives() {
        return new ArrayList<Passive>() {
            {
                add((Passive) Main.plugin.rewardMap.get("ELASTIC_SHOES"));
            }
        };
    }

    @EventHandler(ignoreCancelled = true)
    public void ElasticJumpEvent(PlayerJumpEvent event) {
        if (validatePlayerEvent(event)) {
            event.getPlayer().setVelocity(event.getPlayer().getVelocity().setY(1));
        }
    }
}

