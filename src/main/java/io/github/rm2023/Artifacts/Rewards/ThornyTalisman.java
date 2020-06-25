package io.github.rm2023.Artifacts.Rewards;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Item;

public class ThornyTalisman extends Item {

    @Override
    public ItemStack[] getItem() {
        ItemStack item = new ItemStack(Material.POPPY, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return new ItemStack[] { item };
    }

    @Override
    public String getName() {
        return "Thorny Talisman";
    }

    @Override
    public String getDescription() {
        return "One time use item that reflects all damage back at the attacker for 10 seconds.";
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
            new ThornyListener(event.getPlayer());
            event.setCancelled(true);
        }
    }

    private class ThornyListener implements Listener {
        private Player player;

        public ThornyListener(Player player) {
            this.player = player;
            Main.plugin.getServer().getPluginManager().registerEvents(this, Main.plugin);
            Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> EntityDamageByEntityEvent.getHandlerList().unregister(this), 200);
        }

        @EventHandler(ignoreCancelled = true)
        public void reflectDamageEvent(EntityDamageByEntityEvent event) {
            if (event.getEntity().equals(player) && event.getDamager() instanceof Damageable) {
                ((Damageable) event.getDamager()).damage(event.getDamage(), player);
            }
        }
    }
}
