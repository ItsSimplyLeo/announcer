package cx.leo.announcer.objects;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class Announcement {

    private final String id;
    private final List<Component> chatLines;
    private final Component actionBarMessage;
    private final TitleAnnouncement titleAnnouncement;

    public Announcement(@NotNull String id, List<Component> chatLines, Component actionBarMessage, TitleAnnouncement titleAnnouncement) {
        this.id = id;
        this.chatLines = chatLines;
        this.actionBarMessage = actionBarMessage;
        this.titleAnnouncement = titleAnnouncement;
    }

    @NotNull
    public String getId() {
        return id;
    }

    @Nullable
    public List<Component> getChatLines() {
        return chatLines;
    }

    @Nullable
    public Component getActionBarMessage() {
        return actionBarMessage;
    }

    @Nullable
    public TitleAnnouncement getTitleAnnouncement() {
        return titleAnnouncement;
    }

    public void send(@NotNull Audience audience) {
        if (getChatLines() != null) {
            TextComponent.@NotNull Builder message = Component.text();
            Iterator<Component> componentIterator = getChatLines().iterator();

            while (componentIterator.hasNext()) {
                message.append(componentIterator.next());
                if (componentIterator.hasNext()) message.appendNewline();
            }

            audience.sendMessage(message.build());
        }

        if (getActionBarMessage() != null) {
            audience.sendActionBar(getActionBarMessage());
        }


        if (titleAnnouncement != null) { // Todo Add fade-in // fade-out and stay options
            audience.showTitle(Title.title(titleAnnouncement.getHeader(), titleAnnouncement.getSubtitle()));
        }
    }
}
