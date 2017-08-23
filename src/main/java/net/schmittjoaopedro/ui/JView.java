package net.schmittjoaopedro.ui;

import net.schmittjoaopedro.game.Arena;

import javax.swing.*;

public class JView extends JFrame {

    private JArena jArena;

    public JView(int width, int height) {
        jArena = new JArena(width, height);
        this.add(jArena);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void draw(Arena arena) {
        this.jArena.draw(arena);
    }

}
