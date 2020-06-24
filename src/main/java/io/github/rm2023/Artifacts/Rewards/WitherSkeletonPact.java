package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.RewardBases.Item;

public class WitherSkeletonPact extends Item {

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.COAL, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "Wither Skeleton Pact";
    }

    @Override
    public String getDescription() {
        return "Spawn 3 armored named wither skeletons that's friendly to you.";
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
    public void onUse(PlayerInteractEvent event) {
        if (validateWorld(event.getPlayer().getWorld()) && event.getItem() != null && event.getClickedBlock() != null && getCustomData(event.getItem().getItemMeta()).equals(getID())) {
            ItemStack item = event.getItem().clone();
            item.setAmount(1);
            event.getPlayer().getInventory().removeItem(item);
            for (int i = 0; i < 3; i++) {
                WitherSkeleton skeleton = (WitherSkeleton) event.getClickedBlock().getLocation().getWorld().spawnEntity(event.getClickedBlock().getLocation().add(0, 1, 0), EntityType.WITHER_SKELETON);
                skeleton.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                skeleton.getEquipment().setHelmetDropChance(0);
                skeleton.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                skeleton.getEquipment().setChestplateDropChance(0);
                skeleton.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                skeleton.getEquipment().setLeggingsDropChance(0);
                skeleton.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                skeleton.getEquipment().setBootsDropChance(0);
                skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
                skeleton.setPersistent(true);
                skeleton.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, event.getPlayer().getUniqueId().toString());
            }
            event.setCancelled(true);
        }
    }
}
