
package fi.tuni.tiko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.ArrayList;

import static fi.tuni.tiko.SaveHandler.*;
import static fi.tuni.tiko.Utility.colorMax255;
import static fi.tuni.tiko.Utility.getLocalization;

/** The MainGame class runs the game.
 *
 * The class creates the different screens based on a file.
 *
 * @author Atte Ala-Lahti
 * @author Janika Kupila
 * @author Mika Kivennenä
 */
public class MainGame extends ApplicationAdapter {

	public static ArrayList<Screen> screens;
	private Screen currentScreen;

	public static Color lightBackgroundColor = colorMax255(0f, 151, 167f);
	public static Color darkBackgroundColor = colorMax255(0, 131, 143);
	public static Color desiredBackgroundColor = lightBackgroundColor;
	public static Color currentBackgroundColor = desiredBackgroundColor;

	public static String weekDay;

	public static int currentScreenID;
	private int lastFrameCurrentScreenID;
	public static int nextScreenID = currentScreenID;
	public static int windowWidth;
	public static int windowHeight;
	public static MySkin skin;
	public static GlobalElements globalElements;
	public static float margin;
	public static float meterHeight;
	public static AudioPlayer audioPlayer;
	public static int mainMenuScreenID = 999;
	public static boolean continueUnlocked;

	/** Screens are created. Main menu is created and loaded after.
	 *
	 * The width and height of the screen is determined for all classes to base their sizes on.
	 * All default values are set.
	 */
	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();
		margin = windowHeight * 0.025f;
		meterHeight = windowHeight * 0.1f;
		audioPlayer = new AudioPlayer();

		loadContinueUnlock();
		weekDay = getLocalization("monday");
		skin = new MySkin();
		globalElements = new GlobalElements();
		screens = createScreens(getLocalization("levelDataPath"));

