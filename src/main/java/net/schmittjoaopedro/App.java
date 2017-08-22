package net.schmittjoaopedro;

import net.schmittjoaopedro.game.Arena;
import net.schmittjoaopedro.game.Player;
import net.schmittjoaopedro.game.warrior.*;
import net.schmittjoaopedro.ui.JView;

import java.awt.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        Arena arena = new Arena(11, 20);

        Player player1 = new Player(0, 0, 10, 11, Color.RED);
        Player player2 = new Player(10, 0, 20, 11, Color.BLUE);
        arena.getPlayers().add(player1);
        arena.getPlayers().add(player2);

        arena.getWarriors().add(new Tower(player1, arena, 5, 0));
        arena.getWarriors().add(new Giant(player1, arena, 0, 9));
        arena.getWarriors().add(new Dragon(player1, arena, 2, 7));
        arena.getWarriors().add(new Musketeer(player1, arena, 3, 5));

        arena.getWarriors().add(new Giant(player2, arena, 8, 13));
        arena.getWarriors().add(new Tower(player2, arena, 5, 19));
        arena.getWarriors().add(new Musketeer(player2, arena, 3, 15));
        arena.getWarriors().add(new Witch(player2, arena, 9, 19));

        JView view = new JView(800, 700);
        view.draw(arena);

        for(int i = 0; i < 100; i++) {
            arena.advanceStep();
            view.draw(arena);
            Thread.sleep(500);
        }

    }
}
