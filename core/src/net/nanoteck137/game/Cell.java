package net.nanoteck137.game;

import com.badlogic.gdx.graphics.Color;

public class Cell {
    private int index;

    public Color color;

    public int playerIndex;
    public boolean selected;


    public Cell(int index) {
        this.index = index;
        this.color = Color.WHITE;

        this.playerIndex = -1;
        this.selected = false;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return Integer.toString(index);
    }

    public boolean isValid(int playerIndex) {
        return selected && playerIndex == this.playerIndex;
    }
}
