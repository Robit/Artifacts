package io.github.rm2023.Artifacts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.rm2023.Artifacts.RewardBases.Reward;

public class DataManager {
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
        // TODO - system that either loads a new DataManager for a reward if none exists
        // yet OR returns an already loaded datamanager
        return null;
    }

    public static DataManager getPlayerData(Player p) {
        // TODO - system that either loads a new DataManager for a reward if none exists
        // yet OR returns an already loaded datamanager
        return null;
    }

}
