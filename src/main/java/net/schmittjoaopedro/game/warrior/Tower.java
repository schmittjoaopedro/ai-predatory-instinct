package net.schmittjoaopedro.game.warrior;

import net.schmittjoaopedro.game.Arena;
import net.schmittjoaopedro.game.Player;

public class Tower extends Warrior {

    private Player player;

    private double life = 6392.0;

    public Tower(Player player, Arena arena, double x, double y) {
        super("T", arena, x, y);
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
        return DamageType.AirGround;
    }

    public WarriorType getWarriorType() {
        return WarriorType.Tower;
    }

    public double getRange() {
        return 7.0;
    }

    public double getDamage() {
        return 91.0;
    }

    public double getElixirCost() {
        return 0;
    }

    public void special(Arena environment) {}

    private Warrior findNearestEnemy() {
        Warrior enemy = null;
        double distance = Double.MAX_VALUE;
        for(Warrior warrior : this.arena.getWarriors()) {
            if(!WarriorType.Tower.equals(warrior.getWarriorType()) && warrior.getPlayer() != this.getPlayer() && this.calculateDistance(warrior) < distance) {
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
            }
        }
    }
}
