package net.argus.engine.utility;

import net.argus.core.component.Component;
import net.argus.engine.utility.color.Palette;
import org.bukkit.ChatColor;

public class StringUtil {

    private StringUtil() {}

    public static String format(Palette palette, String message) {
        return format(null, palette, message);
    }

    public static String format(Component component, Palette palette, String message) {
        message = message.replaceAll("\\{1\\}", palette.primary() + "");
        message = message.replaceAll("\\{2\\}", palette.secondary() + "");
        message = message.replaceAll("\\{3\\}", palette.tertiary() + "");
        message = ChatColor.translateAlternateColorCodes('&', message);

        return component == null ? (" " + message) : (ChatColor.BLUE + "[" + component.getName() + "] " + message);
    }


}
