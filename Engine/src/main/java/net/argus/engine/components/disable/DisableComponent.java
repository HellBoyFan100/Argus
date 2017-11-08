package net.argus.engine.components.disable;

import net.argus.core.component.ParentComponent;
import org.bukkit.World;

import java.util.function.Predicate;

public class DisableComponent extends ParentComponent {


    //--GAME RULE--

    public static Predicate<World> daylightCycle(String value) {
        return setGameRule("doDaylightCycle", value);
    }

    public static Predicate<World> entityDrops(String value) {
        return setGameRule("doEntityDrops", value);
    }

    public static Predicate<World> fireSpread(String value) {
        return setGameRule("doFireTick", value);
    }

    public static Predicate<World> mobLoot(String value) {
        return setGameRule("doMobLoot", value);
    }

    public static Predicate<World> mobSpawning(String value) {
        return setGameRule("doMobSpawning", value);
    }

    public static Predicate<World> mobGriefing(String value) {
        return setGameRule("mobGriefing", value);
    }

    public static Predicate<World> naturalRegeneration(String value) {
        return setGameRule("naturalRegeneration", value);
    }

    public static Predicate<World> randomTickSpeed(String value) {
        return setGameRule("randomTickSpeed", value);
    }

    public static Predicate<World> deathMessages(String value) {
        return setGameRule("showDeathMessages", value);
    }

    public static Predicate<World> setGameRule(String rule, String value) {
        return world -> world.setGameRuleValue(rule, value);
    }

}
