package net.nanoteck137.game;

import com.badlogic.gdx.graphics.Color;

public class Player {
    private int index;
    private Color color;

    public Player(int index, Color color) {
        this.index = index;
        this.color = color;
    }

    public int getIndex() {
        return index;
    }

    public Color getColor() {
        return color;
    }
}
