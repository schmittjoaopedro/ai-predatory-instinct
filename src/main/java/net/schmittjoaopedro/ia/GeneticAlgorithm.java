package net.schmittjoaopedro.ia;

import net.schmittjoaopedro.game.Arena;
import net.schmittjoaopedro.game.Player;
import net.schmittjoaopedro.game.WarriorManager;

import java.awt.*;

/**
 * Created by root on 22/08/17.
 */
public class GeneticAlgorithm {

    private Color colorToEvolve;

    private Color colorEnemy;

    private Arena originalArena;

    private Arena[] arenas;

    private int popSize;

    private int maxGen;

    private int stepSize;

    private double pM;

    public GeneticAlgorithm(Arena arena, Color color, Color enemy, int popSize, int maxGen, int stepSize, double pM) {
        this.originalArena = arena;
        this.colorToEvolve = color;
        this.colorEnemy = enemy;
        this.popSize = popSize;
        this.maxGen = maxGen;
        this.pM = pM;
        this.stepSize = stepSize;
        configure();
    }

    public void configure() {
        arenas = new Arena[popSize];
        for(int i = 0; i < popSize; i++) {
            arenas[i] = originalArena.clone(true);
            Player warrior = arenas[i].getPlayerByColor(colorToEvolve);
            warrior.randomInit(warrior.getDeckSize());
            Player enemy = arenas[i].getPlayerByColor(colorEnemy);
            enemy.setStop(true);
        }
    }

    public void reconfigure() {
        Arena[] last = arenas;
        arenas = new Arena[popSize];
        for(int i = 0; i < popSize; i++) {
            arenas[i] = originalArena.clone(true);
            //Main
            Player warrior = arenas[i].getPlayerByColor(colorToEvolve);
            Player lastWarrior = last[i].getPlayerByColor(colorToEvolve);
            warrior.setDeck(lastWarrior.getCardsSequence(), lastWarrior.getCardXPosition(), lastWarrior.getCardYPosition());
            //Enemy
            Player enemy = arenas[i].getPlayerByColor(colorEnemy);
            enemy.setStop(true);
            lastWarrior = last[i].getPlayerByColor(colorEnemy);
            enemy.setDeck(lastWarrior.getCardsSequence(), lastWarrior.getCardXPosition(), lastWarrior.getCardYPosition());
        }
    }

    public void evolve() {
        for(int g = 0; g < maxGen; g++) {
            reconfigure();
            Arena population[] = new Arena[arenas.length];
            double[] fitness = calculateFitness(arenas);
            population[0] = getFittest(fitness);
            for(int p = 1; p < arenas.length; p++) {
                Player a1 = arenas[tournamentSelection(fitness)].getPlayerByColor(colorToEvolve);
                Player a2 = arenas[tournamentSelection(fitness)].getPlayerByColor(colorToEvolve);
                Arena offspring = crossover(a1, a2);
                mutate(offspring);
                population[p] = offspring;
            }
            arenas = population;
        }
        double[] fitness = calculateFitness(arenas);
        Player bestPlayer = getFittest(fitness).getPlayerByColor(colorToEvolve);
        originalArena.getPlayerByColor(colorToEvolve).setDeck(bestPlayer.getCardsSequence(), bestPlayer.getCardXPosition(), bestPlayer.getCardYPosition());
    }

    private Arena getFittest(double[] fitness) {
        int best = 0;
        for(int i = 1; i < fitness.length; i++) {
            if(fitness[i] > fitness[best]) {
                best = i;
            }
        }
        Player player = arenas[best].getPlayerByColor(colorToEvolve);
        Arena newArena = originalArena.clone(true);
        newArena.getPlayerByColor(colorToEvolve).setDeck(player.getCardsSequence(), player.getCardXPosition(), player.getCardYPosition());
        return newArena;
    }

    public Arena crossover(Player parent1, Player parent2) {
        Arena arena = originalArena.clone(true);
        Player toEvolve = arena.getPlayerByColor(colorToEvolve);
        int startPos = (int) (Math.random() * parent1.getDeckSize());
        int endPos = (int) (Math.random() * parent1.getDeckSize());
        for (int i = 0; i < parent1.getDeckSize(); i++) {
            if ((startPos < endPos && i > startPos && i < endPos) ||
                    (startPos > endPos && !(i < startPos && i > endPos))) {
                toEvolve.getCardsSequence()[i] = parent1.getCardsSequence()[i];
                toEvolve.getCardXPosition()[i] = parent1.getCardXPosition()[i];
                toEvolve.getCardYPosition()[i] = parent1.getCardYPosition()[i];
            } else {
                toEvolve.getCardsSequence()[i] = parent2.getCardsSequence()[i];
                toEvolve.getCardXPosition()[i] = parent2.getCardXPosition()[i];
                toEvolve.getCardYPosition()[i] = parent2.getCardYPosition()[i];
            }
        }
        return arena;
    }

    private void mutate(Arena arena) {
        Player toEvolve = arena.getPlayerByColor(colorToEvolve);
        for (int i = 0; i < toEvolve.getDeckSize(); i++) {
            if (Math.random() < pM) {
                toEvolve.getCardsSequence()[i] = WarriorManager.getInstance().getRandomLetter();
                toEvolve.getCardXPosition()[i] = (int) (Math.random() * (toEvolve.getUW() - toEvolve.getLW()));
                toEvolve.getCardYPosition()[i] = (int) (Math.random() * (toEvolve.getUH() - toEvolve.getLH()));
            }
        }
    }

    public int tournamentSelection(double[] fitness) {
        int best = (int) (Math.random() * fitness.length);
        for(int i = 0; i < ((int) fitness.length * 0.2); i++) {
            if(fitness[(int) (Math.random() * fitness.length)] > fitness[best]) {
                best = i;
            }
        }
        return best;
    }

    public double[] calculateFitness(Arena[] arenas) {
        double[] fitness = new double[arenas.length];
        for(int a = 0; a < arenas.length; a++) {
            for(int i = 0; i < this.stepSize; i++) {
                arenas[a].advanceStep();
            }
            Player player = arenas[a].getPlayerByColor(colorToEvolve);
            fitness[a] = player.getLifeAmount() - player.getDamageAmount();
        }
        return fitness;
    }

}
