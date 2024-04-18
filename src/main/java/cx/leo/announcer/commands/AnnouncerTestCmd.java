package cx.leo.announcer.commands;

import cx.leo.announcer.AnnouncerPlugin;
import cx.leo.announcer.objects.Announcement;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AnnouncerTestCmd implements CommandExecutor {

    private final AnnouncerPlugin plugin;

    public AnnouncerTestCmd(AnnouncerPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) return true;

        String targetAnnouncement = args[0];

        Announcement announcement = plugin.getAnnouncementManager().getAnnouncements().stream().filter(a -> a.getId().equalsIgnoreCase(targetAnnouncement))
                .findAny().orElse(null);

        if (announcement == null) return true;
        else announcement.send(sender);

        return true;
    }
}