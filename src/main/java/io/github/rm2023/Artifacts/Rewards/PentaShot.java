package io.github.rm2023.Artifacts.Rewards;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Passive;

public class PentaShot extends Passive {

    @Override
    public String getName() {
        return "Penta Shot";
    }

    @Override
    public String getDescription() {
        return "Fire five arrows in rapid sequence.";
    }

    @Override
    public ItemStack getRepresentationStack() {
        ItemStack item = new ItemStack(Material.TIPPED_ARROW, 5);
        PotionMeta itemMeta = (PotionMeta) item.getItemMeta();
        itemMeta.setColor(Color.ORANGE);
        item.setItemMeta(itemMeta);
        return item;
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
                add((Passive) Main.plugin.rewardMap.get("DOUBLE_SHOT"));
                add((Passive) Main.plugin.rewardMap.get("TRIPLE_SHOT"));
            }
        };
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void PentaShotEvent(EntityShootBowEvent event) {
        if (validateWorld(event.getEntity().getWorld()) && enabledPlayers.contains(event.getEntity()) && event.getProjectile() instanceof Arrow) {
            double velocity = event.getProjectile().getVelocity().length();
            for (int i = 1; i <= 4; i++) {
                Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                    Arrow a = event.getEntity().launchProjectile(Arrow.class, event.getEntity().getLocation().getDirection().normalize().multiply(velocity));
                    a.setCritical(((Arrow) event.getProjectile()).isCritical());
                    a.setDamage(((Arrow) event.getProjectile()).getDamage());
                    a.setGravity(((Arrow) event.getProjectile()).hasGravity());
                    a.setKnockbackStrength(((Arrow) event.getProjectile()).getKnockbackStrength());
                    a.setPierceLevel(((Arrow) event.getProjectile()).getPierceLevel());
                    a.setBasePotionData(((Arrow) event.getProjectile()).getBasePotionData());
                    a.setShooter(((Arrow) event.getProjectile()).getShooter());
                    a.setFireTicks(((Arrow) event.getProjectile()).getFireTicks());
                    (((Arrow) event.getProjectile()).getCustomEffects()).forEach((e) -> a.addCustomEffect(e, true));
                    String customData = getCustomData(event.getProjectile());
                    if (customData.contains("EXPLOSIVE_ARROWS") && !((Player) event.getEntity()).getInventory().removeItem(new ItemStack(Material.GUNPOWDER, 5)).isEmpty()) {
                        customData.replace("EXPLOSIVE_ARROWS", "");
                    }
                    if (customData.contains("FIREWORK_ARROWS") && !((Player) event.getEntity()).getInventory().removeItem(new ItemStack(Material.GUNPOWDER, 1)).isEmpty()) {
                        customData.replace("FIREWORK_ARROWS", "");
                    }
                    a.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, customData);
                    a.setPickupStatus(PickupStatus.CREATIVE_ONLY);
                    event.getEntity().getLocation().getWorld().playSound(event.getEntity().getLocation(), Sound.ITEM_CROSSBOW_SHOOT, 1, 1);
                    Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                        a.remove();
                    }, 100);
                }, i * 5);
            }
        }
    }
}
