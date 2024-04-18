package cx.leo.announcer.task;

import cx.leo.announcer.AnnouncerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AnnouncerTask extends BukkitRunnable {

    private final AnnouncerPlugin plugin;

    public AnnouncerTask(AnnouncerPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> plugin.getAnnouncementManager().getNextAnnouncement().send(player));
    }

}