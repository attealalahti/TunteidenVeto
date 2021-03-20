
package fi.tuni.tiko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
	private ArrayList<ChoiceScreen> screens;
	private Screen currentScreen;
	private Group meters;
	private Button feelingMeterButton;
	private Group settings;
	private Button settingsButton;

	public static int currentScreenID = 0;
	public static int windowWidth;
	public static int windowHeight;
	public static Skin skin;
	public static boolean musicOn = true;
	public static boolean soundsOn = true;

	private float buttonHeight;
	private float bigButtonHeight;
	private float meterWidth;
	private float meterHeight;
	// How long it takes to switch between Game and FeelingMeter mode:
	private float FADE_TIME = 0.2f;
	private float margin;


	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();
		buttonHeight =  windowHeight * 0.07f;
		bigButtonHeight = buttonHeight * 2f;
		meterWidth = windowWidth * 0.8f;
		meterHeight = windowHeight * 0.1f;
		margin = windowHeight * 0.025f;

		boxTexture = new Texture("boxshadowmdpi.png");
		bigBoxTexture = new Texture("bigboxshadowmdpi.png");
		happy = new Texture("ilo_reunatmdpi.png");
		settingsTexture = new Texture("hamburger.png");
		empty = new Texture("emptycircle.png");
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
		Gdx.gl.glClearColor(fraction * 81f, fraction * 99f, fraction * 115f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		for (Screen screen: screens) {
			if (screen.getScreenID() == currentScreenID) {
				currentScreen = screen;
			}
		}
		if (currentScreen.getClass() == ChoiceScreen.class) {
			((ChoiceScreen) currentScreen).addGlobalElements(feelingMeterButton, meters, settingsButton, settings);
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
		s.add("happy", happy);
		s.add("settings", settingsTexture);
		s.add("empty", empty);
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
		feelingsButtonStyle.up = s.newDrawable("empty");
		feelingsButtonStyle.down = s.newDrawable("empty", Color.DARK_GRAY);

		Button.ButtonStyle happyStyle = new Button.ButtonStyle();
		happyStyle.up = s.newDrawable("happy");
		happyStyle.down = s.newDrawable("happy", Color.DARK_GRAY);
		happyStyle.checked = s.newDrawable("happy", Color.DARK_GRAY);

		Button.ButtonStyle settingsButtonStyle = new Button.ButtonStyle();
		settingsButtonStyle.up = s.newDrawable("settings");
		settingsButtonStyle.down = s.newDrawable("settings", Color.DARK_GRAY);
		settingsButtonStyle.checked = s.newDrawable("settings", Color.DARK_GRAY);

		Button.ButtonStyle buttonStyleAlt = new Button.ButtonStyle();
		buttonStyleAlt.up = s.newDrawable("test");
		buttonStyleAlt.down = s.newDrawable("test", Color.DARK_GRAY);



		s.add("default", answerStyle);
		s.add("question", questionStyle);
		s.add("feelings", feelingsButtonStyle);
		s.add("happiness", happyStyle);
		s.add("alt", buttonStyleAlt);
		s.add("text", textBoxStyle);
		s.add("settings", settingsButtonStyle);

		return s;
	}
	public Button createSettingsButton() {
		final Button button = new Button(MainGame.skin, "settings");
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
	public Button createFeelingMeterButton() {
		final Button button = new Button(MainGame.skin, "happiness");
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
		float meterLocationHeight = meterHeight * 7 + margin * 7;
		float currentY = windowHeight - meterLocationHeight;
		for (int i = 0; i < 7; i++) {
			Label myLabel = new Label(i + "", MainGame.skin, "question");
			myLabel.setBounds(windowWidth * 0.5f - meterWidth * 0.5f, currentY, meterWidth, meterHeight);
			myLabel.setAlignment(0);
			myLabel.setFontScaleX(0.005f * windowWidth);
			myLabel.setFontScaleY(0.003f * windowHeight);
			result.addActor(myLabel);
			currentY += margin + meterHeight;
		}
		// Hide the meters initially
		result.toBack();
		result.addAction(Actions.fadeOut(0));

		return result;
	}
	public Group createSettings() {
		Group result = new Group();
		// Create buttons for settings
		final Button musicButton = new Button(MainGame.skin, "alt");
		final Button soundsButton = new Button(MainGame.skin, "alt");
		final Button exitButton = new Button(MainGame.skin, "alt");
		float centerX = windowWidth * 0.5f - bigButtonHeight * 0.5f;
		float centerY = windowHeight * 0.5f - bigButtonHeight * 0.5f;
		musicButton.setBounds(centerX, centerY + bigButtonHeight + margin, bigButtonHeight, bigButtonHeight);
		soundsButton.setBounds(centerX, centerY, bigButtonHeight, bigButtonHeight);
		exitButton.setBounds(centerX, centerY - bigButtonHeight - margin, bigButtonHeight, bigButtonHeight);
		result.addActor(musicButton);
		result.addActor(soundsButton);
		result.addActor(exitButton);
		// Hide the buttons initially
		result.toBack();
		result.addAction(Actions.fadeOut(0));
		musicButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (musicButton.isChecked()) {
					musicButton.setStyle(MainGame.skin.get("happiness", Button.ButtonStyle.class));
				} else {
					musicButton.setStyle(MainGame.skin.get("alt", Button.ButtonStyle.class));
				}
				MainGame.musicOn = musicButton.isChecked();
			}
		});
		soundsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (soundsButton.isChecked()) {
					soundsButton.setStyle(MainGame.skin.get("happiness", Button.ButtonStyle.class));
				} else {
					soundsButton.setStyle(MainGame.skin.get("alt", Button.ButtonStyle.class));
				}
				MainGame.soundsOn = soundsButton.isChecked();
			}
		});
		exitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("EXIT THE GAME");
			}
		});
		musicButton.setChecked(MainGame.musicOn);
		soundsButton.setChecked(MainGame.soundsOn);

		return result;
	}
	@Override
	public void dispose () {
		img.dispose();
		boxTexture.dispose();
		happy.dispose();
		settingsTexture.dispose();
	}
}