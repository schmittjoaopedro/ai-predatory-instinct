package net.schmittjoaopedro.game.warrior;

import net.schmittjoaopedro.game.Arena;
import net.schmittjoaopedro.game.Player;

public abstract class Warrior {

    protected Warrior target;

    private double x;

    private double y;

    private String letter;

    protected Arena arena;

    private double stepSize = 1.0;

    public Warrior(String letter, Arena arena, double x, double y) {
        super();
        this.letter = letter;
        this.arena = arena;
        this.setX(x);
        this.setY(y);
    }

    public abstract Player getPlayer();

    public abstract double getLife();

    public abstract DamageType getDamageType();

    public abstract WarriorType getWarriorType();

    public abstract double getRange();

    public abstract double getDamage();

    public abstract boolean attack(double damage);

    public abstract double getElixirCost();

    public void special(Arena environment) {}

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getLetter() {
        return letter;
    }

    public double getStepSize() {
        return stepSize;
    }

    public boolean isDead() {
        return this.getLife() <= 0;
    }

    public abstract void nextStep();

    protected void moveTo(Warrior warrior) {
        double sX = warrior.getX() - this.getX();
        double sY = warrior.getY() - this.getY();
        double sXA = Math.abs(sX);
        double sYA = Math.abs(sY);
        if(sX < 0 && sXA >= sYA) {
            this.setX(this.getX() - this.getStepSize());
        } else if(sY < 0 && sYA > sXA) {
            this.setY(this.getY() - this.getStepSize());
        } else if (sX > 0 && sXA >= sYA) {
            this.setX(this.getX() + this.getStepSize());
        } else if (sY > 0 && sYA > sXA) {
            this.setY(this.getY() + this.getStepSize());
        }
    }

    public double calculateDistance(Warrior enemy) {
        return Math.sqrt(Math.pow(this.getX() - enemy.getX(), 2) + Math.pow(this.getY() - enemy.getY(), 2));
    }

    public double calculateDistance(double x1, double y1, Warrior enemy) {
        return Math.sqrt(Math.pow(x1 - enemy.getX(), 2) + Math.pow(y1 - enemy.getY(), 2));
    }

    public Arena getArena() {
        return arena;
    }

    public Warrior getTarget() {
        return target;
    }
}
