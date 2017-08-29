package net.schmittjoaopedro.ia;

import net.schmittjoaopedro.game.Arena;
import net.schmittjoaopedro.game.Player;

import java.awt.*;

/**
 * Created by root on 28/08/17.
 */
public class ThreadSimulator extends Thread {

    private Arena arena;

    private int stepSize;

    private double fitness;

    private Color colorToEvolve;

    public ThreadSimulator(Arena arena, int stepSize, Color colorToEvolve) {
        this.arena = arena;
        this.stepSize = stepSize;
        this.colorToEvolve = colorToEvolve;
    }

    @Override
    public void run() {
        for(int i = 0; i < this.stepSize; i++) {
            arena.advanceStep();
        }
        Player player = arena.getPlayerByColor(colorToEvolve);
        this.fitness = player.getLifeAmount() - player.getDamageAmount();
    }

    public double getFitness() {
        return fitness;
    }
}
