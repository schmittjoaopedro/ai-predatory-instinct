package net.schmittjoaopedro.ia;

import net.schmittjoaopedro.game.Arena;
import net.schmittjoaopedro.game.Player;

import java.awt.*;

/**
 * Created by root on 22/08/17.
 */
public class GeneticAlgorithm {

    private Color colorToEvolve;

    private Arena originalArena;

    private Arena[] arenas;

    private int popSize = 30;

    public GeneticAlgorithm(Arena arena, Color color) {
        this.originalArena = arena;
        this.colorToEvolve = color;
    }

    public void reset() {
        arenas = new Arena[popSize];
        for(int i = 0; i < popSize; i++) {
            arenas[i] = originalArena.clone(true);
            Player player = arenas[i].getPlayerByColor(colorToEvolve);
            player.randomInit(player.getDeckSize());
        }
    }

    public void evolve() {
        reset();
        double[] fitness = calculateFitness(arenas);
        int best = 0;
        for(int i = 1; i < fitness.length; i++) {
            if(fitness[i] > fitness[best]) {
                best = i;
            }
        }
        Player bestPlayer = arenas[best].getPlayerByColor(colorToEvolve);
        originalArena.getPlayerByColor(colorToEvolve).setDeck(bestPlayer.getCardsSequence(), bestPlayer.getCardXPosition(), bestPlayer.getCardYPosition());
    }

    public double[] calculateFitness(Arena[] arenas) {
        double[] fitness = new double[arenas.length];
        for(int a = 0; a < arenas.length; a++) {
            for(int i = 0; i < 100; i++) {
                arenas[a].advanceStep();
            }
            Player player = arenas[a].getPlayerByColor(colorToEvolve);
            fitness[a] = player.getLifeAmount() - player.getDamageAmount();
        }
        return fitness;
    }

}
