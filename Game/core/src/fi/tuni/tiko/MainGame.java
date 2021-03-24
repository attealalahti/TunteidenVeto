
package fi.tuni.tiko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;


public class MainGame extends ApplicationAdapter {
	private Texture img;
	private Texture boxTexture;
	private Texture bigBoxTexture;
	private Texture happy;
	private Texture settingsTexture;
	private Texture empty;
	private Texture feelingMeterTexture;
	private Texture musicOnTexture;
	private Texture musicOffTexture;
	private Texture soundOnTexture;
	private Texture soundOffTexture;

	private ArrayList<ChoiceScreen> screens;
	private Screen currentScreen;
	private Group meters;
	private Button feelingMeterButton;
	private Group settings;
	private Button settingsButton;
	private Screen lastFrameCurrentScreen;
	private FeelingMeter happiness;
	private FeelingMeter sadness;
	private FeelingMeter anger;
	private FeelingMeter love;
	private FeelingMeter fear;
	private FeelingMeter astonishment;
	private FeelingMeter disgust;

	public static int currentScreenID = 0;
	public static int windowWidth;
	public static int windowHeight;
	public static Skin skin;
	public static boolean musicOn = true;
	public static boolean soundOn = true;
	public static float margin;
	public static float meterHeight;

	private float buttonHeight;
	private float bigButtonHeight;
	// How long it takes to switch between Game and FeelingMeter mode:
	private float FADE_TIME = 0.2f;


	private String folderToUse = "";
	private int fontSize;

