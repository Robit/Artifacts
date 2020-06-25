package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.RewardBases.Item;

public class EternalBond extends Item {

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.DANDELION, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "Eternal Bond";
    }

    @Override
    public String getDescription() {
        return "Right click on a friendly non-player entity to make them invulnerable and consume this item.";
    }

    @Override
    public Tier getTier() {
        return Tier.RARE;
    }

    @Override
    public double getRarity() {
        return 0.5;
    }

    @EventHandler(ignoreCancelled = true)
    public void onUse(PlayerInteractAtEntityEvent event) {
        if (validateWorld(event.getPlayer().getWorld()) && event.getPlayer().getInventory().getItemInMainHand() != null && getCustomData(event.getPlayer().getInventory().getItemInMainHand().getItemMeta()).contains(getID()) && (event.getRightClicked() instanceof Animals || event.getRightClicked() instanceof Villager) && !(event.getRightClicked() instanceof Wolf) && !event.getRightClicked().isInvulnerable()) {
            ItemStack item = event.getPlayer().getInventory().getItemInMainHand().clone();
            item.setAmount(1);
            event.getPlayer().getInventory().removeItem(item);
            event.getRightClicked().setInvulnerable(true);
            event.getRightClicked().getLocation().getWorld().spawnParticle(Particle.HEART, event.getRightClicked().getLocation(), 5);
            event.setCancelled(true);
        }
    }
}
