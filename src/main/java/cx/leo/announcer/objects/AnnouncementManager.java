package cx.leo.announcer.objects;

import cx.leo.announcer.AnnouncerPlugin;
import cx.leo.announcer.task.AnnouncerTask;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementManager {

    private final AnnouncerPlugin plugin;
    private final List<Announcement> announcements;
    private final long delay = 100L; // Delay to start announcements after task.
    private final long period = 120; // Period in seconds to send announcements

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
        this.announcerTask = new AnnouncerTask(plugin).runTaskTimer(plugin, delay, 20L * period);
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
        if (increment) currentIndex += 1;
        return null;
    }
}
