package io.github.rm2023.Artifacts.Rewards;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.rm2023.Artifacts.Main;
import io.github.rm2023.Artifacts.RewardBases.Passive;

public class Beheading extends Passive {

    Random rng = new Random();

    @Override
    public String getName() {
        return "Beheading";
    }

    @Override
    public String getDescription() {
        return "50% chance to take a player's head upon killing them. 5% chance for a mob's head. (does not apply to wither skeletons)";
    }

    @Override
    public ItemStack getRepresentationStack() {
        ItemStack item = new ItemStack(Material.CREEPER_HEAD);
        return item;
    }

    @Override
    public Tier getTier() {
        return Tier.RARE;
    }

    @Override
    public double getRarity() {
        return 1;
    }

    @EventHandler(ignoreCancelled = true)
    public void BeheadingEvent(EntityDeathEvent event) {
        if (validateWorld(event.getEntity().getWorld()) && enabledPlayers.contains(((LivingEntity) event.getEntity()).getKiller())) {
            if (event.getEntity() instanceof Player && rng.nextInt(2) == 0) {
                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) head.getItemMeta();
                meta.setOwningPlayer((Player) event.getEntity());
                head.setItemMeta(meta);
                event.getEntity().getLocation().getWorld().dropItemNaturally(event.getEntity().getLocation(), head);
            } else if (rng.nextInt(1) == 0) {
                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) head.getItemMeta();
                meta.setOwningPlayer(Main.plugin.getServer().getOfflinePlayer("MHF_" + event.getEntity().getName()));
                meta.setDisplayName(event.getEntity().getName() + "'s Head");
                head.setItemMeta(meta);
                event.getEntity().getLocation().getWorld().dropItemNaturally(event.getEntity().getLocation(), head);
            }
        }
        
    }
}
