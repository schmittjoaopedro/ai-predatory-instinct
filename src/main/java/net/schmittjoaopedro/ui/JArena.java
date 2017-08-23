package net.schmittjoaopedro.ui;

import net.schmittjoaopedro.game.Arena;
import net.schmittjoaopedro.game.Player;
import net.schmittjoaopedro.game.warrior.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class JArena extends JPanel {

    private Arena arena;

    private int width;

    private int height;

    private double scaleW;

    private double scaleH;

    private String imagePath = "/home/joao/projects/ai-predatory-instinct/imgs/";

    public JArena(int width, int height) {
        setBackground(Color.WHITE);
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(++width, ++height));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    public void draw(Arena arena) {
        this.arena = arena;
        this.scaleW = this.width / arena.getWidth();
        this.scaleH = this.height / arena.getHeight();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.drawRect(0, 0, this.width, this.height);
        for (int i = 0; i < arena.getHeight(); i++) {
            graphics.drawLine(0, (int) (i * scaleH), this.width, (int) (i * scaleH));
        }
        for (int i = 0; i < arena.getWidth(); i++) {
            graphics.drawLine((int) (i * scaleW), 0, (int) (i * scaleW), this.height);
        }
        graphics.setColor(Color.GREEN);
        for (Player player : this.arena.getPlayers()) {
            int x = (int) (player.getLW() * this.scaleW);
            int y = (int) (player.getLH() * this.scaleH);
            int width = (int) (player.getUW() * this.scaleW);
            int height = (int) (player.getUH() * this.scaleH);
            graphics.drawRect(x, y, width, height);
        }
        /*
        for(Warrior warrior : this.arena.getWarriors()) {
            graphics.setColor(warrior.getPlayer().getColor());
            String text = warrior.getLetter() + "(" + ((int) warrior.getLife()) + ")";
            FontMetrics fm = ((Graphics2D) graphics.create()).getFontMetrics();
            int x = (int) (warrior.getX() * this.scaleW) - (fm.stringWidth(text) / 2) + ((int) this.scaleW / 2);
            int y = (int) (warrior.getY() * this.scaleH) + (fm.getHeight() / 2) + ((int) this.scaleH / 2);
            graphics.drawString(text, x, y);
        }
        */
        try {
            for (Warrior warrior : this.arena.getWarriors()) {
                String imageName = null;
                if (warrior.getClass().equals(Dragon.class)) {
                    imageName = "baby-dragon.png";
                }
                if (warrior.getClass().equals(Giant.class)) {
                    imageName = "giant.png";
                }
                if (warrior.getClass().equals(Witch.class)) {
                    imageName = "witch.png";
                }
                if (warrior.getClass().equals(Skeleton.class)) {
                    imageName = "skeleton.png";
                }
                if (warrior.getClass().equals(Musketeer.class)) {
                    imageName = "musketeer.png";
                }
                if (warrior.getClass().equals(Tower.class)) {
                    imageName = "king_tower.png";
                }
                if(imageName != null) {
                    int x = (int) ((warrior.getX() * this.scaleW) + (this.scaleW / 2) - 15);
                    int y = (int) ((warrior.getY() * this.scaleH) + (this.scaleH / 2) - 15);
                    final BufferedImage image = ImageIO.read(new File(imagePath + imageName));
                    graphics.setColor(warrior.getPlayer().getColor());
                    graphics.fillRect(x, y, 30, 30);
                    graphics.drawImage(image, x + 2, y + 2, 26, 26, null);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
