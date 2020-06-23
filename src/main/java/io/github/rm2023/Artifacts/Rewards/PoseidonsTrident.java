package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.EternalMaterial;

public class PoseidonsTrident extends EternalMaterial {

    @Override
    public String getName() {
        return "Poseidons Trident";
    }

    @Override
    public String getDescription() {
        return "An unbreakable trident which deals 1 heart of void damage to anything it hits.";
    }

    @Override
    public Tier getTier() {
        return Tier.LEGENDARY;
    }

    @Override
    public double getRarity() {
        return 0.25;
    }

    @Override
    public ItemStack[] getItem() {
        ItemStack[] item = super.getItem();
        item[0].setType(Material.TRIDENT);
        ItemMeta itemMeta = item[0].getItemMeta();
        itemMeta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item[0].setItemMeta(itemMeta);
        return item;
    }

    @EventHandler
    public void tridentDamageEvent(EntityDamageByEntityEvent event) {
        if (validateWorld(event.getEntity().getLocation().getWorld()) && event.getDamager() instanceof Player && getCustomData(((Player) event.getDamager()).getInventory().getItemInMainHand().getItemMeta()).equals(getID()) && !event.getCause().equals(DamageCause.VOID)) {
            EntityDamageByEntityEvent voidEvent = new EntityDamageByEntityEvent(event.getDamager(), event.getEntity(), DamageCause.VOID, 2);
            Main.plugin.getServer().getPluginManager().callEvent(voidEvent);
            if (!voidEvent.isCancelled()) {
                ((LivingEntity) event.getEntity()).setHealth(((LivingEntity) event.getEntity()).getHealth() > 2 ? ((LivingEntity) event.getEntity()).getHealth() - 2 : 0);
            }
        }
    }

    @EventHandler
    public void tridentThrowEvent(ProjectileLaunchEvent event) {
        if (validateWorld(event.getEntity().getLocation().getWorld()) && event.getEntity() instanceof Trident && getCustomData(((Trident) event.getEntity()).getItemStack().getItemMeta()).contains(getID())) {
            event.getEntity().getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        }
    }

    @EventHandler
    public void tridentHitEvent(ProjectileHitEvent event) {
        if (validateWorld(event.getEntity().getWorld()) && event.getHitEntity() instanceof LivingEntity && getCustomData(event.getEntity()).contains(getID())) {
            EntityDamageByEntityEvent voidEvent = new EntityDamageByEntityEvent(event.getEntity(), event.getHitEntity(), DamageCause.VOID, 2);
            Main.plugin.getServer().getPluginManager().callEvent(voidEvent);
            if (!voidEvent.isCancelled()) {
                ((LivingEntity) event.getHitEntity()).setHealth(((LivingEntity) event.getHitEntity()).getHealth() > 2 ? ((LivingEntity) event.getHitEntity()).getHealth() - 2 : 0);
            }
        }
    }
}
