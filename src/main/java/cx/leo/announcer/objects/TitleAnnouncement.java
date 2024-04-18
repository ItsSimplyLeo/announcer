package cx.leo.announcer.objects;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class TitleAnnouncement {

    private final Component header, subtitle;
    private final long fadeIn, stay, fadeOut;

    public TitleAnnouncement(@NotNull Component header, @NotNull Component subtitle) {
        this.header = header;
        this.subtitle = subtitle;
        this.fadeIn = 0L;
        this.stay = 0L;
        this.fadeOut = 0L;
    }

    public TitleAnnouncement(@NotNull Component header, @NotNull Component subtitle, long fadeIn, long stay, long fadeOut) {
        this.header = header;
        this.subtitle = subtitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public Component getHeader() {
        return header;
    }

    public Component getSubtitle() {
        return subtitle;
    }
}
