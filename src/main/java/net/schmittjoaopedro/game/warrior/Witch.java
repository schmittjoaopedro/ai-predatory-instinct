package net.schmittjoaopedro.game.warrior;

import net.schmittjoaopedro.game.Arena;
import net.schmittjoaopedro.game.Player;

public class Witch extends Warrior {

    private Player player;

    private double life = 696.0;

    int summonTime = 0;

    public Witch(Player player, Arena arena, double x, double y) {
        super("W", arena, x, y);
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
        return WarriorType.Ground;
    }

    public double getRange() {
        return 3;
    }

    public double getElixirCost() {
        return 5.0;
    }

    public double getDamage() {
        return 98.0;
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
        summonTime++;
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
        if(summonTime % 10 == 0) {
            double xLeft = this.getX() - 1 < 0 ? 0 : this.getX() - 1;
            Skeleton skeleton = new Skeleton(this.getPlayer(), this.arena, xLeft, this.getY());
            arena.getSummons().add(skeleton);
            double xRight = this.getX() + 1 >= arena.getWidth() ? arena.getWidth() - 1 : this.getX() + 1;
            skeleton = new Skeleton(this.getPlayer(), this.arena, xRight, this.getY());
            arena.getSummons().add(skeleton);
        }
    }

    public int getSummonTime() {
        return summonTime;
    }

    public void setSummonTime(int summonTime) {
        this.summonTime = summonTime;
    }

    @Override
    public Warrior clone(Arena arena, Player player) {
        Witch witch = new Witch(player, arena, this.getX(), this.getY());
        witch.setLife(this.getLife());
        witch.setSummonTime(witch.getSummonTime());
        witch.setId(this.getId());
        return witch;
    }
}
