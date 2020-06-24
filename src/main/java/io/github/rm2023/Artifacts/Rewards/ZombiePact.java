package io.github.rm2023.Artifacts.Rewards;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.RewardBases.Item;

public class ZombiePact extends Item {

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.ROTTEN_FLESH, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "Zombie Pact";
    }

    @Override
    public String getDescription() {
        return "Spawn an armored named zombie that's friendly to you.";
    }

    @Override
    public Tier getTier() {
        return Tier.COMMON;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @EventHandler(ignoreCancelled = true)
    public void onUse(PlayerInteractEvent event) {
        if (validateWorld(event.getPlayer().getWorld()) && event.getItem() != null && event.getClickedBlock() != null && getCustomData(event.getItem().getItemMeta()).equals(getID())) {
            ItemStack item = event.getItem().clone();
            item.setAmount(1);
            event.getPlayer().getInventory().removeItem(item);
            Zombie zombie = (Zombie) event.getClickedBlock().getLocation().getWorld().spawnEntity(event.getClickedBlock().getLocation().add(0, 1, 0), EntityType.ZOMBIE);
            zombie.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
            zombie.getEquipment().setHelmetDropChance(0);
            zombie.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
            zombie.getEquipment().setChestplateDropChance(0);
            zombie.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
            zombie.getEquipment().setLeggingsDropChance(0);
            zombie.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
            zombie.getEquipment().setBootsDropChance(0);
            zombie.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_SWORD));
            zombie.setPersistent(true);
            zombie.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, event.getPlayer().getUniqueId().toString());
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void dontTargetFriendlyEvent(EntityTargetLivingEntityEvent event) {
        if (event.getTarget() instanceof Player && !getCustomData(event.getEntity()).isEmpty() && UUID.fromString(getCustomData(event.getEntity())).equals(((Player) event.getTarget()).getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
