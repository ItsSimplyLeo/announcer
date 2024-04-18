package cx.leo.announcer.objects;

import cx.leo.announcer.AnnouncerPlugin;
import cx.leo.announcer.task.AnnouncerTask;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementManager {

    private final static MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private final AnnouncerPlugin plugin;
    private final List<Announcement> announcements;
    private long delay = 100L; // Delay to start announcements after task.
    private long period = 300; // Period in seconds to send announcements

    private BukkitTask announcerTask = null;
    private int currentIndex = 0;

    public AnnouncementManager(AnnouncerPlugin plugin) {
        this.plugin = plugin;
        this.announcements = new ArrayList<>();
        this.reload();
    }

    public void reload() {
        this.announcements.clear();
        if (announcerTask != null) this.announcerTask.cancel();

        FileConfiguration config = plugin.getConfig();

        ConfigurationSection settingsSection = config.getConfigurationSection("settings");
        if (settingsSection != null) {
            this.delay = 20L * settingsSection.getLong("delay", 10);
            this.period = 20L * settingsSection.getLong("delay", 300);
        }

        ConfigurationSection announcementSection = config.getConfigurationSection("announcements");
        if (announcementSection == null) {
            plugin.getLogger().warning("Could not load \"announcements\" ConfigurationSection, please check the config.");
            return;
        }

        for (String id : announcementSection.getKeys(false)) {
            ConfigurationSection currentSection = config.getConfigurationSection("announcements." + id);
            if (currentSection == null) {
                plugin.getLogger().warning("Could not load announcement id \"" + id + "\" ConfigurationSection, please check the config.");
                return;
            }
            this.announcements.add(loadAnnouncement(currentSection));
        }

        this.announcerTask = new AnnouncerTask(plugin).runTaskTimer(plugin, delay, 20L * period);
    }

    private Announcement loadAnnouncement(@NotNull ConfigurationSection section) {
        String id = section.getName();

        List<Component> lines = section.getStringList("chat").stream().map(MINI_MESSAGE::deserialize).toList();
        Component actionBar = null;
        if (section.contains("action-bar")) {
            actionBar = MINI_MESSAGE.deserialize(section.getString("action-bar", "<red>ERROR"));
        }

        ConfigurationSection titleSection = section.getConfigurationSection("title");
        TitleAnnouncement titleAnnouncement = null;

        if (titleSection != null) {
            if (titleSection.contains("header") && titleSection.contains("subtitle")) {
                Component header = MINI_MESSAGE.deserialize(titleSection.getString("header", "<red>ERROR"));
                Component subtitle = MINI_MESSAGE.deserialize(titleSection.getString("subtitle", "<red>ERROR"));

                titleAnnouncement = new TitleAnnouncement(header, subtitle);
            }
        }

        return new Announcement(id, lines, actionBar, titleAnnouncement);
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public BukkitTask getAnnouncerTask() {
        return announcerTask;
    }

    public Announcement getNextAnnouncement() {
        return getNextAnnouncement(true);
    }

    public Announcement getNextAnnouncement(boolean increment) {
        int returnIndex = currentIndex;
        if (currentIndex + 1 >= announcements.size() || currentIndex < 0){
            // Reset the index back to 0
            returnIndex = 0;
        } else {
            // Get the next index
            returnIndex += 1;
        }

        Announcement next = announcements.get(returnIndex);
        if (increment) {
            this.currentIndex = returnIndex;
        }

        return next;
    }
}
