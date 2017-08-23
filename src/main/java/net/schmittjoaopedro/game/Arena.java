package net.schmittjoaopedro.game;

import net.schmittjoaopedro.game.warrior.Warrior;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Arena {

    private double width;

    private double height;

    private List<Warrior> warriors;

    private List<Warrior> summons;

    private List<Player> players;

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
        advanceStep(false);
    }

    public void advanceStep(boolean log) {
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
        if(log) {
            int p1 = 0;
            int p2 = 0;
            for(Warrior w : this.getWarriors()) {
                if(Color.RED.equals(w.getPlayer().getColor())) p1++;
                else p2++;
            }
            System.out.format("Red: %d\tBlue: %d\n", p1, p2);
        }
        this.getSummons().clear();
    }

    public Player getPlayerByColor(Color color) {
        for(Player player : this.getPlayers()) {
            if(player.getColor().equals(color)) {
                return player;
            }
        }
        return null;
    }

    public Arena clone(boolean deep) {
        Arena arena = new Arena(this.getWidth(), this.getHeight());
        if(deep) {
            for(Player player : this.getPlayers()) {
                arena.getPlayers().add(player.clone(arena, deep));
            }
            for(Warrior warrior : this.getWarriors()) {
                if(warrior.getTarget() != null && !warrior.getTarget().isDead()) {
                    Warrior attacker = arena.getWarriorById(warrior.getId());
                    attacker.setTarget(arena.getWarriorById(warrior.getTarget().getId()));
                }
            }
        }
        return arena;
    }

    private Warrior getWarriorById(Long id) {
        for(Warrior warrior : this.getWarriors()) {
            if(warrior.getId().equals(id)) return warrior;
        }
        return null;
    }

}
