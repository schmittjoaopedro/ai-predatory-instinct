package net.schmittjoaopedro.game;

import java.awt.*;

public class Player {

    private double elixirAmount = 0.0;

    private double UH;

    private double LH;

    private double UW;

    private double LW;

    private Color color;

    public Player(double LH, double LW, double UH, double UW, Color color) {
        super();
        this.UH = UH;
        this.LH = LH;
        this.UW = UW;
        this.LW = LW;
        this.color = color;
    }

    public double getElixirAmount() {
        return elixirAmount;
    }

    public void setElixirAmount(double elixirAmount) {
        this.elixirAmount = elixirAmount;
    }

    public double getUH() {
        return UH;
    }

    public double getLH() {
        return LH;
    }

    public double getUW() {
        return UW;
    }

    public double getLW() {
        return LW;
    }

    public Color getColor() {
        return color;
    }
}
