package cx.leo.announcer.file;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Yaml {

    private final JavaPlugin plugin;
    private final String name;
    private final boolean data;
    private YamlConfiguration yamlConfiguration;

    public Yaml(JavaPlugin plugin, String name) {
        this(plugin, name, false);
    }

    public Yaml(JavaPlugin plugin, String name, boolean data) {
        this.plugin = plugin;
        this.name = name;
        this.data = data;
        this.reload();
    }

    public YamlConfiguration getConfig() {
        return yamlConfiguration;
    }

    public String getName() {
        return name;
    }

    public String getRealName() {
        String realName = name + ".yml";
        if (data) realName = File.separator + "data" + File.separator + realName;
        return realName;
    }

    public void reload() {
        File file = new File(plugin.getDataFolder(), getRealName());

        if (!file.exists()) {
            boolean success = file.getParentFile().mkdirs();
            if (!data) {
                plugin.saveResource(getRealName(), false);
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        this.yamlConfiguration = new YamlConfiguration();

        try {
            yamlConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        File file = new File(plugin.getDataFolder(), getRealName());
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    }
}
