package net.schmittjoaopedro.game;

import net.schmittjoaopedro.game.warrior.Warrior;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private double width;

    private double height;

    private List<Warrior> warriors;

    private List<Warrior> summons;

    private List<Player> players;

    private double elixirAmount = 0.0;

    public Arena(double width, double height) {
        super();
        this.width = width;
        this.height = height;
    }

    public List<Warrior> getWarriors() {
        if(this.warriors == null) this.warriors = new ArrayList<Warrior>();
        return warriors;
    }

    public void setWarriors(List<Warrior> warriors) {
        this.warriors = warriors;
    }

    public List<Player> getPlayers() {
        if(this.players == null) this.players = new ArrayList<Player>();
        return players;
    }

    public List<Warrior> getSummons() {
        if(this.summons == null) this.summons = new ArrayList<Warrior>();
        return summons;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void advanceStep() {
        for(Player player : this.getPlayers()) {
            player.nextStep();
        }
        for(Warrior warrior : this.getWarriors()) {
            warrior.nextStep();
        }
        List<Warrior> bodies = new ArrayList<Warrior>();
        for(Warrior warrior : this.getWarriors()) {
            if(warrior.isDead())
                bodies.add(warrior);
        }
        this.getWarriors().removeAll(bodies);
        this.getWarriors().addAll(this.getSummons());
        System.out.format("Live: %d\tKilled: %d\tSummoned: %d\n", this.getWarriors().size(), bodies.size(), this.getSummons().size());
        this.getSummons().clear();
    }


}
