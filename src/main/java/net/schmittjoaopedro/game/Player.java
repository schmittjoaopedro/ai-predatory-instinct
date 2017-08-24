package net.schmittjoaopedro.game;


import net.schmittjoaopedro.game.warrior.Tower;
import net.schmittjoaopedro.game.warrior.Warrior;

import java.awt.*;

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

    private int deckSize;

    private boolean evolving = true;

    private boolean stop = false;

    public Player(double LH, double LW, double UH, double UW, Color color, Arena arena, boolean initTower) {
        super();
        this.UH = UH;
        this.LH = LH;
        this.UW = UW;
        this.LW = LW;
        this.color = color;
        this.arena = arena;
        if(initTower) {
            tower = new Tower(this, arena, (UW - LW - 1.0) / 2.0, LH == 0.0 ? 0.0 : UH - 1);
            arena.getWarriors().add(tower);
            lifeAmount += tower.getLife();
            arena.getPlayers().add(this);
        }
    }

    public void randomInit(int deckSize) {
        this.deckSize = deckSize;
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
        if(!stop) {
            elixirAmount++;
            String nextCard = cardsSequence[currentCard];
            if (currentCard < cardsSequence.length && WarriorManager.getInstance().warriorAvailable(nextCard, elixirAmount)) {
                Warrior warrior = WarriorManager.getInstance().getCard(this, arena, nextCard, cardXPosition[currentCard], cardYPosition[currentCard]);
                this.elixirAmount -= warrior.getElixirCost();
                arena.getWarriors().add(warrior);
                lifeAmount += warrior.getLife();
                currentCard++;
            }
            if (currentCard >= cardsSequence.length) {
                currentCard = 0;
                if (!this.isEvolving()) {
                    this.randomInit(this.getDeckSize());
                }
            }
        }
    }

    public boolean isDead() {
        return this.tower.isDead();
    }

    public void setLifeAmount(double lifeAmount) {
        this.lifeAmount = lifeAmount;
    }

    public void setDamageAmount(double damageAmount) {
        this.damageAmount = damageAmount;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public int getDeckSize() {
        return deckSize;
    }

    public void setDeckSize(int deckSize) {
        this.deckSize = deckSize;
    }

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    public String[] getCardsSequence() {
        return cardsSequence;
    }

    public void setCardsSequence(String[] cardsSequence) {
        this.cardsSequence = cardsSequence;
    }

    public double[] getCardXPosition() {
        return cardXPosition;
    }

    public void setCardXPosition(double[] cardXPosition) {
        this.cardXPosition = cardXPosition;
    }

    public double[] getCardYPosition() {
        return cardYPosition;
    }

    public void setCardYPosition(double[] cardYPosition) {
        this.cardYPosition = cardYPosition;
    }

    public int getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(int currentCard) {
        this.currentCard = currentCard;
    }

    public boolean isEvolving() {
        return evolving;
    }

    public void setEvolving(boolean evolving) {
        this.evolving = evolving;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public Player clone(Arena arena, boolean deep) {
        Player player = new Player(this.getLH(), this.getLW(), this.getUH(), this.getUW(), this.getColor(), arena, false);
        player.setDeckSize(this.getDeckSize());
        player.setDamageAmount(this.getDamageAmount());
        player.setElixirAmount(this.getElixirAmount());
        player.setLifeAmount(this.getLifeAmount());
        player.setDeck(this.getCardsSequence().clone(), this.getCardXPosition().clone(), this.getCardYPosition().clone());
        player.setCurrentCard(this.getCurrentCard());
        if(deep) {
            for(Warrior warrior : this.arena.getWarriors()) {
                if(warrior.getPlayer().equals(this) && !warrior.isDead()) {
                    Warrior newWarrior = warrior.clone(arena, player);
                    arena.getWarriors().add(newWarrior);
                    if(warrior.getClass().equals(Tower.class) && warrior.getPlayer().equals(this)) {
                        player.setTower((Tower) warrior);
                    }
                }
            }
        }
        return player;
    }

    @Override
    public String toString() {
        String body = "";
        for(String s : getCardsSequence()) {
            body += s;
        }
        return body;
    }
}
