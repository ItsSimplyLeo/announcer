package cx.leo.announcer;

import cx.leo.announcer.objects.AnnouncementManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AnnouncerPlugin extends JavaPlugin {

    private AnnouncementManager announcementManager;

    @Override
    public void onEnable() {
        this.announcementManager = new AnnouncementManager(this);
    }

    @Override
    public void onDisable() {
        this.announcementManager.getAnnouncerTask().cancel();
    }

    public AnnouncementManager getAnnouncementManager() {
        return announcementManager;
    }
}