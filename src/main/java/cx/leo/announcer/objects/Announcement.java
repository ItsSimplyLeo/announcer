package cx.leo.announcer.objects;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.util.List;

public class Announcement {

    private final List<Component> chatLines;
    private final Component actionBarMessage;

    public Announcement(List<Component> chatLines, Component actionBarMessage) {
        this.chatLines = chatLines;
        this.actionBarMessage = actionBarMessage;
    }

    public List<Component> getChatLines() {
        return chatLines;
    }

    public Component getActionBarMessage() {
        return actionBarMessage;
    }

    public void send(Audience audience) {

    }
}
