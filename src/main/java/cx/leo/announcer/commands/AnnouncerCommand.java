package cx.leo.announcer.commands;

import cx.leo.announcer.AnnouncerPlugin;
import cx.leo.announcer.objects.Announcement;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * We will use a framework later ;)
 */
public class AnnouncerCommand implements CommandExecutor, TabExecutor {

    private static final MiniMessage MINIMESSAGE = MiniMessage.miniMessage();

    private final AnnouncerPlugin plugin;
    private final List<String> subcommands = List.of("reload", "test");

    public AnnouncerCommand(AnnouncerPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            sender.sendMessage(MINIMESSAGE.deserialize(
                    "<aqua>Currently running <white><plugin></white> version <white>v<version></white>.",
                    Placeholder.parsed("plugin", plugin.getName()), Placeholder.parsed("version", plugin.getPluginMeta().getVersion())
            ));
            return true;
        }

        String arg1 = args[0];

        if (arg1.equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            plugin.getAnnouncementManager().reload();
            sender.sendMessage(MINIMESSAGE.deserialize("<green>announcer has been reloaded."));
            return true;
        }

        if (arg1.equalsIgnoreCase("test")) {
            if (args.length < 2) return true;

            String announcementId = args[1];

            Announcement announcement = plugin.getAnnouncementManager().getAnnouncements().stream().filter(a -> a.getId().equalsIgnoreCase(announcementId))
                    .findAny().orElse(null);

            if (announcement == null) return true;
            else announcement.send(sender);

            return true;
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            String arg1 = args[0];
            StringUtil.copyPartialMatches(arg1, subcommands, completions);
        }

        if (args.length == 2) {
            String arg2 = args[1];
            if (args[0].equalsIgnoreCase("test")) {
                List<String> announcements = plugin.getAnnouncementManager().getAnnouncements().stream().map(Announcement::getId).toList();
                StringUtil.copyPartialMatches(arg2, announcements, completions);
            }
        }

        Collections.sort(completions);
        return completions;
    }
}
