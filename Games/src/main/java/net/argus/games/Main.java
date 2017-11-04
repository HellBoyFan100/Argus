package net.argus.games;

import net.argus.engine.Arena;
import net.argus.games.hub.HubArena;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Arena arena = new HubArena();

    @Override
    public void onEnable() {
        arena.enable();
    }

    @Override
    public void onDisable() {
        arena.disable();
    }

}
