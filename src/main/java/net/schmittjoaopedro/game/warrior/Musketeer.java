package net.schmittjoaopedro.game.warrior;

import net.schmittjoaopedro.game.Arena;
import net.schmittjoaopedro.game.Player;

public class Musketeer extends Warrior {

    private Player player;

    private double life = 598.0;

    public Musketeer(Player player, Arena arena, double x, double y) {
        super("M", arena, x, y);
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public double getLife() {
        return this.life;
    }

    public DamageType getDamageType() {
        return DamageType.AirGround;
    }

    public WarriorType getWarriorType() {
        return WarriorType.Ground;
    }

    public double getRange() {
        return 6;
    }

    public boolean attack(double damage) {
        this.life -= damage;
        return this.life <= 0;
    }

    public double getElixirCost() {
        return 4.0;
    }

    public double getDamage() {
        return 160.0;
    }

    private Warrior findNearestEnemy() {
        Warrior enemy = null;
        double distance = Double.MAX_VALUE;
        for(Warrior warrior : this.arena.getWarriors()) {
            if(warrior.getPlayer() != this.getPlayer() && this.calculateDistance(warrior) < distance) {
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
}
