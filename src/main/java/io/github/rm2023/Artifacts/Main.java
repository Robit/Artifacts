package io.github.rm2023.Artifacts;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import io.github.rm2023.Artifacts.Commands.ArtifactAdminExecutor;
import io.github.rm2023.Artifacts.Commands.PassivesExecutor;
import io.github.rm2023.Artifacts.RewardBases.Reward;

public class Main extends JavaPlugin {

    public static Main plugin;
    public YamlConfiguration config;
    public RewardManager rewardManager;
    public Map<String, Reward> rewardMap;
    public List<String> enabledWorlds;
    public ArtifactItemManager artifactItemManager;

    @Override
    public void onLoad() {
        plugin = this;
        File configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            this.saveResource("config.yml", false);
        }
        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            this.getLogger().log(Level.SEVERE, "Error loading config information!");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Set<Class<? extends Reward>> rewardClasses = new Reflections("io.github.rm2023.Artifacts.Rewards").getSubTypesOf(Reward.class);
        rewardMap = rewardClasses.stream().filter((rewardClass) -> rewardClass.getPackage().getName().equals("io.github.rm2023.Artifacts.Rewards")).map((rewardClass) -> {
            try {
                return rewardClass.newInstance();
            } catch (InstantiationException e) {
                this.getLogger().severe("Failed to intantiate artifact " + rewardClass.getName());
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                this.getLogger().severe("Failed to intantiate artifact " + rewardClass.getName());
                e.printStackTrace();
            }
            return null;
        }).filter((reward) -> reward != null).collect(Collectors.toMap((reward) -> reward.getID(), (reward) -> reward));
        config.getStringList("disabledArtifacts").stream().forEach((disabledArtifact) -> {
            if (rewardMap.remove(disabledArtifact) == null) {
                getLogger().severe("Unable to disable " + disabledArtifact + ". Reward not found.");
            }
        });
        enabledWorlds = config.getStringList("enabledWorlds");
    }

    @Override
    public void onEnable() {
        rewardManager = new RewardManager(rewardMap);
        artifactItemManager = new ArtifactItemManager();

        getServer().getPluginManager().registerEvents(rewardManager, this);
        getServer().getPluginManager().registerEvents(artifactItemManager, this);

        getCommand("passives").setExecutor(new PassivesExecutor());
        getCommand("artifactAdmin").setExecutor(new ArtifactAdminExecutor());

        getServer().getPluginManager().addPermission(new Permission("artifacts.user", "The ability to use artifacts and manage your own passives"));
        getServer().getPluginManager().addPermission(new Permission("artifacts.admin", "Permission to use /artifactAdmin"));
    }

    public void onReload() {
        try {
            config.load(new File(this.getDataFolder(), "config.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            this.getLogger().log(Level.SEVERE, "Error loading config information!");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        rewardManager.reload();
    }

    @Override
    public void onDisable() {
        DataManager.saveAll();
    }
}

