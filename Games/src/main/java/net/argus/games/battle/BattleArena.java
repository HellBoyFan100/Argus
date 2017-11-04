package net.argus.games.battle;

import net.argus.core.holder.MutableHolder;
import net.argus.engine.Arena;
import org.bukkit.entity.Player;

public class BattleArena extends Arena {

    public BattleArena(MutableHolder<Player> players) {

        //--States--
        State preState = new PreGameState(players);
        State gameState = new GameState(players);
        State endState = new EndState(players);


        //--Hooks--
        preState.onComplete(chain(preState::disable, gameState::enable));
        gameState.onComplete(chain(gameState::disable, endState::enable));
        endState.onComplete(chain(endState::disable, this::complete));

        onEnable(() -> {
            players.forEach(player -> player.sendMessage("The game is about to load..."));
        }, preState::enable);

        onComplete(this::disable);

        onDisable(() -> {
            players.forEach(player -> player.sendMessage("Sending you back to a random lobby..."));
        }, preState::disable, gameState::disable, endState::disable);
    }

    @Override
    public String getName() {
        return "Battle";
    }


}
