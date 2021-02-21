
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

import javax.swing.text.LabelView;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture boxTexture;
	public static int windowWidth;
	public static int windowHeight;

	ArrayList<String> answers;

	ChoiceScreen screen;
	ChoiceScreen screen2a;
	ChoiceScreen screen2b;

	ChoiceScreen currentScreen;

	Skin skin;
	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();

		boxTexture = new Texture("Box.png");
		skin = createSkin();

		String question = "Välitunnilla ystäväsi on vaisu, \n eikä vaikuta olevan lainkaan kiinnostunut jutuistasi. \n Hän on selvästi omissa ajatuksissaan. \n Sinä... ";
		answers = new ArrayList<String>();
		answers.add(" ...tunnet itsesi hölmöksi. \n Oletkohan tehnyt jotakin, \n mistä hän on suuttunut?");
		answers.add(" ...alat kysellä, mikä vaivaa. \n Hän ei selvästikään ole oma itsensä.");
		ArrayList<Integer> screenLinks = new ArrayList<Integer>();
		screenLinks.add(1);
		screenLinks.add(2);
		screen = new ChoiceScreen(0, skin, question, answers, screenLinks);

		String feedback2a = "Tilanne jää mietityttämään. \n Oliko ystäväsi kuitenkin \n vihaisen sijaan surullinen? \n Et oikein tiedä, \n mitä tilanteesta pitäisi ajatella.";
		ArrayList<String> answers2a = new ArrayList<String>();
		answers2a.add("HÄMMENNYS +10");
		ArrayList<Integer> screenLinks2 = new ArrayList<Integer>();
		screenLinks2.add(0);
		screen2a = new ChoiceScreen(1, skin, feedback2a, answers2a, screenLinks2);

		String feedback2b = "Hetken päästä ystäväsi kertoo,\n että hänen lemmikkinsä kuoli \n muutama päivä sitten. \n Juttelette tilanteesta hetken ja \n ystäväsi vaikuttaa tulevan paremmille mielin. \n Itseäsi alkaa hiukan surettaa ystäväsi puolesta, \n mutta olet iloinen että juttelusta \n tuli hänelle parempi mieli.";
		ArrayList<String> answers2b = new ArrayList<String>();
		answers2b.add("SURU +5, ILO +15");
		answers2b.add("another option");
		ArrayList<Integer> screenLinks3 = new ArrayList<Integer>();
		screenLinks3.add(0);
		screenLinks3.add(1);
		screen2b = new ChoiceScreen(2, skin, feedback2b, answers2b, screenLinks3);

		currentScreen = screen2b;
		Gdx.input.setInputProcessor(currentScreen);
	}
	public Skin createSkin() {
		Skin s = new Skin();
		Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		s.add("round_corners", boxTexture);
		s.add("white", new Texture(pixmap));
		s.add("default", new BitmapFont());

		Label.LabelStyle answerStyle = new Label.LabelStyle();
		answerStyle.background = s.newDrawable("round_corners", Color.FOREST);
		answerStyle.font = s.getFont("default");
		Label.LabelStyle questionStyle = new Label.LabelStyle();
		questionStyle.background = s.newDrawable("white", Color.ORANGE);
		questionStyle.font = s.getFont("default");

		s.add("default", answerStyle);
		s.add("question", questionStyle);

		return s;
	}
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		currentScreen.draw();
		currentScreen.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
	}

	@Override
	public void dispose () {
		screen.dispose();
		batch.dispose();
		img.dispose();
		boxTexture.dispose();
	}
}