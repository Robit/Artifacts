package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.rm2023.Artifacts.RewardBases.Item;

public class GravityAnomaly extends Item {

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.ENDER_EYE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "Gravity Anomaly";
    }

    @Override
    public String getDescription() {
        return "One time use item that gives all entities in a 7 block radius levitation for 10 seconds.";
    }

    @Override
    public Tier getTier() {
        return Tier.COMMON;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        if (validateWorld(event.getPlayer().getWorld()) && event.getItem() != null && getCustomData(event.getItem().getItemMeta()).contains(getID())) {
            ItemStack item = event.getItem().clone();
            item.setAmount(1);
            event.getPlayer().getInventory().removeItem(item);
            event.getPlayer().getNearbyEntities(7, 7, 7).stream().filter(entity -> entity instanceof LivingEntity).forEach(entity -> ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 200, 0)));
            event.getPlayer().getLocation().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1, 0);
            event.setCancelled(true);
        }
    }
}
