
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

import java.util.ArrayList;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture boxTexture;
	public static int windowWidth;
	public static int windowHeight;

	ChoiceScreen screen;
	Skin skin;
	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();

		boxTexture = new Texture("Box.png");
		skin = createSkin();

		String q = "What are you going to do?";
		ArrayList<String> answers = new ArrayList<String>();
		answers.add("Who knows...");
		answers.add("I don't care.");
		answers.add("Run towards the nearest wall.");

		screen = new ChoiceScreen(skin, q, answers);
		Gdx.input.setInputProcessor(screen);

	}
	public Skin createSkin() {
		Skin s = new Skin();
		Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		s.add("white", boxTexture);
		s.add("default", new BitmapFont());

		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.background = s.newDrawable("white", Color.FOREST);
		labelStyle.font = s.getFont("default");

		s.add("default", labelStyle);

		return s;
	}
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		screen.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		screen.draw();
	}

	@Override
	public void dispose () {
		screen.dispose();
		batch.dispose();
		img.dispose();
		boxTexture.dispose();
	}
}