		screens.add(new MainMenuScreen());
		currentScreen = screens.get(screens.size()-1);
		currentScreenID = currentScreen.getScreenID();
		loadSettings();
	}

	/** Draws the game and handles inputs. Changes the screen when an answer is chosen.
	 *
	 * Each frame currentScreenID is checked for changes. If it changes, the screen is changed to that of the new ID.
	 * The screen is refreshed and global UI elements are added to it.
	 * If one of the emotions is in a critical state when switching screen, an emotion info screen is moved to instead.
	 */
	@Override
	public void render () {
		currentBackgroundColor = updateBackgroundColor(currentBackgroundColor, desiredBackgroundColor);
		Gdx.gl.glClearColor(currentBackgroundColor.r, currentBackgroundColor.g, currentBackgroundColor.b, currentBackgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (currentScreenID != lastFrameCurrentScreenID) {
			for (int i = 0; i < screens.size(); i++) {
				if (screens.get(i).getScreenID() == currentScreenID) {
					currentScreen = screens.get(i);
					if (screens.get(i).getClass() == MainMenuScreen.class) {
						screens.set(i, new MainMenuScreen());
						currentScreen.addActor(globalElements.getSettings());
						globalElements.getSettings().toBack();
					} else {
						int emotionAlertScreenID = getEmotionAlertScreen();
						if (emotionAlertScreenID != 0 && lastFrameCurrentScreenID != mainMenuScreenID) {
							for (int j = 0; j < screens.size(); j++) {
								if (screens.get(j).getScreenID() == emotionAlertScreenID) {
									ArrayList<Integer> tempLink = new ArrayList<>();
									tempLink.add(currentScreenID);
									screens.set(j, new ChoiceScreen(
											screens.get(j).getScreenID(),
											((ChoiceScreen)screens.get(j)).getQuestion(),
											screens.get(j).getChoices(),
											tempLink,
											((ChoiceScreen)screens.get(j)).getEffects()));
									currentScreen = screens.get(j);
								}
							}
							currentScreen.getScreenLinks().set(0, currentScreenID);
							nextScreenID = currentScreenID;
							currentScreenID = 10000;
						} else {
							screens.set(i, new ChoiceScreen(
									screens.get(i).getScreenID(),
									((ChoiceScreen)screens.get(i)).getQuestion(),
									screens.get(i).getChoices(),
									screens.get(i).getScreenLinks(),
									((ChoiceScreen)screens.get(i)).getEffects()));
							currentScreen = screens.get(i);
							nextScreenID = currentScreenID;
						}
						switch (currentScreenID) {
							case 39: weekDay = getLocalization("tuesday");
								break;
							case 81: weekDay = getLocalization("wednesday");
								break;
							case 126: weekDay = getLocalization("thursday");
								break;
							case 164: weekDay = getLocalization("friday");
								break;
							case 207: weekDay = getLocalization("saturday");
								break;
							case 240: weekDay = getLocalization("sunday");
								break;
						}
						((ChoiceScreen) currentScreen).activate(globalElements, weekDay);
						if (lastFrameCurrentScreenID != mainMenuScreenID) {
							globalElements.updateMeters(((ChoiceScreen) currentScreen).getEffects());
						}
						globalElements.getFeelingMeterButton().setStyle(skin.get(globalElements.getStrongestEmotion(), Button.ButtonStyle.class));
						continueUnlocked = true;
						saveProgress();
					}
				}
			}
		}
		lastFrameCurrentScreenID = currentScreenID;

		Gdx.input.setInputProcessor(currentScreen);
		currentScreen.draw();
		currentScreen.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
	}

	/** Returns the ID of the emotion alert screen if one of the emotions is in a critical enough condition.
	 *
	 * Too little disgust doesn't have it's own alert screen because it's impossible to happen in the game.
	 *
	 * @return ID of the emotion alert screen, if there isn't need for it, returns 0
	 */
	public int getEmotionAlertScreen() {
		int result = 0;
		String [] emotions = {"anger", "sadness", "disgust", "love", "happiness", "astonishment", "fear"};
		int link = 401;
		for (int i = 0; i < 7; i++) {
			if (globalElements.getMeter(emotions[i]).getValue() == 100) {
				result = link;
			} else if (globalElements.getMeter(emotions[i]).getValue() == 0 && i != 2) {
				result = link + 1;
			}
			link += 2;
		}
		return result;
	}

	/** Creates the ChoiceScreens from a file. Adds them to a general screens list.
	 *
	 * @param path filename of the file to create the screens from
	 * @return list of all screens
	 */
	public ArrayList<Screen> createScreens(String path) {
		ArrayList<ChoiceScreen> choiceScreens = createChoiceScreens(path);
		ArrayList<Screen> allScreens = new ArrayList<>();
		allScreens.addAll(choiceScreens);
		return allScreens;
	}

	/** Creates ChoiceScreens from a file.
	 *
	 * In the file screens are separated with a line of "---".
	 * Each line start with it's associated screen ID.
	 * Answer lines' ID is the one they lead to.
	 *
	 * @param path file name of the file to read from
	 * @return the list of all ChoiceScreens in the game.
	 */
	public ArrayList<ChoiceScreen> createChoiceScreens(String path) {
		FileHandle handle = Gdx.files.internal(path);
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

	/** Attempts to parse the first characters of a string up to a space into an integer and return it.
	 *
	 * Is used in creating ChoiceScreens from a file.
	 *
	 * @param line line to get the first number from
	 * @return first characters of the line interpreted as an integer
	 */
	public int getStartOfLineNumber(String line) {
		StringBuilder number = new StringBuilder();
		int index = 0;
		while (line.charAt(index) != ' ') {
			number.append(line.charAt(index));
			index++;
		}
		return Integer.parseInt(number.toString());
	}

	/** Returns everything in a String after a space.
	 *
	 * Is used in creating ChoiceScreens from a file.
	 *
	 * @param line line to read from
	 * @return everything after a space
	 */
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

	/** Returns possible emotional effects on the feeling meters based on a given answer text.
	 *
	 * Goes through all seven emotions trying to find emotional effect indicators in the line it is given.
	 * If it finds them, it will find out how much that emotion is changed and add the first three letters and the amount of change to a list of effects.
	 *
	 * @param myAnswer the answer text to find the emotional effects from
	 * @return list of emotional effects found in the answer, formatted for future use
	 */
	public ArrayList<String> getAnswerEffects(String myAnswer) {
		ArrayList<String> answerEffects = new ArrayList<>();
		for (String indicator : globalElements.getEmotions()) {
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

	/** Returns a color that is slightly more like another color.
	 *
	 * Called every frame to make smooth transitions between background colors.
	 *
	 * @param currentBGColor the color to change
	 * @param desired the color to change to
	 * @return original color changed to be slightly more like the target color
	 */
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

	/** Disposes all screens, the skin and audio player.
	 */
	@Override
	public void dispose () {
		for (Screen s: screens) {
			s.dispose();
		}
		skin.dispose();
		audioPlayer.dispose();
	}
}