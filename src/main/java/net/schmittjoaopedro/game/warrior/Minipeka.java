package net.schmittjoaopedro.game.warrior;

import net.schmittjoaopedro.game.Arena;
import net.schmittjoaopedro.game.Player;

/**
 * Created by root on 23/08/17.
 */
public class Minipeka extends Warrior {

    private Player player;

    private double life = 960.0;

    public Minipeka(Player player, Arena arena, double x, double y) {
        super("P", arena, x, y);
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public double getLife() {
        return this.life;
    }

    public void setLife(double life) {
        this.life = life;
    }

    public DamageType getDamageType() {
        return DamageType.Ground;
    }

    public WarriorType getWarriorType() {
        return WarriorType.Ground;
    }

    public double getRange() {
        return 1.0;
    }

    public double getDamage() {
        return 544.0;
    }

    public double getElixirCost() {
        return 4.0;
    }

    private Warrior findNearestEnemy() {
        Warrior enemy = null;
        double distance = Double.MAX_VALUE;
        for(Warrior warrior : this.arena.getWarriors()) {
            if(!WarriorType.Air.equals(warrior.getWarriorType()) && warrior.getPlayer() != this.getPlayer() && this.calculateDistance(warrior) < distance) {
                distance = this.calculateDistance(warrior);
                enemy = warrior;
            }
        }
        return enemy;
    }

    public void nextStep() {
        if(this.target == null || this.target.isDead()) {
            this.target = findNearestEnemy();
        }
        if(this.target != null && !this.target.isDead()) {
            if(this.calculateDistance(this.target) <= this.getRange()) {
                this.target.attack(this.getDamage());
            } else {
                this.moveTo(this.target);
            }
        }
    }

    @Override
    public Warrior clone(Arena arena, Player player) {
        Minipeka minipeka = new Minipeka(player, arena, this.getX(), this.getY());
        minipeka.setLife(this.getLife());
        minipeka.setId(this.getId());
        return minipeka;
    }
}
