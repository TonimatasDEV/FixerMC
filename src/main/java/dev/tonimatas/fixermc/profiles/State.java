package dev.tonimatas.fixermc.profiles;

import java.awt.*;

public enum State {
    NORMAL("Play", Color.GREEN.darker().darker(), true),
    LOADING("Loading...", Color.ORANGE.darker(), true),
    RUNNING("Kill", Color.RED.darker(), true),
    DOWNLOADING("Downloading...", Color.BLUE.brighter(), true),
    DELETING("Deleting...", Color.DARK_GRAY, true);


    public final String title;
    public final Color color;
    public final boolean alwaysVisible;

    State(String title, Color color, boolean alwaysVisible) {
        this.title = title;
        this.color = color;
        this.alwaysVisible = alwaysVisible;
    }
}
