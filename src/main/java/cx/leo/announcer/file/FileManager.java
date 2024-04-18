package cx.leo.announcer.file;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class FileManager {

    private final JavaPlugin plugin;
    private final HashMap<String, Yaml> files;

    public FileManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.files = new HashMap<>();
    }

    @Nullable
    public Yaml getFile(String fileName) {
        return files.get(fileName.toLowerCase());
    }

    public void register(String fileName) {
        Yaml yaml = new Yaml(plugin, fileName);
        this.files.put(fileName.toLowerCase(), yaml);
    }

    public void reload() {
        files.values().forEach(Yaml::reload);
    }

}
