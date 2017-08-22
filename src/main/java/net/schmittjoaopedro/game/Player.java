package net.schmittjoaopedro.game;


import net.schmittjoaopedro.game.warrior.Tower;
import net.schmittjoaopedro.game.warrior.Warrior;

import java.awt.*;
import java.util.List;

public class Player {

    private double elixirAmount = 0.0;

    private int currentCard = 0;

    private double UH;

    private double LH;

    private double UW;

    private double LW;

    private Color color;

    private String[] cardsSequence;

    private double[] cardXPosition;

    private double[] cardYPosition;

    private double lifeAmount;

    private double damageAmount;

    private Arena arena;

    private Tower tower;

    public Player(double LH, double LW, double UH, double UW, Color color, Arena arena) {
        super();
        this.UH = UH;
        this.LH = LH;
        this.UW = UW;
        this.LW = LW;
        this.color = color;
        this.arena = arena;
        tower = new Tower(this, arena, (UW - LW - 1.0) / 2.0, LH == 0.0 ? 0.0 : UH - 1);
        arena.getWarriors().add(tower);
        lifeAmount += tower.getLife();
        arena.getPlayers().add(this);
    }

    public void randomInit(int deckSize) {
        cardXPosition = new double[deckSize];
        cardYPosition = new double[deckSize];
        cardsSequence = new String[deckSize];
        for(int i = 0; i < deckSize; i++) {
            cardXPosition[i] = (int) (LW + Math.random() * UW);
            cardYPosition[i] = (int) (LH + Math.random() * UH);
            cardsSequence[i] = WarriorManager.getInstance().getRandomLetter();
        }
    }

    public void setDeck(String[] deck, double[] x, double[] y) {
        this.cardsSequence = deck;
        this.cardXPosition = x;
        this.cardYPosition = y;
    }

    public double getElixirAmount() {
        return elixirAmount;
    }

    public void setElixirAmount(double elixirAmount) {
        this.elixirAmount = elixirAmount;
    }

    public double getUH() {
        return UH;
    }

    public double getLH() {
        return LH;
    }

    public double getUW() {
        return UW;
    }

    public double getLW() {
        return LW;
    }

    public Color getColor() {
        return color;
    }

    public void attack(double damage) {
        this.damageAmount += damage;
    }

    public double getLifeAmount() {
        return lifeAmount;
    }

    public double getDamageAmount() {
        return damageAmount;
    }

    public void nextStep() {
        elixirAmount++;
        String nextCard = cardsSequence[currentCard];
        if(currentCard < cardsSequence.length && WarriorManager.getInstance().warriorAvailable(nextCard, elixirAmount)) {
            Warrior warrior = WarriorManager.getInstance().getCard(this, arena, nextCard, cardXPosition[currentCard], cardYPosition[currentCard]);
            this.elixirAmount -= warrior.getElixirCost();
            arena.getWarriors().add(warrior);
            lifeAmount += warrior.getLife();
            currentCard++;
        }
    }

    public boolean isDead() {
        return this.tower.isDead();
    }

}
