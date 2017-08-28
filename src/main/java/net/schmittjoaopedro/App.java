package net.schmittjoaopedro;

import net.schmittjoaopedro.game.Arena;
import net.schmittjoaopedro.game.Player;
import net.schmittjoaopedro.ia.GeneticAlgorithm;
import net.schmittjoaopedro.ui.JView;

import java.awt.*;
import java.util.Locale;

public class App  {

    public static void main( String[] args ) throws Exception {

        int p1 = 0;
        int p2 = 0;

        for(int p = 0; p < 1; p++) {

            Arena arena = new Arena(11, 20);

            Player player1 = new Player(0, 0, 10, 11, Color.RED, arena, true);
            player1.setEvolving(false);
            Player player2 = new Player(10, 0, 20, 11, Color.BLUE, arena, true);
            player2.setEvolving(true);

            player1.randomInit(10);
            player2.randomInit(10);

            JView view = new JView(800, 600);

            GeneticAlgorithm gaRed = new GeneticAlgorithm(arena, Color.BLUE, Color.RED, 20, 100, 40, 0.015);

            for (int i = 0; i < 1000; i++) {
                if (player1.isDead() || player2.isDead()) break;
                System.out.format(Locale.US, "P1[%.2f]\t\tp2[%.2f]\t", (player1.getLifeAmount() - player1.getDamageAmount()), (player2.getLifeAmount() - player2.getDamageAmount()));
                arena.advanceStep(true);
                view.draw(arena);
                Thread.sleep(100);
                gaRed.evolve();
            }

            if (player1.isDead()) {
                p2++;
                System.out.println("Player2 Win!!");
            } else {
                p1++;
                System.out.println("Player1 Win!!");
            }
            System.out.println(p);
        }

        System.out.format("p1 = %d\t\t p2 = %d", p1, p2);

    }
}
