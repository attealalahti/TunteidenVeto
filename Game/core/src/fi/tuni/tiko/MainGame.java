
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
	private Texture img;
	private Texture answerBoxTexture;
	private Texture answerBoxHighlightedTexture;
	private Texture immobileBoxTexture;
	private Texture questionBoxTexture;
	private Texture bigQuestionBoxTexture;
	private Texture settingsTexture;
	private Texture settingsPressedTexture;
	private Texture mainMenuTexture;
	private Texture arrowBoxTexture;
	private Texture empty;
	private Texture feelingMeterTexture;
	private Texture musicOnTexture;
	private Texture musicOffTexture;
	private Texture soundOnTexture;
	private Texture soundOffTexture;
	private Texture railTexture;
	private Texture angerTexture;
	private Texture astonishmentTexture;
	private Texture disgustTexture;
	private Texture fearTexture;
	private Texture happinessTexture;
	private Texture loveTexture;
	private Texture sadnessTexture;
	private Texture angerButtonTexture;
	private Texture astonishmentButtonTexture;
	private Texture disgustButtonTexture;
	private Texture fearButtonTexture;
	private Texture happinessButtonTexture;
	private Texture loveButtonTexture;
	private Texture sadnessButtonTexture;

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
	private float colorFraction = 1f / 255f;
	private Color loveColor = new Color(colorFraction * 234, colorFraction * 140, colorFraction * 128, 1);
	private Color happinessColor = new Color(colorFraction * 249, colorFraction * 212, colorFraction * 7, 1);
	private Color angerColor = new Color(colorFraction * 143, colorFraction * 12, colorFraction * 0, 1);
	private Color astonishmentColor = new Color(colorFraction * 64, colorFraction * 165, colorFraction * 193, 1);
	private Color sadnessColor = new Color(colorFraction * 0, colorFraction * 59, colorFraction * 143, 1);
	private Color disgustColor = new Color(colorFraction * 60, colorFraction * 143, colorFraction * 0, 1);
	private Color fearColor = new Color(colorFraction * 51, colorFraction * 51, colorFraction * 51, 1);
	private Color lightBackgroundColor = new Color(colorFraction * 0f, colorFraction * 151, colorFraction * 167f, 1);
	private Color darkBackgroundColor = new Color(colorFraction * 0, colorFraction * 131, colorFraction * 143, 1);
	private Color desiredBackgroundColor = lightBackgroundColor;
	private Color currentBackgroundColor = desiredBackgroundColor;
	private String [] effectIndicators = {"ILO", "SURU", "VIHA", "RAKKAUS", "PELKO", "HÄMMENNYS", "INHO"};

	private Color secondaryColor = new Color(colorFraction * 234, colorFraction * 158, colorFraction * 128, 1);

	public static int currentScreenID;
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


	private String pixelDensity = "";
	private int fontSize;

	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();
		buttonHeight =  windowHeight * 0.07f;
		bigButtonHeight = buttonHeight * 2f;
		margin = windowHeight * 0.025f;
		meterHeight = windowHeight * 0.1f;
		fontSize = getFontSize();


		answerBoxTexture = new Texture(getPath("box"));
		immobileBoxTexture = new Texture(getPath("box2"));
		questionBoxTexture = new Texture(getPath("textbox"));
		settingsTexture = new Texture(getPath("hamburgermenu"));
		mainMenuTexture = new Texture(getPath("mainmenubutton"));
		empty = new Texture(getPath("button"));
		feelingMeterTexture = new Texture(getPath("meter"));
		musicOnTexture = new Texture(getPath("music_on"));
		musicOffTexture = new Texture(getPath("music_off"));
		soundOnTexture = new Texture(getPath("sound_on"));
		soundOffTexture = new Texture(getPath("sound_off"));
		railTexture = new Texture(getPath("rail"));
		angerTexture = new Texture(getPath("anger"));
		astonishmentTexture = new Texture(getPath("astonishment"));
		disgustTexture = new Texture(getPath("disgust"));
		fearTexture = new Texture(getPath("fear"));
		happinessTexture = new Texture(getPath("joy"));
		loveTexture = new Texture(getPath("love"));
		sadnessTexture = new Texture(getPath("sadness"));
		String button = "_button";
		angerButtonTexture = new Texture(getPath("anger"+button));
		astonishmentButtonTexture = new Texture(getPath("astonishment"+button));
		disgustButtonTexture = new Texture(getPath("disgust"+button));
		fearButtonTexture = new Texture(getPath("fear"+button));
		happinessButtonTexture = new Texture(getPath("joy"+button));
		loveButtonTexture = new Texture(getPath("love"+button));
		sadnessButtonTexture = new Texture(getPath("sadness"+button));


		img = new Texture("badlogic.jpg");

		settingsPressedTexture = new Texture("hdpi/hamburgermenu_pressedhdpi.png");
		answerBoxHighlightedTexture = new Texture("hdpi/box3hdpi.png");
		arrowBoxTexture = new Texture("hdpi/arrowboxhdpi.png");
		bigQuestionBoxTexture = new Texture("hdpi/bigtextboxhdpi.png");

		skin = createSkin();

		settingsButton = createSettingsButton();
		feelingMeterButton = createFeelingMeterButton();
		settings = createSettings();
		meters = createMeters();
		screens = createChoiceScreens();
		loadProgress();
		currentScreen = screens.get(currentScreenID);
		lastFrameCurrentScreen = currentScreen;
		((ChoiceScreen) currentScreen).addGlobalElements(feelingMeterButton, meters, settingsButton, settings);
	}
	public String getPath(String texture) {
		pixelDensity = getPixelDensity();
		String folderToUse = pixelDensity + "/";
		String suffix = pixelDensity + ".png";
		return folderToUse + texture + suffix;
	}
	@Override
	public void render () {
		currentBackgroundColor = updateBackgroundColor(currentBackgroundColor, desiredBackgroundColor);
		Gdx.gl.glClearColor(currentBackgroundColor.r, currentBackgroundColor.g, currentBackgroundColor.b, currentBackgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		for (Screen screen: screens) {
			if (screen.getScreenID() == currentScreenID) {
				currentScreen = screen;
			}
		}

		if (currentScreen.getClass() == ChoiceScreen.class && currentScreen != lastFrameCurrentScreen) {
			((ChoiceScreen) currentScreen).addGlobalElements(feelingMeterButton, meters, settingsButton, settings);
			updateMeters(((ChoiceScreen) currentScreen).getEffects());
			feelingMeterButton.setStyle(skin.get(getStrongestEmotion(), Button.ButtonStyle.class));
			saveProgress();
		}
		lastFrameCurrentScreen = currentScreen;

		Gdx.input.setInputProcessor(currentScreen);
		currentScreen.draw();
		currentScreen.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
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
		prefs.putFloat("happiness", happiness.getValue());
		prefs.putFloat("sadness", sadness.getValue());
		prefs.putFloat("anger", anger.getValue());
		prefs.putFloat("love", love.getValue());
		prefs.putFloat("astonishment", astonishment.getValue());
		prefs.putFloat("fear", fear.getValue());
		prefs.putFloat("disgust", disgust.getValue());

		prefs.flush();
	}
	public void loadProgress() {
		Preferences prefs = Gdx.app.getPreferences("MyPreferences");
		currentScreenID = prefs.getInteger("screen", 0);
		float meterDefault = 50;
		happiness.setValue(prefs.getFloat("happiness", meterDefault));
		sadness.setValue(prefs.getFloat("sadness", meterDefault));
		anger.setValue(prefs.getFloat("anger", meterDefault));
		love.setValue(prefs.getFloat("love", meterDefault));
		astonishment.setValue(prefs.getFloat("astonishment", meterDefault));
		fear.setValue(prefs.getFloat("fear", meterDefault));
		disgust.setValue(prefs.getFloat("disgust", meterDefault));

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
	public Skin createSkin() {
		Skin s = new Skin();
		Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		s.add("answer_box", answerBoxTexture);
		s.add("highlighted_box", answerBoxHighlightedTexture);
		s.add("immobile_box", immobileBoxTexture);
		s.add("questionBox", questionBoxTexture);
		s.add("bigQuestionBox", bigQuestionBoxTexture);
		s.add("arrow_box", arrowBoxTexture);
		s.add("white", new Texture(pixmap));
		s.add("default", new BitmapFont());
		s.add("settings", settingsTexture);
		s.add("mainMenu", mainMenuTexture);
		s.add("empty", empty);
		s.add("meter", feelingMeterTexture);
		s.add("soundOn", soundOnTexture);
		s.add("soundOff", soundOffTexture);
		s.add("musicOn", musicOnTexture);
		s.add("musicOff", musicOffTexture);
		s.add("rail_img", railTexture);
		s.add("test", img);
		s.add("font", createFont());
		s.add("anger", angerTexture);
		s.add("astonishment", astonishmentTexture);
		s.add("disgust", disgustTexture);
		s.add("fear", fearTexture);
		s.add("happiness", happinessTexture);
		s.add("love", loveTexture);
		s.add("sadness", sadnessTexture);
		s.add("angerButton", angerButtonTexture);
		s.add("astonishmentButton", astonishmentButtonTexture);
		s.add("disgustButton", disgustButtonTexture);
		s.add("fearButton", fearButtonTexture);
		s.add("happinessButton", happinessButtonTexture);
		s.add("loveButton", loveButtonTexture);
		s.add("sadnessButton", sadnessButtonTexture);


		s.add("settings_pressed", settingsPressedTexture);

		Label.LabelStyle answerStyle = new Label.LabelStyle();
		answerStyle.background = s.newDrawable("answer_box");
		answerStyle.font = s.getFont("default");

		Label.LabelStyle answerHighlightedStyle = new Label.LabelStyle();
		answerHighlightedStyle.background = s.newDrawable("highlighted_box");
		answerHighlightedStyle.font = s.getFont("default");

		Label.LabelStyle immobileAnswerStyle = new Label.LabelStyle();
		immobileAnswerStyle.background = s.newDrawable("immobile_box");
		immobileAnswerStyle.font = s.getFont("default");

		Label.LabelStyle questionStyle = new Label.LabelStyle();
		questionStyle.background = s.newDrawable("questionBox");
		questionStyle.font = s.getFont("font");

		Label.LabelStyle questionStyleBig = new Label.LabelStyle();
		questionStyleBig.background = s.newDrawable("bigQuestionBox");
		questionStyleBig.font = s.getFont("font");

		Label.LabelStyle arrowStyle = new Label.LabelStyle();
		arrowStyle.background = s.newDrawable("arrow_box");
		arrowStyle.font = s.getFont("default");

		Label.LabelStyle textBoxStyle = new Label.LabelStyle();
		textBoxStyle.background = s.newDrawable("white", Color.CLEAR);
		textBoxStyle.font = s.getFont("font");

		Button.ButtonStyle feelingsButtonStyle = new Button.ButtonStyle();
		feelingsButtonStyle.up = s.newDrawable("empty");
		feelingsButtonStyle.down = s.newDrawable("empty", Color.DARK_GRAY);

		Button.ButtonStyle settingsButtonStyle = new Button.ButtonStyle();
		settingsButtonStyle.up = s.newDrawable("settings");
		settingsButtonStyle.down = s.newDrawable("settings_pressed", Color.WHITE);
		settingsButtonStyle.checked = s.newDrawable("settings_pressed", Color.WHITE);

		Button.ButtonStyle soundStyle = new Button.ButtonStyle();
		soundStyle.up = s.newDrawable("soundOff");
		soundStyle.down = s.newDrawable("soundOff", secondaryColor);
		soundStyle.checked = s.newDrawable("soundOn");
		soundStyle.checkedDown = s.newDrawable("soundOn", secondaryColor);

		Button.ButtonStyle musicStyle = new Button.ButtonStyle();
		musicStyle.up = s.newDrawable("musicOff");
		musicStyle.down = s.newDrawable("musicOff", secondaryColor);
		musicStyle.checked = s.newDrawable("musicOn");
		musicStyle.checkedDown = s.newDrawable("musicOn", secondaryColor);

		Button.ButtonStyle exitStyle = new Button.ButtonStyle();
		exitStyle.up = s.newDrawable("mainMenu");
		exitStyle.down = s.newDrawable("mainMenu", secondaryColor);

		Button.ButtonStyle buttonStyleAlt = new Button.ButtonStyle();
		buttonStyleAlt.up = s.newDrawable("test");
		buttonStyleAlt.down = s.newDrawable("test", Color.DARK_GRAY);

		Label.LabelStyle feelingMeterForeGroundStyle = new Label.LabelStyle();
		feelingMeterForeGroundStyle.font = s.getFont("default");
		feelingMeterForeGroundStyle.background = s.newDrawable("meter");

		Label.LabelStyle railStyle = new Label.LabelStyle();
		railStyle.font = s.getFont("default");
		railStyle.background = s.newDrawable("rail_img");

		Label.LabelStyle angerStyle = new Label.LabelStyle();
		angerStyle.font = s.getFont("default");
		angerStyle.background = s.newDrawable("anger");

		Label.LabelStyle astonishmentStyle = new Label.LabelStyle();
		astonishmentStyle.font = s.getFont("default");
		astonishmentStyle.background = s.newDrawable("astonishment");

		Label.LabelStyle disgustStyle = new Label.LabelStyle();
		disgustStyle.font = s.getFont("default");
		disgustStyle.background = s.newDrawable("disgust");

		Label.LabelStyle fearStyle = new Label.LabelStyle();
		fearStyle.font = s.getFont("default");
		fearStyle.background = s.newDrawable("fear");

		Label.LabelStyle happinessStyle = new Label.LabelStyle();
		happinessStyle.font = s.getFont("default");
		happinessStyle.background = s.newDrawable("happiness");

		Label.LabelStyle loveStyle = new Label.LabelStyle();
		loveStyle.font = s.getFont("default");
		loveStyle.background = s.newDrawable("love");

		Label.LabelStyle sadnessStyle = new Label.LabelStyle();
		sadnessStyle.font = s.getFont("default");
		sadnessStyle.background = s.newDrawable("sadness");

		Button.ButtonStyle happinessButtonStyle = new Button.ButtonStyle();
		happinessButtonStyle.up = s.newDrawable("happinessButton");
		happinessButtonStyle.down = s.newDrawable("happinessButton", secondaryColor);
		happinessButtonStyle.checked = s.newDrawable("happinessButton", secondaryColor);

		Button.ButtonStyle sadnessButtonStyle = new Button.ButtonStyle();
		sadnessButtonStyle.up = s.newDrawable("sadnessButton");
		sadnessButtonStyle.down = s.newDrawable("sadnessButton", secondaryColor);
		sadnessButtonStyle.checked = s.newDrawable("sadnessButton", secondaryColor);

		Button.ButtonStyle angerButtonStyle = new Button.ButtonStyle();
		angerButtonStyle.up = s.newDrawable("angerButton");
		angerButtonStyle.down = s.newDrawable("angerButton", secondaryColor);
		angerButtonStyle.checked = s.newDrawable("angerButton", secondaryColor);

		Button.ButtonStyle loveButtonStyle = new Button.ButtonStyle();
		loveButtonStyle.up = s.newDrawable("loveButton");
		loveButtonStyle.down = s.newDrawable("loveButton", secondaryColor);
		loveButtonStyle.checked = s.newDrawable("loveButton", secondaryColor);

		Button.ButtonStyle disgustButtonStyle = new Button.ButtonStyle();
		disgustButtonStyle.up = s.newDrawable("disgustButton");
		disgustButtonStyle.down = s.newDrawable("disgustButton", secondaryColor);
		disgustButtonStyle.checked = s.newDrawable("disgustButton", secondaryColor);

		Button.ButtonStyle fearButtonStyle = new Button.ButtonStyle();
		fearButtonStyle.up = s.newDrawable("fearButton");
		fearButtonStyle.down = s.newDrawable("fearButton", secondaryColor);
		fearButtonStyle.checked = s.newDrawable("fearButton", secondaryColor);

		Button.ButtonStyle astonishmentButtonStyle = new Button.ButtonStyle();
		astonishmentButtonStyle.up = s.newDrawable("astonishmentButton");
		astonishmentButtonStyle.down = s.newDrawable("astonishmentButton", secondaryColor);
		astonishmentButtonStyle.checked = s.newDrawable("astonishmentButton", secondaryColor);



		s.add("answer_movable", answerStyle);
		s.add("answer_static", immobileAnswerStyle);
		s.add("answer_highlighted", answerHighlightedStyle);
		s.add("question", questionStyle);
		s.add("bigQuestion", questionStyleBig);
		s.add("arrow", arrowStyle);
		s.add("feelings", feelingsButtonStyle);
		s.add("alt", buttonStyleAlt);
		s.add("text", textBoxStyle);
		s.add("settings", settingsButtonStyle);
		s.add("sound", soundStyle);
		s.add("music", musicStyle);
		s.add("exit", exitStyle);
		s.add("feelingMeterForeground", feelingMeterForeGroundStyle);
		s.add("rail", railStyle);
		s.add("anger", angerStyle);
		s.add("astonishment", astonishmentStyle);
		s.add("disgust", disgustStyle);
		s.add("fear", fearStyle);
		s.add("happiness", happinessStyle);
		s.add("love", loveStyle);
		s.add("sadness", sadnessStyle);
		s.add("anger", angerButtonStyle);
		s.add("astonishment", astonishmentButtonStyle);
		s.add("disgust", disgustButtonStyle);
		s.add("fear", fearButtonStyle);
		s.add("happiness", happinessButtonStyle);
		s.add("love", loveButtonStyle);
		s.add("sadness", sadnessButtonStyle);


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
		fontParameter.color = Color.BLACK;

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
						desiredBackgroundColor = lightBackgroundColor;
					}
				} else {
					meters.addAction(Actions.fadeIn(FADE_TIME));
					meters.toFront();
					desiredBackgroundColor = darkBackgroundColor;
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
				ChoiceScreen thisScreen = (ChoiceScreen) settingsButton.getStage();
				if (!settingsButton.isChecked()) {
					settings.addAction(Actions.fadeOut(FADE_TIME));
					settings.toBack();
					if (!feelingMeterButton.isChecked()) {
						thisScreen.getGameElements().addAction(Actions.fadeIn(FADE_TIME));
						thisScreen.answersSetPause(false);
						thisScreen.getGameElements().toFront();
						desiredBackgroundColor = lightBackgroundColor;
					}
				} else {
					settings.addAction(Actions.fadeIn(FADE_TIME));
					settings.toFront();
					desiredBackgroundColor = darkBackgroundColor;
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
				resetProgress();
				loadProgress();
			}
		});
		musicButton.setChecked(musicOn);
		soundButton.setChecked(soundOn);

		return result;
	}
	@Override
	public void dispose () {
		img.dispose();
		answerBoxTexture.dispose();
		questionBoxTexture.dispose();
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
			tempInt = 120;
		} else {
			tempInt = 60 * (int)density;
		}

		return tempInt;
	}
}