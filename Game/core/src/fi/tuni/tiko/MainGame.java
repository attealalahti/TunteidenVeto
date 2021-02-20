
package fi.tuni.tiko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	public static int windowWidth;
	public static int windowHeight;

	Stage stage;
	AnswerBox answerBox;
	Skin skin;
	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();

		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();
		float boxWidth = windowWidth * 0.9f;
		float boxHeight = windowHeight * 0.1f;
		float boxMargin = windowHeight * 0.05f;
		float xBox = (windowWidth - boxWidth) / 2f;

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin();

		Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		skin.add("default", new BitmapFont());

		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.background = skin.newDrawable("white", Color.GREEN);
		labelStyle.font = skin.getFont("default");

		skin.add("default", labelStyle);

		answerBox = new AnswerBox("Test text.", skin, xBox, boxMargin, boxWidth, boxHeight);
		stage.addActor(answerBox);
	}
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		stage.draw();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}