package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.RewardBases.Item;

public class LightningBottle extends Item {

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.SPLASH_POTION, 1);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setColor(Color.YELLOW);
        meta.setDisplayName("Lightning Bottle");
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "Lightning Bottle";
    }

    @Override
    public String getDescription() {
        return "Splash potion which hits affected entities with lightning.";
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
    public void lightningBottleThrowEvent(ProjectileLaunchEvent event) {
        if (validateWorld(event.getEntity().getLocation().getWorld()) && event.getEntity() instanceof ThrownPotion && getCustomData(((ThrownPotion) event.getEntity()).getItem().getItemMeta()).contains(getID())) {
            event.getEntity().getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        }
    }

    @EventHandler
    public void lightningBottleHitEvent(PotionSplashEvent event) {
        if (validateWorld(event.getEntity().getLocation().getWorld()) && event.getEntity() instanceof ThrownPotion && getCustomData((ThrownPotion) event.getEntity()).contains(getID())) {
            event.getAffectedEntities().forEach((entity) -> entity.getWorld().strikeLightning(entity.getLocation()));
        }
    }
}
