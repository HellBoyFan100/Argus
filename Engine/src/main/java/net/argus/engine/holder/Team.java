package net.argus.engine.holder;

import net.argus.core.Named;
import net.argus.engine.utility.color.Colored;
import org.bukkit.entity.Player;

public interface Team extends Colored, Named, Group<Player> {
}
