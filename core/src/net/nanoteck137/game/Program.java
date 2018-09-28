package net.nanoteck137.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Program extends ApplicationAdapter {

    private static GlyphLayout glyphLayout = new GlyphLayout();

    private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private OrthographicCamera camera;

	private Board board;
	private BitmapFont font;

	private boolean gameOver = false;
	private int playerWon = -1;

	private boolean wasPressed = true;

	@Override
	public void create () {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        camera = new OrthographicCamera(9, 9 * (h / w));

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        board = new Board();
        font = new BitmapFont(Gdx.files.internal("test.fnt"), Gdx.files.internal("test.png"), false);
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.3f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(board.needReset()) {
            gameOver = true;
            playerWon = board.getPlayerWon();
        }

        if(!gameOver)
		    board.update(camera);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(camera.combined);

        if(!gameOver)
            board.render(shapeRenderer);

        shapeRenderer.end();

        batch.begin();

        if(gameOver) {
            showGameOver();
        } else {
            showCurrentPlayer();
        }



        batch.end();
	}

	private void showGameOver() {
        font.setColor(Color.PINK);
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getWidth();
        String str = "Game Over -  ";

        if(playerWon == 0)
            str += "Player: 1 Won";
        else if(playerWon == 1)
            str += "Player: 2 Won";
        else if(playerWon == -2)
            str += "Draw";

        glyphLayout.setText(font, str);

        font.getData().setScale(0.6f);
        font.draw(batch, str, width / 2 - (glyphLayout.width / 2), height / 2);

        boolean pressed = Gdx.input.isButtonPressed(0);
        if(pressed && !wasPressed) {
            gameOver = false;
            playerWon = -1;
            board = new Board();
        }
        wasPressed = pressed;
    }

	private void showCurrentPlayer() {
	    if(board != null) {
            int currentPlayer = board.getCurrentPlayer();

            font.setColor(Color.PINK);
            int width = Gdx.graphics.getWidth();
            int height = Gdx.graphics.getWidth();
            String str = "";

            if(currentPlayer == 0)
                str = "Player - 1";
            else if(currentPlayer == 1)
                str = "Player - 2";

            glyphLayout.setText(font, str);

            font.getData().setScale(0.6f);
            font.draw(batch, str, width / 2 - (glyphLayout.width / 2), height - (glyphLayout.height / 2) - 4);
        }
    }
	
	@Override
	public void dispose () {
	    shapeRenderer.dispose();
	}
}
