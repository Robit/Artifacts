package io.github.rm2023.Artifacts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.rm2023.Artifacts.RewardBases.Reward;

public class DataManager {
    private static HashMap<String, DataManager> dataManagers = new HashMap<String, DataManager>();
    private YamlConfiguration data;
    private File file;
    private ExecutorService loadExecutor = Executors.newSingleThreadExecutor();
    private ExecutorService saveExecutor = Executors.newSingleThreadExecutor();

    private DataManager(String file) {
        this.data = new YamlConfiguration();
        this.file = new File(Main.plugin.getDataFolder(), file);
        loadExecutor.execute(() -> {
            try {
                this.file.getParentFile().mkdirs();
                this.file.createNewFile();
                data.load(this.file);
                if (!data.isConfigurationSection("passives")) {
                    data.createSection("passives");
                }
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        });
        loadExecutor.shutdown();
        dataManagers.put(file, this);
    }

    public YamlConfiguration getData() {
        try {
            loadExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        return data;
    }

    public void save() {
        try {
            loadExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        String toSave = data.saveToString();
        saveExecutor.execute(() -> {
            try (FileWriter f = new FileWriter(file)) {
                f.write(toSave);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void saveAll() {
        dataManagers.values().forEach((manager) -> manager.save());
    }

    public static DataManager getRewardData(Reward reward) {
        return getFileData("data/reward/" + reward.getID() + ".yml");
    }

    public static DataManager getPlayerData(Player player) {
        return getFileData("data/player/" + player.getUniqueId().toString() + ".yml");
    }

    public static DataManager getFileData(String file) {
        return dataManagers.containsKey(file) ? dataManagers.get(file) : new DataManager(file);
    }

    public static void reload() {
        dataManagers.values().forEach((manager) -> manager.saveExecutor.shutdown());
        dataManagers.values().forEach((manager) -> {
            try {
                manager.saveExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        dataManagers.clear();
        for (Player player : Main.plugin.getServer().getOnlinePlayers()) {
            DataManager.getPlayerData(player);
        }
    }
}
