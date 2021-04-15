
package fi.tuni.tiko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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

	private ArrayList<Screen> screens;
	private Screen currentScreen;
	private MainMenuScreen mainMenu;
	private Group meters;
	private Button feelingMeterButton;
	private Group settings;
	private Button settingsButton;
	private Button musicButton;
	private Button soundButton;
	private Button exitButton;
	private Screen lastFrameCurrentScreen;
	private FeelingMeter happiness;
	private FeelingMeter sadness;
	private FeelingMeter anger;
	private FeelingMeter love;
	private FeelingMeter fear;
	private FeelingMeter astonishment;
	private FeelingMeter disgust;
	private Color loveColor = colorMax255(234, 140, 128);
	private Color happinessColor = colorMax255(249, 212, 7);
	private Color angerColor = colorMax255(143, 12, 0);
	private Color astonishmentColor = colorMax255(64, 165, 193);
	private Color sadnessColor =colorMax255( 0, 59, 143);
	private Color disgustColor = colorMax255(60, 143, 0);
	private Color fearColor = colorMax255(51, 51, 51);
	private Color lightBackgroundColor = colorMax255(0f, 151, 167f);
	private Color darkBackgroundColor = colorMax255(0, 131, 143);
	private Color desiredBackgroundColor = lightBackgroundColor;
	private Color currentBackgroundColor = desiredBackgroundColor;
	private String [] effectIndicators = {"ILO", "SURU", "VIHA", "RAKKAUS", "PELKO", "HÄMMENNYS", "INHO"};
	private String weekDay = "Maanantai";



	public static int currentScreenID;
	public static int mainMenuChecker = 0;
	public static int windowWidth;
	public static int windowHeight;
	public static MySkin skin;
	public static boolean musicOn;
	public static boolean soundOn;
	public static float margin;
	public static float meterHeight;

	private float buttonHeight;
	private float bigButtonHeight;
	// How long it takes to switch between Game and FeelingMeter mode:
	private float FADE_TIME = 0.2f;

	AudioPlayer audioPlayer;
	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();
		buttonHeight =  windowHeight * 0.07f;
		bigButtonHeight = buttonHeight * 2f;
		margin = windowHeight * 0.025f;
		meterHeight = windowHeight * 0.1f;
		audioPlayer = new AudioPlayer();

		skin = new MySkin();


		settingsButton = createSettingsButton();
		feelingMeterButton = createFeelingMeterButton();
		meters = createMeters();
		screens = createScreens();
		settings = createSettings();

		loadProgress();

		mainMenu = createMainMenu();
		screens.add(mainMenu);
		currentScreen = mainMenu;
		currentScreenID = 999;

		//currentScreen = screens.get(currentScreenID);
		//lastFrameCurrentScreen = currentScreen;
		//((ChoiceScreen) currentScreen).addGlobalElements(feelingMeterButton, meters, settingsButton, settings);
		//audioPlayer.playMenuMusic();
	}

	public static Color colorMax255(float r, float g, float b) {
		float colorFraction = 1f / 255f;
		return new Color(r*colorFraction, g*colorFraction, b*colorFraction, 1);
	}

	public MainMenuScreen createMainMenu() {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("POISTU PELISTÄ");
		choices.add("ASETUKSET");
		choices.add("JATKA PELIÄ");
		choices.add("UUSI PELI");
		ArrayList<Integer> menuScreenLinks = new ArrayList<Integer>();
		menuScreenLinks.add(4);
		menuScreenLinks.add(3);
		menuScreenLinks.add(2);
		menuScreenLinks.add(1);
		return new MainMenuScreen(999, choices, menuScreenLinks);
	}
	public void checkMenuChoice() {
		if (mainMenuChecker == 1) {
			resetProgress();
			loadProgress();
			currentScreenID = 266;
		} else if (mainMenuChecker == 2) {
			loadProgress();
		} else if (mainMenuChecker == 3) {
			hideScreenElements(mainMenu);
			showSettings();
		} else if (mainMenuChecker == 4) {
			Gdx.app.exit();
		}
		mainMenuChecker = 0;
	}


	@Override
	public void render () {
		currentBackgroundColor = updateBackgroundColor(currentBackgroundColor, desiredBackgroundColor);
		Gdx.gl.glClearColor(currentBackgroundColor.r, currentBackgroundColor.g, currentBackgroundColor.b, currentBackgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		for (int i = 0; i < screens.size(); i++) {
			if (screens.get(i).getScreenID() == currentScreenID) {
				currentScreen = screens.get(i);
				if (screens.get(i).getClass() == ChoiceScreen.class && currentScreen != lastFrameCurrentScreen) {
					screens.set(i, new ChoiceScreen(
							screens.get(i).getScreenID(),
							((ChoiceScreen)screens.get(i)).getQuestion(),
							screens.get(i).getChoices(),
							screens.get(i).getScreenLinks(),
							((ChoiceScreen)screens.get(i)).getEffects()));
					currentScreen = screens.get(i);
				} else {

				}
			}
		}
		if (currentScreen != lastFrameCurrentScreen) {
			if (currentScreen.getClass() == ChoiceScreen.class) {
				switch (currentScreenID) {
					case 39: weekDay = "Tiistai";
						break;
					case 81: weekDay = "Keskiviikko";
						break;
					case 126: weekDay = "Torstai";
						break;
					case 164: weekDay = "Perjantai";
						break;
					case 207: weekDay = "Lauantai";
						break;
					case 240: weekDay = "Sunnuntai";
						break;
				}
				((ChoiceScreen) currentScreen).addGlobalElements(feelingMeterButton, meters, settingsButton, settings, weekDay);
				updateMeters(((ChoiceScreen) currentScreen).getEffects());
				feelingMeterButton.setStyle(skin.get(getStrongestEmotion(), Button.ButtonStyle.class));
				saveProgress();
			} else {
				currentScreen.addActor(settings);
				settings.toBack();
			}
		}
		lastFrameCurrentScreen = currentScreen;

		Gdx.input.setInputProcessor(currentScreen);
		currentScreen.draw();
		currentScreen.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));

		if (currentScreen == mainMenu) {
			checkMenuChoice();
		}
	}
	public ArrayList<Screen> createScreens() {
		ArrayList<ChoiceScreen> choiceScreens = createChoiceScreens();
		ArrayList<Screen> allScreens = new ArrayList<>();
		allScreens.addAll(choiceScreens);
		return allScreens;
	}
	public ArrayList<ChoiceScreen> createChoiceScreens() {
		FileHandle handle = Gdx.files.internal("fullleveldata.txt");
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
			ArrayList<String> effects = new ArrayList<>();
			for (int j = startingIndex+1; j <startingIndex+1+amountOfAnswers ; j++) {
				screenLinks.add(getStartOfLineNumber(allLines[j]));
				answers.add(getEndOfLineText(allLines[j]));
				ArrayList<String> newEffects = getAnswerEffects(answers.get(answers.size()-1));
				effects.addAll(newEffects);
			}
			choiceScreens.add(new ChoiceScreen(screenID, question, answers, screenLinks, effects));
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
	public void saveProgress() {
		Preferences prefs = Gdx.app.getPreferences("MyPreferences");
		prefs.putInteger("screen", currentScreenID);
		prefs.putString("day", weekDay);
		prefs.putFloat("happiness", happiness.getValue());
		prefs.putFloat("sadness", sadness.getValue());
		prefs.putFloat("anger", anger.getValue());
		prefs.putFloat("love", love.getValue());
		prefs.putFloat("astonishment", astonishment.getValue());
		prefs.putFloat("fear", fear.getValue());
		prefs.putFloat("disgust", disgust.getValue());
		saveSettings();
		prefs.flush();
	}
	public void loadProgress() {
		Preferences prefs = Gdx.app.getPreferences("MyPreferences");
		currentScreenID = prefs.getInteger("screen", 0);
		weekDay = prefs.getString("day", "Maanantai");
		float meterDefault = 50;
		happiness.setValue(prefs.getFloat("happiness", meterDefault));
		sadness.setValue(prefs.getFloat("sadness", meterDefault));
		anger.setValue(prefs.getFloat("anger", meterDefault));
		love.setValue(prefs.getFloat("love", meterDefault));
		astonishment.setValue(prefs.getFloat("astonishment", meterDefault));
		fear.setValue(prefs.getFloat("fear", meterDefault));
		disgust.setValue(prefs.getFloat("disgust", meterDefault));
		loadSettings();
	}
	public void saveSettings() {
		Preferences prefs = Gdx.app.getPreferences("MySettings");
		prefs.putBoolean("music", musicOn);
		prefs.putBoolean("sound", soundOn);
		prefs.flush();
	}
	public void loadSettings() {
		Preferences prefs = Gdx.app.getPreferences("MySettings");
		musicOn = prefs.getBoolean("music", true);
		soundOn = prefs.getBoolean("sound", true);
		musicButton.setChecked(musicOn);
		soundButton.setChecked(soundOn);
	}
	public void resetProgress() {
		Preferences prefs = Gdx.app.getPreferences("MyPreferences");
		prefs.clear();
		prefs.flush();
	}
	public String getStrongestEmotion() {
		String result = "happiness";
		float largestValue = Math.max(Math.max(Math.max(happiness.getValue(), sadness.getValue()), Math.max(anger.getValue(), love.getValue())), Math.max(disgust.getValue(), Math.max(astonishment.getValue(), fear.getValue())));
		if (sadness.getValue() == largestValue) {
			result = "sadness";
		} else if (anger.getValue() == largestValue) {
			result = "anger";
		} else if (love.getValue() == largestValue) {
			result = "love";
		} else if (disgust.getValue() == largestValue) {
			result = "disgust";
		} else if (fear.getValue() == largestValue) {
			result = "fear";
		} else if (astonishment.getValue() == largestValue) {
			result = "astonishment";
		}

		return result;
	}
	public ArrayList<String> getAnswerEffects(String myAnswer) {
		ArrayList<String> answerEffects = new ArrayList<>();
		for (String indicator : effectIndicators) {
			if (myAnswer.contains(indicator)) {
				boolean effectAdded = false;
				for (int k = 0; k < myAnswer.length() && k + 2 < myAnswer.length() && !effectAdded; k++) {
					char[] charArray1 = new char[3];
					char[] charArray2 = new char[3];
					myAnswer.getChars(k, k + 3, charArray1, 0);
					indicator.getChars(0, 3, charArray2, 0);
					String answerTest = "" + charArray1[0] + charArray1[1] + charArray1[2];
					String indicatorTest = "" + charArray2[0] + charArray2[1] + charArray2[2];
					if (answerTest.equals(indicatorTest)) {
						k += 4;
						String value = "";
						boolean keepChecking = true;
						for (int j = k; j < myAnswer.length() && keepChecking; j++) {
							if (myAnswer.charAt(j) == '+' ||
									myAnswer.charAt(j) == '-' ||
									myAnswer.charAt(j) == '0' ||
									myAnswer.charAt(j) == '1' ||
									myAnswer.charAt(j) == '2' ||
									myAnswer.charAt(j) == '3' ||
									myAnswer.charAt(j) == '4' ||
									myAnswer.charAt(j) == '5' ||
									myAnswer.charAt(j) == '6' ||
									myAnswer.charAt(j) == '7' ||
									myAnswer.charAt(j) == '8' ||
									myAnswer.charAt(j) == '9') {
								keepChecking = false;
								k = j;
							}
						}
						keepChecking = true;
						for (int i = k; i < myAnswer.length() && keepChecking; i++) {
							if (myAnswer.charAt(i) == '+' ||
								myAnswer.charAt(i) == '-' ||
								myAnswer.charAt(i) == '0' ||
								myAnswer.charAt(i) == '1' ||
								myAnswer.charAt(i) == '2' ||
								myAnswer.charAt(i) == '3' ||
								myAnswer.charAt(i) == '4' ||
								myAnswer.charAt(i) == '5' ||
								myAnswer.charAt(i) == '6' ||
								myAnswer.charAt(i) == '7' ||
								myAnswer.charAt(i) == '8' ||
								myAnswer.charAt(i) == '9') {
								value += myAnswer.charAt(i);
							} else {
								keepChecking = false;
							}
						}
						answerEffects.add(answerTest+value);
						effectAdded = true;
					}
				}
			}
		}
		return answerEffects;
	}
	public void updateMeters(ArrayList<String> effects) {
		for (String effect: effects) {
			String indicator = "" + effect.charAt(0) + effect.charAt(1) + effect.charAt(2);
			String value = "";
			for (int i = 3; i < effect.length(); i++) {
				value += effect.charAt(i);
			}
			int change = Integer.parseInt(value);
			String hap = effectIndicators[0].substring(0, 3);
			String sad = effectIndicators[1].substring(0, 3);
			String ang = effectIndicators[2].substring(0, 3);
			String lov = effectIndicators[3].substring(0, 3);
			String fea = effectIndicators[4].substring(0, 3);
			String ast = effectIndicators[5].substring(0, 3);
			String dis = effectIndicators[6].substring(0, 3);
			if (indicator.equals(hap)) {
				happiness.addValue(change);
			} else if (indicator.equals(sad)) {
				sadness.addValue(change);
			} else if (indicator.equals(ang)) {
				anger.addValue(change);
			} else if (indicator.equals(lov)) {
				love.addValue(change);
			} else if (indicator.equals(fea)) {
				fear.addValue(change);
			} else if (indicator.equals(ast)) {
				astonishment.addValue(change);
			} else if (indicator.equals(dis)) {
				disgust.addValue(change);
			}
		}
	}
	public Color updateBackgroundColor(Color currentBGColor, Color desired) {
		Color current = new Color(currentBGColor);
		if (!currentBackgroundColor.equals(desiredBackgroundColor)) {
			float colorIncrement = 0.3f * Gdx.graphics.getDeltaTime();
			if (current.r > desired.r - colorIncrement * 2f && current.r < desired.r + colorIncrement * 2f) {
				current.r = desired.r;
			}
			if (current.g > desired.g - colorIncrement * 2f && current.g < desired.g + colorIncrement * 2f) {
				current.g = desired.g;
			}
			if (current.b > desired.b - colorIncrement * 2f && current.b < desired.b + colorIncrement * 2f) {
				current.b = desired.b;
			}
			if (current.a > desired.a - colorIncrement * 2f && current.a < desired.a + colorIncrement * 2f) {
				current.a = desired.a;
			}

			if (current.r > desired.r) {
				current.r -= colorIncrement;
			} else if (current.r < desired.r) {
				current.r += colorIncrement;
			}
			if (current.g > desired.g) {
				current.g -= colorIncrement;
			} else if (current.g < desired.g) {
				current.g += colorIncrement;
			}
			if (current.b > desired.b) {
				current.b -= colorIncrement;
			} else if (current.b < desired.b) {
				current.b += colorIncrement;
			}
			if (current.a > desired.a) {
				current.a -= colorIncrement;
			} else if (current.a < desired.a) {
				current.a += colorIncrement;
			}
		}
		return current;
	}




	public Button createFeelingMeterButton() {
		final Button button = new Button(skin, "happiness");
		button.setBounds(((float) windowWidth / 3f) - buttonHeight * 0.5f, margin, buttonHeight, buttonHeight);
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Screen thisScreen = (Screen) feelingMeterButton.getStage();
				if (!feelingMeterButton.isChecked()) {
					hideMeters();
					if (!settingsButton.isChecked()) {
						showScreenElements(thisScreen);
					}
				} else {
					showMeters();
					hideScreenElements(thisScreen);
					if (settingsButton.isChecked()) {
						settingsButton.setChecked(false);
					}
				}
			}
		});
		return button;
	}
	public void hideMeters() {
		meters.addAction(Actions.fadeOut(FADE_TIME));
		meters.toBack();
	}
	public void showMeters() {
		meters.addAction(Actions.fadeIn(FADE_TIME));
		meters.toFront();
		desiredBackgroundColor = darkBackgroundColor;
	}
	public void showSettings() {
		settings.addAction(Actions.fadeIn(FADE_TIME));
		settings.toFront();
		desiredBackgroundColor = darkBackgroundColor;
	}
	public void hideSettings() {
		settings.addAction(Actions.fadeOut(FADE_TIME));
		settings.toBack();
	}
	public void showScreenElements(Screen thisScreen) {
		thisScreen.getElements().addAction(Actions.fadeIn(FADE_TIME));
		if (thisScreen.getClass() == ChoiceScreen.class) {
			((ChoiceScreen) thisScreen).answersSetPause(false);
		}
		thisScreen.getElements().toFront();
		desiredBackgroundColor = lightBackgroundColor;
	}
	public void hideScreenElements(Screen thisScreen) {
		thisScreen.getElements().addAction(Actions.fadeOut(FADE_TIME));
		if (thisScreen.getClass() == ChoiceScreen.class) {
			((ChoiceScreen) thisScreen).answersSetPause(true);
		}
		thisScreen.getElements().toBack();
	}
	public Group createMeters() {
		Group result = new Group();
		// Create FeelingMeters
		float meterMargin = margin + meterHeight;
		float meterLocationHeight = meterMargin * 7;
		float currentY = windowHeight - meterLocationHeight;
		happiness = new FeelingMeter(currentY, happinessColor, "happiness");
		result.addActor(happiness);
		currentY += meterMargin;
		sadness = new FeelingMeter(currentY, sadnessColor, "sadness");
		result.addActor(sadness);
		currentY += meterMargin;
		love = new FeelingMeter(currentY, loveColor, "love");
		result.addActor(love);
		currentY += meterMargin;
		anger = new FeelingMeter(currentY, angerColor, "anger");
		result.addActor(anger);
		currentY += meterMargin;
		fear = new FeelingMeter(currentY, fearColor, "fear");
		result.addActor(fear);
		currentY += meterMargin;
		astonishment = new FeelingMeter(currentY, astonishmentColor, "astonishment");
		result.addActor(astonishment);
		currentY += meterMargin;
		disgust = new FeelingMeter(currentY, disgustColor, "disgust");
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
				Screen thisScreen = (Screen) settingsButton.getStage();
				if (!settingsButton.isChecked()) {
					hideSettings();
					if (!feelingMeterButton.isChecked()) {
						showScreenElements(thisScreen);
					}
				} else {
					showSettings();
					hideScreenElements(thisScreen);
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
		musicButton = new Button(skin, "music");
		soundButton = new Button(skin, "sound");
		exitButton = new Button(skin, "exit");
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
				saveSettings();
			}
		});
		soundButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				soundOn = soundButton.isChecked();
				saveSettings();
			}
		});
		exitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//screens = createScreens();
				currentScreenID = 999;
				settingsButton.setChecked(false);
				for (int i = 0; i < screens.size(); i++) {
					if (screens.get(i).getClass() == MainMenuScreen.class) {
						screens.set(i, createMainMenu());
						mainMenu = ((MainMenuScreen)screens.get(i));
					}
				}
				hideSettings();
				showScreenElements(mainMenu);
			}
		});
		musicButton.setChecked(musicOn);
		soundButton.setChecked(soundOn);

		return result;
	}
	@Override
	public void dispose () {

	}



}