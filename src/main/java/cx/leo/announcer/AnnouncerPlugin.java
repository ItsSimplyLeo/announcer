package cx.leo.announcer;

import cx.leo.announcer.file.FileManager;
import cx.leo.announcer.objects.AnnouncementManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class AnnouncerPlugin extends JavaPlugin {

    private FileManager fileManager;
    private AnnouncementManager announcementManager;

    @Override
    public void onEnable() {
        this.fileManager = new FileManager(this);
        this.fileManager.register("config");

        this.announcementManager = new AnnouncementManager(this);
    }

    @Override
    public void onDisable() {
        this.announcementManager.getAnnouncerTask().cancel();
    }

    @Override
    public @NotNull FileConfiguration getConfig() {
        return fileManager.getFile("config").getConfig();
    }

    @Override
    public void reloadConfig() {
        this.fileManager.getFile("config").reload();
    }

    @Override
    public void saveConfig() {
        this.fileManager.getFile("config").save();
    }

    public AnnouncementManager getAnnouncementManager() {
        return announcementManager;
    }
}