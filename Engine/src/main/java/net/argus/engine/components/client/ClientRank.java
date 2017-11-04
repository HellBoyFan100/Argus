package net.argus.engine.components.client;

import org.bukkit.ChatColor;

public enum ClientRank {

    DEVELOPER(ChatColor.DARK_GREEN, "DEV"),
    ADMIN(ChatColor.RED, "Admin"),
    FRIEND(ChatColor.AQUA, "Friend"),
    DONOR(ChatColor.YELLOW, "$$"),
    USER(ChatColor.GRAY, "");

    private final ChatColor color;
    private final String name;

    ClientRank(ChatColor color, String name) {
        this.color = color;
        this.name = name;
    }

    public boolean has(ClientRank clientRank) {
        return compareTo(clientRank) <= 0;
    }

    public String getFormat() {
        return getFormat(false);
    }

    public String getFormat(boolean raw) {
        return color + (raw ? name : "[" + name + "]");
    }

    public ChatColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

}
