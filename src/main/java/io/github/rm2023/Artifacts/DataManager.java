package io.github.rm2023.Artifacts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.rm2023.Artifacts.RewardBases.Reward;

public class DataManager {
    private static ArrayList<DataManager> dataManagers = new ArrayList<DataManager>();
    private YamlConfiguration data;
    private File file;
    private ExecutorService loadExecutor = Executors.newSingleThreadExecutor();
    private ExecutorService saveExecutor = Executors.newSingleThreadExecutor();

    private DataManager(File file) {
        this.data = new YamlConfiguration();
        loadExecutor.execute(() -> {
            try {
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                data.load(file);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        });
        loadExecutor.shutdown();
        dataManagers.add(this);
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
        return getFileData(new File(Main.plugin.getDataFolder(), "data/reward/" + reward.getID() + ".yml"));
    }

    public static DataManager getPlayerData(Player p) {
        return getFileData(new File(Main.plugin.getDataFolder(), "data/player/" + p.getUniqueId().toString() + ".yml"));
    }

    public static DataManager getFileData(File file) {
        for (DataManager manager : dataManagers) {
            if (manager.file.equals(file)) {
                return manager;
            }
        }
        return new DataManager(file);
    }
}
