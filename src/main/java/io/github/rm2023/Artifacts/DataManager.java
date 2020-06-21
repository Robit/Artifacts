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
                if (!this.file.exists()) {
                    this.file.getParentFile().mkdirs();
                    this.file.createNewFile();
                    data.load(file);
                    data.createSection("passives");
                } else {
                    data.load(file);
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

    public static DataManager getRewardData(Reward reward) {
        return getFileData("data/reward/" + reward.getID() + ".yml");
    }

    public static DataManager getPlayerData(Player player) {
        return getFileData("data/player/" + player.getUniqueId().toString() + ".yml");
    }

    public static DataManager getFileData(String file) {
        return dataManagers.containsKey(file) ? dataManagers.get(file) : new DataManager(file);
    }
}
