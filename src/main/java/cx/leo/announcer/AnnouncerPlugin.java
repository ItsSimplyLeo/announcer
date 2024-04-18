package cx.leo.announcer;

import cx.leo.announcer.commands.AnnouncerCommand;
import cx.leo.announcer.file.FileManager;
import cx.leo.announcer.objects.AnnouncementManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AnnouncerPlugin extends JavaPlugin {

    private FileManager fileManager;
    private AnnouncementManager announcementManager;

    @Override
    public void onEnable() {
        this.fileManager = new FileManager(this);
        this.fileManager.register("config");

        this.announcementManager = new AnnouncementManager(this);

        AnnouncerCommand announcerCommand = new AnnouncerCommand(this);
        @Nullable PluginCommand command = getCommand("announcer");

        if (command == null) return;

        command.setExecutor(announcerCommand);
        command.setTabCompleter(announcerCommand);
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