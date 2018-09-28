package net.nanoteck137.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public class Board {

    private static final int BOARD_CELLS = 3;
    private static final int PLAYER_COUNT = 2;

    private Player[] players;
    private int currentPlayer = 0;

    private boolean wasPressed = false;

    private Cell[] cells;

    private boolean reset = false;

    private int playerWon = -1;

    public Board() {
        players = new Player[PLAYER_COUNT];
        players[0] = new Player(0, Color.RED);
        players[1] = new Player(1, Color.BLUE);

        cells = new Cell[BOARD_CELLS * BOARD_CELLS];

        for(int i = 0; i < BOARD_CELLS; i++) {
            for(int j = 0; j < BOARD_CELLS; j++) {
                int index =i + j * BOARD_CELLS;
                Cell cell = new Cell(i + (j * BOARD_CELLS));
                cells[index] = cell;
            }
        }

        wasPressed = true;
    }

    public void update(Camera camera) {
        Vector3 mousePos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        boolean isPressed = Gdx.input.isButtonPressed(0);
        if(isPressed && !wasPressed) {
            Cell cell = cells[(int)Math.floor(mousePos.x / 3) + (int)Math.floor(mousePos.y / 3) * BOARD_CELLS];
            if(!cell.selected) {
                cell.color = players[currentPlayer].getColor();

                cell.selected = true;
                cell.playerIndex = currentPlayer;
            }

            checkBoard();
            currentPlayer++;
            currentPlayer %= 2;
        }

        wasPressed = isPressed;
    }

    public void render(ShapeRenderer renderer) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                Cell cell = cells[i + j * BOARD_CELLS];
                renderer.setColor(cell.color);
                renderer.rect(i * 3 + 0.05f, j * 3 + 0.05f, 2.9f, 2.9f);
            }
        }
    }

    private void checkBoard() {
        for (int i = 0; i < cells.length / BOARD_CELLS; i++) {
            if (cells[i].isValid(currentPlayer) &&
                cells[i + BOARD_CELLS].isValid(currentPlayer) &&
                cells[i + (BOARD_CELLS * 2)].isValid(currentPlayer))
            {
                System.out.println("Horizontal WIN: " + currentPlayer);
                reset = true;
                playerWon = currentPlayer;
                return;
            }

            if (cells[i * 3].isValid(currentPlayer) &&
                cells[i * 3 + 1].isValid(currentPlayer) &&
                cells[i * 3 + 2].isValid(currentPlayer))
            {
                System.out.println("Vertical WIN: " + currentPlayer);
                reset = true;
                playerWon = currentPlayer;
                return;
            }

            if (i == 0) {
                if (cells[i].isValid(currentPlayer) &&
                    cells[i + 4].isValid(currentPlayer) &&
                    cells[i + 4 * 2].isValid(currentPlayer))
                {
                    System.out.println("Diagonal WIN: " + currentPlayer);
                    reset = true;
                    playerWon = currentPlayer;
                    return;
                }
            }

            if (i == 2) {
                if (cells[i].isValid(currentPlayer) &&
                    cells[i + 2].isValid(currentPlayer) &&
                    cells[i + (2 * 2)].isValid(currentPlayer))
                {
                    System.out.println("Diagonal WIN 22222: " + currentPlayer);
                    reset = true;
                    playerWon = currentPlayer;
                    return;
                }
            }
        }

        int numSelected = 0;
        for(int i = 0; i < cells.length; i++) {
            if(cells[i].selected)
                numSelected++;
        }

        if(numSelected == cells.length) {
            reset = true;
            System.out.println("Nobody wins");
            playerWon = -2;
            return;
        }
    }

    public boolean needReset() {
        return reset;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getPlayerWon() {
        return playerWon;
    }

}
