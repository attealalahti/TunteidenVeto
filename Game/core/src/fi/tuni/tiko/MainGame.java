
package fi.tuni.tiko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

import javax.swing.text.LabelView;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture boxTexture;
	Texture bigBoxTexture;
	Texture feeling;
	public static int windowWidth;
	public static int windowHeight;

	ArrayList<String> answers;
	ArrayList<ChoiceScreen> screens;

	ChoiceScreen screen;
	ChoiceScreen screen2a;
	ChoiceScreen screen2b;
	ChoiceScreen currentScreen;
	int nextScreensID = 0;

	Skin skin;

	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();

		boxTexture = new Texture("boxshadowmdpi.png");
		bigBoxTexture = new Texture("bigboxshadowmdpi.png");
		feeling = new Texture("ilo_reunatmdpi.png");
		img = new Texture("badlogic.jpg");
		skin = createSkin();

		String question1 = "Välitunnilla ystäväsi on vaisu, eikä vaikuta olevan lainkaan kiinnostunut jutuistasi. Hän on selvästi omissa ajatuksissaan. \n Sinä... ";
		answers = new ArrayList<String>();
		answers.add(" ...tunnet itsesi hölmöksi. Oletkohan tehnyt jotakin, mistä hän on suuttunut?");
		answers.add(" ...alat kysellä, mikä vaivaa. Hän ei selvästikään ole oma itsensä.");
		//answers.add("Another option.");
		ArrayList<Integer> screenLinks = new ArrayList<Integer>();
		screenLinks.add(1);
		screenLinks.add(2);
		screen = new ChoiceScreen(0, skin, question1, answers, screenLinks);
		currentScreen = screen;

		String feedback2a = "Tilanne jää mietityttämään. Oliko ystäväsi kuitenkin vihaisen sijaan surullinen? Et oikein tiedä, mitä tilanteesta pitäisi ajatella.";
		ArrayList<String> answers2a = new ArrayList<String>();
		answers2a.add("HÄMMENNYS +10");
		ArrayList<Integer> screenLinks2 = new ArrayList<Integer>();
		screenLinks2.add(3);
		screen2a = new ChoiceScreen(1, skin, feedback2a, answers2a, screenLinks2);

		String feedback2b = "Hetken päästä ystäväsi kertoo, että hänen lemmikkinsä kuoli muutama päivä sitten. Juttelette tilanteesta hetken ja ystäväsi vaikuttaa tulevan paremmille mielin. Itseäsi alkaa hiukan surettaa ystäväsi puolesta, mutta olet iloinen että juttelusta tuli hänelle parempi mieli.";
		ArrayList<String> answers2b = new ArrayList<String>();
		answers2b.add("SURU +5, ILO +15");
		ArrayList<Integer> screenLinks3 = new ArrayList<Integer>();
		screenLinks3.add(3);
		screen2b = new ChoiceScreen(2, skin, feedback2b, answers2b, screenLinks3);


		/*
		screens = createScreens();
		currentScreen = screens.get(0);

		 */


	}
	public ArrayList<ChoiceScreen> createScreens() {
		FileHandle handle = Gdx.files.internal("leveldata.feel");
		String text = handle.readString();
		String [] linesArray = text.split("\\r?\\n");
		int index = 0;
		int screenID = Integer.parseInt(linesArray[index]);
		index++;
		String question = linesArray[index];
		index++;
		int oldIndex = index;
		while(!linesArray[index].equals("")) {
			index++;
		}
		int amountOfAnswers = (index - oldIndex) / 2;
		ArrayList<String> answers = new ArrayList<>();
		for (int i = oldIndex; i < oldIndex + amountOfAnswers; i++) {
			answers.add(linesArray[i]);
		}
		oldIndex += amountOfAnswers;
		ArrayList<Integer> screenLinks = new ArrayList<>();
		for (int i = oldIndex; i <oldIndex + amountOfAnswers ; i++) {
			screenLinks.add(Integer.parseInt(linesArray[i]));
		}
		ChoiceScreen choiceScreen = new ChoiceScreen(screenID, skin, question, answers, screenLinks);
		ArrayList<ChoiceScreen> choiceScreens = new ArrayList<>();
		choiceScreens.add(choiceScreen);



		return choiceScreens;
	}
	public Skin createSkin() {
		Skin s = new Skin();
		Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		s.add("round_corners", boxTexture);
		s.add("big_box", bigBoxTexture);
		s.add("white", new Texture(pixmap));
		s.add("default", new BitmapFont());
		s.add("feeling", feeling);
		s.add("test", img);

		Label.LabelStyle answerStyle = new Label.LabelStyle();
		answerStyle.background = s.newDrawable("round_corners", Color.WHITE);
		answerStyle.font = s.getFont("default");

		Label.LabelStyle questionStyle = new Label.LabelStyle();
		questionStyle.background = s.newDrawable("big_box", Color.WHITE);
		questionStyle.font = s.getFont("default");

		Label.LabelStyle textBoxStyle = new Label.LabelStyle();
		textBoxStyle.background = s.newDrawable("white", Color.CLEAR);
		textBoxStyle.font = s.getFont("default");

		Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
		buttonStyle.up = s.newDrawable("feeling");
		buttonStyle.down = s.newDrawable("feeling", Color.DARK_GRAY);

		Button.ButtonStyle buttonStyleAlt = new Button.ButtonStyle();
		buttonStyleAlt.up = s.newDrawable("test");
		buttonStyleAlt.down = s.newDrawable("test", Color.DARK_GRAY);

		s.add("default", answerStyle);
		s.add("question", questionStyle);
		s.add("default", buttonStyle);
		s.add("alt", buttonStyleAlt);
		s.add("text", textBoxStyle);
		s.add("font", createFont());

		return s;
	}

	/** createFont creates and returns a BitmapFont to be used
	 *
	 * This method uses FreeTypeFont to create a BitmapFont from an existing font file in the project font folder.
	 * @return returns a BitmapFont
	 * @author Mika Kivennenä
	 */
	public BitmapFont createFont() {
		FreeTypeFontGenerator fontGenerator new FreeTypeFontGenerator(Gdx.files.internal(font/"lato.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		fontParameter.size = 100;
		fontParameter.borderColor = Color.BLACK;
		fontParameter.color = Color.WHITE

		BitmapFont font = fontGenerator.generateFont(fontParameter);
		return font;
	}

	/** This method creates an array of screens containing longer story texts.
	 *
	 * This method takes in the story text, divides it into different screens for easier readibility.
	 * @param lengthOfText is the float given to determine the length of the story text.
	 * @param string containing the story text that will be split into separate parts
	 * @return an array of strings
	 * @author Mika Kivennenä
	 */
	public String[] createStoryScreens(float lengthOfMaxPerScreen, String storyText) {
		int amountOfScreens = (int)(storyText.length() / lengthOfMaxPerScreen);
		int characterIndex = 0;
		int screenIndex = 0;
		String[] stringArr = new String[amountOfScreens];

		// This loop runs until the current character is the last character of the text.
		while(characterIndex < storyText.length()) {
			String tempString;
			// Saves the story text one character at a time into a temporary string that is later added to the story array.
			for(int i = 0; i<lengthOfMaxPerScreen; i++) {
				tempString += storyText.charAt(characterIndex);
				characterIndex++;
			}
			// Adds the tempstring into the string array of the current screen index. Increases screen index by one.
			stringArr[screenIndex] = tempString;
			screenIndex++;
		}
	}

	@Override
	public void render () {
		float fraction = 1f / 255f;
		Gdx.gl.glClearColor(fraction * 81f, fraction * 99f, fraction * 115f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		nextScreensID = currentScreen.getNextScreensID();

		if(nextScreensID == 1) {
			currentScreen = screen2a;
		}
		else if(nextScreensID == 2) {
			currentScreen = screen2b;
		}



		Gdx.input.setInputProcessor(currentScreen);
		currentScreen.draw();
		currentScreen.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
	}

	@Override
	public void dispose () {
		currentScreen.dispose();
		batch.dispose();
		img.dispose();
		boxTexture.dispose();
		feeling.dispose();
	}
}