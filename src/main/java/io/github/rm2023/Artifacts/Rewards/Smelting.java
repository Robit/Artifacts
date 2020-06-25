package io.github.rm2023.Artifacts.Rewards;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Passive;

public class Smelting extends Passive {

    @Override
    public String getName() {
        return "Smelting";
    }

    @Override
    public String getDescription() {
        return "Auto-smelt the blocks you break. (does not include logs)";
    }

    @Override
    public ItemStack getRepresentationStack() {
        ItemStack item = new ItemStack(Material.FURNACE);
        return item;
    }

    @Override
    public Tier getTier() {
        return Tier.RARE;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @EventHandler(ignoreCancelled = true)
    public void SmeltingEvent(BlockBreakEvent event) {
        if (validateWorld(event.getBlock().getWorld()) && enabledPlayers.contains(event.getPlayer())) {
            event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand(), event.getPlayer()).stream().map(drop -> {
                List<Recipe> list = new LinkedList<Recipe>();
                Main.plugin.getServer().recipeIterator().forEachRemaining(recipe -> {
                    if (recipe instanceof FurnaceRecipe && ((FurnaceRecipe) recipe).getInput().getType().equals(drop.getType())) {
                        list.add(recipe);
                    }
                });
                Main.plugin.getLogger().info(drop.toString());
                Main.plugin.getLogger().info(Integer.toString(list.size()));
                if(!list.isEmpty()) {
                    return list.get(0).getResult().add(drop.getAmount() - 1);
                }
                return drop;
            }).forEach(drop -> event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), drop));
            event.setDropItems(false);
        }
    }
}
