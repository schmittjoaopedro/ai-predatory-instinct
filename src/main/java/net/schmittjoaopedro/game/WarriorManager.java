package net.schmittjoaopedro.game;

import net.schmittjoaopedro.game.warrior.*;

import java.util.HashMap;
import java.util.Map;

public class WarriorManager {

    private static WarriorManager WARRIOR_MANAGER;

    private static Map<String, Class<? extends Warrior>> CARDS = new HashMap<String, Class<? extends Warrior>>();

    private static Map<String, Double> CARD_ELIXIR = new HashMap<String, Double>();

    private WarriorManager() {
        super();
        WarriorManager.CARDS.put("M", Musketeer.class);
        WarriorManager.CARDS.put("W", Witch.class);
        WarriorManager.CARDS.put("G", Giant.class);
        WarriorManager.CARDS.put("D", Dragon.class);
        WarriorManager.CARD_ELIXIR.put("M", 4.0);
        WarriorManager.CARD_ELIXIR.put("W", 5.0);
        WarriorManager.CARD_ELIXIR.put("G", 5.0);
        WarriorManager.CARD_ELIXIR.put("D", 4.0);
    }

    public static WarriorManager getInstance() {
        if(WarriorManager.WARRIOR_MANAGER == null) {
            WarriorManager.WARRIOR_MANAGER = new WarriorManager();
        }
        return WarriorManager.WARRIOR_MANAGER;
    }

    public Warrior getCard(Player player, Arena arena, String letter, double x, double y) {
        try {
            return WarriorManager.CARDS.get(letter).getDeclaredConstructor(Player.class, Arena.class, double.class, double.class).newInstance(player, arena, x, y);
        } catch (Exception ex) {
            System.exit(1);
        }
        return null;
    }

    public String getRandomLetter() {
        String letters[] = CARDS.keySet().toArray(new String[] {});
        return letters[(int) (Math.random() * letters.length)];
    }

    public boolean warriorAvailable(String letter, double elixir) {
        return CARD_ELIXIR.get(letter) <= elixir;
    }

}
