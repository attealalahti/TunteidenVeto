package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import static fi.tuni.tiko.MainGame.FADE_TIME;
import static fi.tuni.tiko.MainGame.colorMax255;
import static fi.tuni.tiko.MainGame.currentScreenID;
import static fi.tuni.tiko.MainGame.darkBackgroundColor;
import static fi.tuni.tiko.MainGame.desiredBackgroundColor;
import static fi.tuni.tiko.MainGame.lightBackgroundColor;
import static fi.tuni.tiko.MainGame.mainMenu;
import static fi.tuni.tiko.MainGame.margin;
import static fi.tuni.tiko.MainGame.meterHeight;
import static fi.tuni.tiko.MainGame.musicOn;
import static fi.tuni.tiko.MainGame.saveSettings;
import static fi.tuni.tiko.MainGame.screens;
import static fi.tuni.tiko.MainGame.skin;
import static fi.tuni.tiko.MainGame.soundOn;
import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.windowWidth;


public class GlobalElements extends Group {
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

    private float buttonHeight;
    private float bigButtonHeight;

    private Group meters;
    private Group settings;

    private Button musicButton;
    private Button soundButton;
    private Button exitButton;
    private Button settingsButton;
    private Button feelingMeterButton;

    public GlobalElements() {
        buttonHeight =  windowHeight * 0.07f;
        bigButtonHeight = buttonHeight * 2f;
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
                        screens.set(i, new MainMenuScreen());
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

    public FeelingMeter getMeter(String emotion) {
        FeelingMeter tempMeter = new FeelingMeter(5f, Color.BLACK, "Janika");
        switch(emotion) {
            case "love":
                tempMeter = love;
                break;

            case "happiness":
                tempMeter =  happiness;
            break;

            case "anger":
                tempMeter = anger;
            break;

            case "astonishment":
                tempMeter = astonishment;
            break;

            case "fear":
                tempMeter = fear;
            break;

            case "disgust":
                tempMeter = disgust;
            break;

            case "sadness":
                tempMeter = sadness;
            break;
        }
        return tempMeter;
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
}
