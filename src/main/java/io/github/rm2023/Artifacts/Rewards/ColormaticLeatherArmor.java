package io.github.rm2023.Artifacts.Rewards;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.RewardBases.EternalMaterial;

public class ColormaticLeatherArmor extends EternalMaterial {

    private Random rng = new Random();

    @Override
    public String getName() {
        return "Colormatic Leather Armor";
    }

    @Override
    public String getDescription() {
        return "Unbreakable leather armor that changes colors when worn while taking damage";
    }

    @Override
    public Tier getTier() {
        return Tier.COMMON;
    }

    @Override
    public double getRarity() {
        return 0.1;
    }

    @Override
    public ItemStack[] getItem() {
        ItemStack[] item = super.getItem();
        ItemStack[] result = new ItemStack[] { super.getItem()[0], super.getItem()[0], super.getItem()[0], super.getItem()[0] };
        result[0].setType(Material.LEATHER_HELMET);
        result[1].setType(Material.LEATHER_CHESTPLATE);
        result[2].setType(Material.LEATHER_LEGGINGS);
        result[3].setType(Material.LEATHER_BOOTS);
        Color color = Color.fromRGB(rng.nextInt(255), rng.nextInt(255), rng.nextInt(255));
        for (ItemStack piece : result) {
            LeatherArmorMeta meta = (LeatherArmorMeta) piece.getItemMeta();
            meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
            meta.setColor(color);
            piece.setItemMeta(meta);
        }
        return result;
    }

    @EventHandler
    public void onHit(EntityDamageEvent event) {
        if(validateWorld(event.getEntity().getWorld()) && event.getEntity() instanceof LivingEntity) {
            EntityEquipment equipment = ((LivingEntity) event.getEntity()).getEquipment();
            Color color = Color.fromRGB(rng.nextInt(255), rng.nextInt(255), rng.nextInt(255));
            if (equipment.getHelmet() != null && getCustomData(equipment.getHelmet().getItemMeta()).contains(getID())) {
                ItemStack item = equipment.getHelmet();
                LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                meta.setColor(color);
                item.setItemMeta(meta);
                equipment.setHelmet(item);
            }
            if (equipment.getChestplate() != null && getCustomData(equipment.getChestplate().getItemMeta()).contains(getID())) {
                ItemStack item = equipment.getChestplate();
                LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                meta.setColor(color);
                item.setItemMeta(meta);
                equipment.setChestplate(item);
            }
            if (equipment.getLeggings() != null && getCustomData(equipment.getLeggings().getItemMeta()).contains(getID())) {
                ItemStack item = equipment.getLeggings();
                LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                meta.setColor(color);
                item.setItemMeta(meta);
                equipment.setLeggings(item);
            }
            if (equipment.getBoots() != null && getCustomData(equipment.getBoots().getItemMeta()).contains(getID())) {
                ItemStack item = equipment.getBoots();
                LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                meta.setColor(color);
                item.setItemMeta(meta);
                equipment.setBoots(item);
            }
        }
    }
}