	@Override
	public void create () {
		folderToUse = getPixelDensity();
		fontSize = getFontSize();

		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();
		buttonHeight =  windowHeight * 0.07f;
		bigButtonHeight = buttonHeight * 2f;
		margin = windowHeight * 0.025f;
		meterHeight = windowHeight * 0.1f;

		String endPart = folderToUse + "/";

		boxTexture = new Texture(folderToUse+"box"+endPart+".png");
		bigBoxTexture = new Texture(folderToUse+"textbox.png");
		happy = new Texture("ilo_reunatmdpi.png");
		settingsTexture = new Texture(folderToUse+"hamburgermenu.png");
		empty = new Texture(folderToUse+"button.png");
		feelingMeterTexture = new Texture("mittari2.png");
		musicOnTexture = new Texture(folderToUse+"music_on.png");
		musicOffTexture = new Texture(folderToUse+"music_off.png");
		soundOnTexture = new Texture(folderToUse+"sound_on.png");
		soundOffTexture = new Texture(folderToUse+"sound_off.png");
		img = new Texture("badlogic.jpg");
		skin = createSkin();

		settingsButton = createSettingsButton();
		feelingMeterButton = createFeelingMeterButton();
		settings = createSettings();
		meters = createMeters();


		screens = createChoiceScreens();
		currentScreen = screens.get(0);
	}
	@Override
	public void render () {
		float fraction = 1f / 255f;
		Gdx.gl.glClearColor(fraction * 0f, fraction * 151, fraction * 167f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		for (Screen screen: screens) {
			if (screen.getScreenID() == currentScreenID) {
				currentScreen = screen;
			}
		}

		if (currentScreen.getClass() == ChoiceScreen.class && currentScreen != lastFrameCurrentScreen) {
			((ChoiceScreen) currentScreen).addGlobalElements(feelingMeterButton, meters, settingsButton, settings);
		}
		lastFrameCurrentScreen = currentScreen;

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
		s.add("happy", happy);
		s.add("settings", settingsTexture);
		s.add("empty", empty);
		s.add("meter", feelingMeterTexture);
		s.add("soundOn", soundOnTexture);
		s.add("soundOff", soundOffTexture);
		s.add("musicOn", musicOnTexture);
		s.add("musicOff", musicOffTexture);
		s.add("test", img);
		s.add("font", createFont());

		Label.LabelStyle answerStyle = new Label.LabelStyle();
		answerStyle.background = s.newDrawable("round_corners", Color.WHITE);
		answerStyle.font = s.getFont("default");

		Label.LabelStyle questionStyle = new Label.LabelStyle();
		questionStyle.background = s.newDrawable("big_box", Color.WHITE);
		questionStyle.font = s.getFont("font");

		Label.LabelStyle textBoxStyle = new Label.LabelStyle();
		textBoxStyle.background = s.newDrawable("white", Color.CLEAR);
		textBoxStyle.font = s.getFont("font");

		Button.ButtonStyle feelingsButtonStyle = new Button.ButtonStyle();
		feelingsButtonStyle.up = s.newDrawable("empty");
		feelingsButtonStyle.down = s.newDrawable("empty", Color.DARK_GRAY);

		Button.ButtonStyle happyStyle = new Button.ButtonStyle();
		happyStyle.up = s.newDrawable("empty");
		happyStyle.down = s.newDrawable("empty", Color.DARK_GRAY);
		happyStyle.checked = s.newDrawable("empty", Color.DARK_GRAY);

		Button.ButtonStyle settingsButtonStyle = new Button.ButtonStyle();
		settingsButtonStyle.up = s.newDrawable("settings");
		settingsButtonStyle.down = s.newDrawable("settings", Color.DARK_GRAY);
		settingsButtonStyle.checked = s.newDrawable("settings", Color.DARK_GRAY);

		Button.ButtonStyle soundStyle = new Button.ButtonStyle();
		soundStyle.up = s.newDrawable("soundOff");
		soundStyle.down = s.newDrawable("soundOff", Color.DARK_GRAY);
		soundStyle.checked = s.newDrawable("soundOn");
		soundStyle.checkedDown = s.newDrawable("soundOn", Color.DARK_GRAY);

		Button.ButtonStyle musicStyle = new Button.ButtonStyle();
		musicStyle.up = s.newDrawable("musicOff");
		musicStyle.down = s.newDrawable("musicOff", Color.DARK_GRAY);
		musicStyle.checked = s.newDrawable("musicOn");
		musicStyle.checkedDown = s.newDrawable("musicOn", Color.DARK_GRAY);

		Button.ButtonStyle exitStyle = new Button.ButtonStyle();
		exitStyle.up = s.newDrawable("empty");
		exitStyle.down = s.newDrawable("empty", Color.DARK_GRAY);

		Button.ButtonStyle buttonStyleAlt = new Button.ButtonStyle();
		buttonStyleAlt.up = s.newDrawable("test");
		buttonStyleAlt.down = s.newDrawable("test", Color.DARK_GRAY);

		Label.LabelStyle feelingMeterForeGroundStyle = new Label.LabelStyle();
		feelingMeterForeGroundStyle.font = s.getFont("default");
		feelingMeterForeGroundStyle.background = s.newDrawable("meter");



		s.add("default", answerStyle);
		s.add("question", questionStyle);
		s.add("feelings", feelingsButtonStyle);
		s.add("happiness", happyStyle);
		s.add("alt", buttonStyleAlt);
		s.add("text", textBoxStyle);
		s.add("settings", settingsButtonStyle);
		s.add("sound", soundStyle);
		s.add("music", musicStyle);
		s.add("exit", exitStyle);
		s.add("feelingMeterForeground", feelingMeterForeGroundStyle);


		return s;
	}

	/** createFont creates and returns a BitmapFont to be used
	 *
	 * This method uses FreeTypeFont to create a BitmapFont from an existing font file in the project font folder.
	 * @return returns a BitmapFont
	 * @author Mika Kivennenä
	 */
	public BitmapFont createFont() {
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/lato.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();


		fontParameter.size = fontSize;
		fontParameter.borderColor = Color.BLACK;
		fontParameter.color = Color.WHITE;

		BitmapFont font = fontGenerator.generateFont(fontParameter);
		return font;
	}

	public Button createFeelingMeterButton() {
		final Button button = new Button(skin, "happiness");
		button.setBounds(((float) windowWidth / 3f) - buttonHeight * 0.5f, margin, buttonHeight, buttonHeight);
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				ChoiceScreen thisScreen = (ChoiceScreen) feelingMeterButton.getStage();
				if (!feelingMeterButton.isChecked()) {
					meters.addAction(Actions.fadeOut(FADE_TIME));
					meters.toBack();
					if (!settingsButton.isChecked()) {
						thisScreen.getGameElements().addAction(Actions.fadeIn(FADE_TIME));
						thisScreen.answersSetPause(false);
						thisScreen.getGameElements().toFront();
					}
				} else {
					meters.addAction(Actions.fadeIn(FADE_TIME));
					meters.toFront();
					thisScreen.getGameElements().addAction(Actions.fadeOut(FADE_TIME));
					thisScreen.answersSetPause(true);
					thisScreen.getGameElements().toBack();
					if (settingsButton.isChecked()) {
						settingsButton.setChecked(false);
					}
				}
			}
		});
		return button;
	}
	public Group createMeters() {
		Group result = new Group();
		// Create FeelingMeters
		float meterMargin = margin + meterHeight;
		float meterLocationHeight = meterMargin * 7;
		float currentY = windowHeight - meterLocationHeight;
		happiness = new FeelingMeter(currentY, Color.YELLOW);
		result.addActor(happiness);
		currentY += meterMargin;
		sadness = new FeelingMeter(currentY, Color.BLUE);
		result.addActor(sadness);
		currentY += meterMargin;
		love = new FeelingMeter(currentY, Color.PINK);
		result.addActor(love);
		currentY += meterMargin;
		anger = new FeelingMeter(currentY, Color.RED);
		result.addActor(anger);
		currentY += meterMargin;
		fear = new FeelingMeter(currentY, Color.PURPLE);
		result.addActor(fear);
		currentY += meterMargin;
		astonishment = new FeelingMeter(currentY, Color.ORANGE);
		result.addActor(astonishment);
		currentY += meterMargin;
		disgust = new FeelingMeter(currentY, Color.OLIVE);
		result.addActor(disgust);

		// Hide the meters initially
		result.toBack();
		result.addAction(Actions.fadeOut(0));

		return result;
	}
	public Button createSettingsButton() {
		final Button button = new Button(skin, "settings");
		button.setBounds(((float) windowWidth / 3f) * 2f - buttonHeight * 0.5f, margin, buttonHeight, buttonHeight);
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				ChoiceScreen thisScreen = (ChoiceScreen) settingsButton.getStage();
				if (!settingsButton.isChecked()) {
					settings.addAction(Actions.fadeOut(FADE_TIME));
					settings.toBack();
					if (!feelingMeterButton.isChecked()) {
						thisScreen.getGameElements().addAction(Actions.fadeIn(FADE_TIME));
						thisScreen.answersSetPause(false);
						thisScreen.getGameElements().toFront();
					}
				} else {
					settings.addAction(Actions.fadeIn(FADE_TIME));
					settings.toFront();
					thisScreen.getGameElements().addAction(Actions.fadeOut(FADE_TIME));
					thisScreen.answersSetPause(true);
					thisScreen.getGameElements().toBack();
					if (feelingMeterButton.isChecked()) {
						feelingMeterButton.setChecked(false);
					}
				}
			}
		});
		return button;
	}
	public Group createSettings() {
		Group result = new Group();
		// Create buttons for settings
		final Button musicButton = new Button(skin, "music");
		final Button soundButton = new Button(skin, "sound");
		final Button exitButton = new Button(skin, "exit");
		float centerX = windowWidth * 0.5f - bigButtonHeight * 0.5f;
		float centerY = windowHeight * 0.5f - bigButtonHeight * 0.5f;
		musicButton.setBounds(centerX, centerY + bigButtonHeight + margin, bigButtonHeight, bigButtonHeight);
		soundButton.setBounds(centerX, centerY, bigButtonHeight, bigButtonHeight);
		exitButton.setBounds(centerX, centerY - bigButtonHeight - margin, bigButtonHeight, bigButtonHeight);
		result.addActor(musicButton);
		result.addActor(soundButton);
		result.addActor(exitButton);
		// Hide the buttons initially
		result.toBack();
		result.addAction(Actions.fadeOut(0));
		musicButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				musicOn = musicButton.isChecked();
			}
		});
		soundButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				soundOn = soundButton.isChecked();
			}
		});
		exitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				screens = createChoiceScreens();
				currentScreenID = 0;
				settingsButton.setChecked(false);
			}
		});
		musicButton.setChecked(musicOn);
		soundButton.setChecked(soundOn);

		return result;
	}
	@Override
	public void dispose () {
		img.dispose();
		boxTexture.dispose();
		bigBoxTexture.dispose();
		happy.dispose();
		settingsTexture.dispose();
		empty.dispose();
	}

	/** getPixelDensity method is used to show correct images
	 *
	 * This method uses getDensity() method to determine screen pixel density
	 * and sets the correct folder to which the images in the project are being used from.
	 *
	 * @return returns a string that is used to choose right folder for images
	 * @author Mika Kivennenä
	 */
	public String getPixelDensity() {
		float density = Gdx.graphics.getDensity();
		String tempString = "";

		if(density < 1) {
			tempString = "ldpi";
		}
		else if(density >= 1f && density < 2f) {
			tempString = "mdpi";
		}
		else if(density >= 2f && density < 3f) {
			tempString = "hdpi";
		}
		else if(density >= 3f && density < 4f) {
			tempString = "xhdpi";
		}

		return tempString;
	}

	/** getFontSize method is used to set size for the font
	 *
	 * This method uses getDensity() method to determine screen pixel density
	 * and calculates the correct font size based on pixel density
	 *
	 * @return returns an integer that is used to set fontSize
	 * @author Mika Kivennenä
	 */
	public int getFontSize() {
		float density = Gdx.graphics.getDensity();
		int tempInt = 0;

		if(density < 1) {
			tempInt = 150;
		} else {
			tempInt = 75 * (int)density;
		}

		return tempInt;
	}
}