package net.schmittjoaopedro.game.warrior;

import net.schmittjoaopedro.game.Arena;
import net.schmittjoaopedro.game.Player;

public class Giant extends Warrior {

    private Player player;

    private double life = 3344.0;

    public Giant(Player player, Arena arena, double x, double y) {
        super("G", arena, x, y);
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
        return DamageType.Building;
    }

    public WarriorType getWarriorType() {
        return WarriorType.Ground;
    }

    public double getRange() {
        return 1.0;
    }

    public double getElixirCost() {
        return 5.0;
    }

    public double getDamage() {
        return 140.0;
    }

    private Warrior findNearestEnemy() {
        Warrior enemy = null;
        double distance = Double.MAX_VALUE;
        for(Warrior warrior : this.arena.getWarriors()) {
            if(WarriorType.Tower.equals(warrior.getWarriorType()) && warrior.getPlayer() != this.getPlayer() && this.calculateDistance(warrior) < distance) {
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
        Giant giant = new Giant(player, arena, this.getX(), this.getY());
        giant.setLife(this.getLife());
        giant.setId(this.getId());
        return giant;
    }
}
