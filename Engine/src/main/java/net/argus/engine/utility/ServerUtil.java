package net.argus.engine.utility;

import net.argus.engine.utility.color.Palette;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

public class ServerUtil {

    private ServerUtil() {}

    public static Player getPlayer(String name) {
        return Bukkit.getPlayer(name);
    }

    public static Player getPlayer(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    public static Collection<? extends Player> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers();
    }

    public static void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, Bukkit.getPluginManager().getPlugins()[0]);
    }

    public static void unregisterListener(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    public static void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }

    public static void sendAllMessage(Palette palette, String message) {
        final String format = StringUtil.format(palette, message);
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(format));
    }

    public static void sendLocalMessage(Location location, double range, Palette palette, String message) {
        message = StringUtil.format(palette, message);

        Iterator<Player> players = location.getWorld().getPlayers().iterator();
        while (players.hasNext()) {
            Player player = players.next();
            if (location.distanceSquared(player.getLocation()) <= range * range) {
                player.sendMessage(message);
            }
        }
    }

}
