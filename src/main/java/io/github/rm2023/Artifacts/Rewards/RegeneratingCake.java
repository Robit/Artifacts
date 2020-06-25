package io.github.rm2023.Artifacts.Rewards;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Cake;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Item;

public class RegeneratingCake extends Item {

    List<Location> cakeLocations;

    @Override
    public void enable() {
        super.enable();
        cakeLocations = (List<Location>) getData().getData().getList("locations", new ArrayList<Location>());
    }

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.CAKE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "Regenerating Cake";
    }

    @Override
    public String getDescription() {
        return "A cake that regenerates its slices when eaten. (will not regenerate if broken or fully eaten)";
    }

    @Override
    public Tier getTier() {
        return Tier.LEGENDARY;
    }

    @Override
    public double getRarity() {
        return 0.5;
    }

    @EventHandler(ignoreCancelled = true)
    public void onCakePlace(BlockPlaceEvent event) {
        if (validateWorld(event.getBlock().getWorld()) && getCustomData(event.getItemInHand().getItemMeta()).contains(getID())) {
            cakeLocations.add(event.getBlock().getLocation());
            getData().getData().set("locations", cakeLocations);
            getData().save();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onCakeBreak(BlockBreakEvent event) {
        if (validateWorld(event.getBlock().getWorld()) && cakeLocations.contains(event.getBlock().getLocation())) {
            cakeLocations.remove(event.getBlock().getLocation());
            getData().getData().set("locations", cakeLocations);
            getData().save();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onCakeEat(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && cakeLocations.contains(event.getClickedBlock().getLocation())) {
            Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                try {
                    Block cake = event.getClickedBlock();
                    Cake cakeData = (Cake) cake.getBlockData();
                    if (cakeData.getBites() > 0) {
                        cakeData.setBites(cakeData.getBites() - 1);
                        cake.setBlockData(cakeData);
                        event.getPlayer().getWorld().playSound(cake.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, cakeData.getBites());
                    }
                } catch (ClassCastException e) {
                    cakeLocations.remove(event.getClickedBlock().getLocation());
                    getData().getData().set("locations", cakeLocations);
                    getData().save();
                }
            }, 20);
        }
    }

}
