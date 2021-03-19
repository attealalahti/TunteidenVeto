
package fi.tuni.tiko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.util.ArrayList;


public class MainGame extends ApplicationAdapter {
	private Texture img;
	private Texture boxTexture;
	private Texture bigBoxTexture;
	private Texture happy;
	private Texture settings;
	private ArrayList<ChoiceScreen> screens;
	private Screen currentScreen;

	public static int currentScreenID = 0;
	public static int windowWidth;
	public static int windowHeight;
	public static Skin skin;

	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();

		boxTexture = new Texture("boxshadowmdpi.png");
		bigBoxTexture = new Texture("bigboxshadowmdpi.png");
		happy = new Texture("ilo_reunatmdpi.png");
		settings = new Texture("hamburger.png");
		img = new Texture("badlogic.jpg");
		skin = createSkin();

		screens = createChoiceScreens();
		currentScreen = screens.get(0);
	}
	@Override
	public void render () {
		float fraction = 1f / 255f;
		Gdx.gl.glClearColor(fraction * 81f, fraction * 99f, fraction * 115f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		for (Screen screen: screens) {
			if (screen.getScreenID() == currentScreenID) {
				currentScreen = screen;
			}
		}

		Gdx.input.setInputProcessor(currentScreen);
		currentScreen.draw();
		currentScreen.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
	}
	public ArrayList<ChoiceScreen> createChoiceScreens() {
		FileHandle handle = Gdx.files.internal("leveldata.feel");
		String text = handle.readString();
		String [] allLines = text.split("\\r?\\n");

		ArrayList<ChoiceScreen> choiceScreens = new ArrayList<>();

		for (int i = 0; i < allLines.length; i++) {
			int startingIndex = i;
			while (!allLines[i].equals("---")) {
				i++;
			}
			int amountOfAnswers = i - startingIndex - 1;

			int screenID = getStartOfLineNumber(allLines[startingIndex]);
			String question = getEndOfLineText(allLines[startingIndex]);

			ArrayList<Integer> screenLinks = new ArrayList<>();
			ArrayList<String> answers = new ArrayList<>();
			for (int j = startingIndex+1; j <startingIndex+1+amountOfAnswers ; j++) {
				screenLinks.add(getStartOfLineNumber(allLines[j]));
				answers.add(getEndOfLineText(allLines[j]));
			}
			choiceScreens.add(new ChoiceScreen(screenID, question, answers, screenLinks));
		}

		return choiceScreens;
	}
	public int getStartOfLineNumber(String line) {
		StringBuilder number = new StringBuilder();
		int index = 0;
		while (line.charAt(index) != ' ') {
			number.append(line.charAt(index));
			index++;
		}
		return Integer.parseInt(number.toString());
	}
	public String getEndOfLineText(String line) {
		int index = 0;
		while (line.charAt(index) != ' ') {
			index++;
		}
		index++;
		StringBuilder text = new StringBuilder();
		for (int i = index; i < line.length(); i++) {
			text.append(line.charAt(i));
		}
		return text.toString();
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
		s.add("feeling", happy);
		s.add("settings", settings);
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

		Button.ButtonStyle feelingsButtonStyle = new Button.ButtonStyle();
		feelingsButtonStyle.up = s.newDrawable("feeling");
		feelingsButtonStyle.down = s.newDrawable("feeling", Color.DARK_GRAY);

		Button.ButtonStyle settingsButtonStyle = new Button.ButtonStyle();
		settingsButtonStyle.up = s.newDrawable("settings");
		settingsButtonStyle.down = s.newDrawable("settings", Color.DARK_GRAY);

		Button.ButtonStyle buttonStyleAlt = new Button.ButtonStyle();
		buttonStyleAlt.up = s.newDrawable("test");
		buttonStyleAlt.down = s.newDrawable("test", Color.DARK_GRAY);

		s.add("default", answerStyle);
		s.add("question", questionStyle);
		s.add("feelings", feelingsButtonStyle);
		s.add("alt", buttonStyleAlt);
		s.add("text", textBoxStyle);
		s.add("settings", settingsButtonStyle);

		return s;
	}

	@Override
	public void dispose () {
		img.dispose();
		boxTexture.dispose();
		happy.dispose();
		settings.dispose();
	}
}