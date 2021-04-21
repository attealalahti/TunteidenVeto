
package fi.tuni.tiko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import java.util.ArrayList;


public class MainGame extends ApplicationAdapter {

	public static ArrayList<Screen> screens;
	private Screen currentScreen;
	public static MainMenuScreen mainMenu;

	private Screen lastFrameCurrentScreen;

	public static Color lightBackgroundColor = colorMax255(0f, 151, 167f);
	public static Color darkBackgroundColor = colorMax255(0, 131, 143);
	public static Color desiredBackgroundColor = lightBackgroundColor;
	public static Color currentBackgroundColor = desiredBackgroundColor;
	private String [] effectIndicators = {"ILO", "SURU", "VIHA", "RAKKAUS", "PELKO", "HÄMMENNYS", "INHO"};
	private String weekDay = "Maanantai";
	private GlobalElements globalElements;

	public static int currentScreenID;
	public static int mainMenuChecker = 0;
	public static int windowWidth;
	public static int windowHeight;
	public static MySkin skin;
	public static boolean musicOn;
	public static boolean soundOn;
	public static float margin;
	public static float meterHeight;

	AudioPlayer audioPlayer;
	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();
		margin = windowHeight * 0.025f;
		meterHeight = windowHeight * 0.1f;
		audioPlayer = new AudioPlayer();

		skin = new MySkin();

		globalElements = new GlobalElements();
		screens = createScreens();

		loadProgress();

		mainMenu = new MainMenuScreen();
		screens.add(mainMenu);
		currentScreen = mainMenu;
		currentScreenID = currentScreen.getScreenID();

		//audioPlayer.playMenuMusic();
	}

	public static Color colorMax255(float r, float g, float b) {
		float colorFraction = 1f / 255f;
		return new Color(r*colorFraction, g*colorFraction, b*colorFraction, 1);
	}

	public void checkMenuChoice() {
		if (mainMenuChecker == 1) {
			resetProgress();
			loadProgress();
			currentScreenID = 266;
		} else if (mainMenuChecker == 2) {
			loadProgress();
		} else if (mainMenuChecker == 3) {
			globalElements.hideScreenElements(mainMenu);
			globalElements.showSettings();
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
				((ChoiceScreen) currentScreen).addGlobalElements(globalElements, weekDay);
				updateMeters(((ChoiceScreen) currentScreen).getEffects());
				globalElements.getFeelingMeterButton().setStyle(skin.get(getStrongestEmotion(), Button.ButtonStyle.class));
				saveProgress();
			} else {
				currentScreen.addActor(globalElements.getSettings());
				globalElements.getSettings().toBack();
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
		prefs.putFloat("happiness", globalElements.getMeter("happiness").getValue());
		prefs.putFloat("sadness", globalElements.getMeter("sadness").getValue());
		prefs.putFloat("anger", globalElements.getMeter("anger").getValue());
		prefs.putFloat("love", globalElements.getMeter("love").getValue());
		prefs.putFloat("astonishment", globalElements.getMeter("astonishment").getValue());
		prefs.putFloat("fear", globalElements.getMeter("fear").getValue());
		prefs.putFloat("disgust", globalElements.getMeter("disgust").getValue());
		saveSettings();
		prefs.flush();
	}
	public void loadProgress() {
		Preferences prefs = Gdx.app.getPreferences("MyPreferences");
		currentScreenID = prefs.getInteger("screen", 0);
		weekDay = prefs.getString("day", "Maanantai");
		float meterDefault = 50;
		globalElements.getMeter("happiness").setValue(prefs.getFloat("happiness", meterDefault));
		globalElements.getMeter("sadness").setValue(prefs.getFloat("sadness", meterDefault));
		globalElements.getMeter("anger").setValue(prefs.getFloat("anger", meterDefault));
		globalElements.getMeter("love").setValue(prefs.getFloat("love", meterDefault));
		globalElements.getMeter("astonishment").setValue(prefs.getFloat("astonishment", meterDefault));
		globalElements.getMeter("fear").setValue(prefs.getFloat("fear", meterDefault));
		globalElements.getMeter("disgust").setValue(prefs.getFloat("disgust", meterDefault));
		loadSettings();
	}
	public static void saveSettings() {
		Preferences prefs = Gdx.app.getPreferences("MySettings");
		prefs.putBoolean("music", musicOn);
		prefs.putBoolean("sound", soundOn);
		prefs.flush();
	}
	public void loadSettings() {
		Preferences prefs = Gdx.app.getPreferences("MySettings");
		musicOn = prefs.getBoolean("music", true);
		soundOn = prefs.getBoolean("sound", true);
		globalElements.getMusicButton().setChecked(musicOn);
		globalElements.getSoundButton().setChecked(soundOn);
	}
	public void resetProgress() {
		Preferences prefs = Gdx.app.getPreferences("MyPreferences");
		prefs.clear();
		prefs.flush();
	}
	public String getStrongestEmotion() {
		String result = "happiness";
		float largestValue = Math.max(Math.max(Math.max(globalElements.getMeter("happiness").getValue(), globalElements.getMeter("sadness").getValue()), Math.max(globalElements.getMeter("anger").getValue(), globalElements.getMeter("love").getValue())), Math.max(globalElements.getMeter("fear").getValue(), Math.max(globalElements.getMeter("disgust").getValue(), globalElements.getMeter("astonishment").getValue())));
		if (globalElements.getMeter("sadness").getValue() == largestValue) {
			result = "sadness";
		} else if (globalElements.getMeter("anger").getValue() == largestValue) {
			result = "anger";
		} else if (globalElements.getMeter("love").getValue() == largestValue) {
			result = "love";
		} else if (globalElements.getMeter("disgust").getValue() == largestValue) {
			result = "disgust";
		} else if (globalElements.getMeter("fear").getValue() == largestValue) {
			result = "fear";
		} else if (globalElements.getMeter("astonishment").getValue() == largestValue) {
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
						StringBuilder value = new StringBuilder();
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
								value.append(myAnswer.charAt(i));
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
			StringBuilder value = new StringBuilder();
			for (int i = 3; i < effect.length(); i++) {
				value.append(effect.charAt(i));
			}
			int change = Integer.parseInt(value.toString());
			String hap = effectIndicators[0].substring(0, 3);
			String sad = effectIndicators[1].substring(0, 3);
			String ang = effectIndicators[2].substring(0, 3);
			String lov = effectIndicators[3].substring(0, 3);
			String fea = effectIndicators[4].substring(0, 3);
			String ast = effectIndicators[5].substring(0, 3);
			String dis = effectIndicators[6].substring(0, 3);
			if (indicator.equals(hap)) {
				globalElements.getMeter("happiness").addValue(change);
			} else if (indicator.equals(sad)) {
				globalElements.getMeter("sadness").addValue(change);
			} else if (indicator.equals(ang)) {
				globalElements.getMeter("anger").addValue(change);
			} else if (indicator.equals(lov)) {
				globalElements.getMeter("love").addValue(change);
			} else if (indicator.equals(fea)) {
				globalElements.getMeter("fear").addValue(change);
			} else if (indicator.equals(ast)) {
				globalElements.getMeter("astonishment").addValue(change);
			} else if (indicator.equals(dis)) {
				globalElements.getMeter("disgust").addValue(change);
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

	@Override
	public void dispose () {
		for (Screen s: screens) {
			s.dispose();
		}
		skin.dispose();
	}
}