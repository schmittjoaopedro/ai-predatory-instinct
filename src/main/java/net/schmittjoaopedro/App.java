package net.schmittjoaopedro;

import net.schmittjoaopedro.game.Arena;
import net.schmittjoaopedro.game.Player;
import net.schmittjoaopedro.game.warrior.*;
import net.schmittjoaopedro.ui.JView;

import java.awt.*;
import java.util.Locale;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        Arena arena = new Arena(11, 20);

        Player player1 = new Player(0, 0, 10, 11, Color.RED, arena);
        Player player2 = new Player(10, 0, 20, 11, Color.BLUE, arena);

        player1.randomInit(100);
        player2.randomInit(100);

        JView view = new JView(800, 700);
        view.draw(arena);

        for(int i = 0; i < 1000; i++) {
            if(player1.isDead() || player2.isDead()) break;
            System.out.format(Locale.US,"P1[%.2f\t%.2f]\t\tp2[%.2f\t%.2f]\t", player1.getLifeAmount(), player1.getDamageAmount(), player2.getLifeAmount(), player2.getDamageAmount());
            arena.advanceStep();
            Thread.sleep(500);
            view.draw(arena);
            Thread.sleep(500);
        }

        if(player1.isDead()) {
            System.out.println("Player2 Win!!");
        } else {
            System.out.println("Player1 Win!!");
        }

    }
}
