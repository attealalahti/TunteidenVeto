package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static fi.tuni.tiko.MainGame.audioPlayer;
import static fi.tuni.tiko.MainGame.currentScreenID;
import static fi.tuni.tiko.MainGame.darkBackgroundColor;
import static fi.tuni.tiko.MainGame.desiredBackgroundColor;
import static fi.tuni.tiko.MainGame.lightBackgroundColor;
import static fi.tuni.tiko.MainGame.mainMenuScreenID;
import static fi.tuni.tiko.MainGame.margin;
import static fi.tuni.tiko.MainGame.meterHeight;
import static fi.tuni.tiko.SaveHandler.*;
import static fi.tuni.tiko.MainGame.skin;
import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.windowWidth;
import static fi.tuni.tiko.Utility.colorMax255;
import static fi.tuni.tiko.Utility.getLocalization;

/** The GlobalElements group hold all elements needed in all screens of the game.
 *
 * The elements are added to each screen as they become active.
 *
 * @author Atte Ala-Lahti
 */
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
    private ArrayList<Actor> initiallyHiddenElements;

    private Button musicButton;
    private Button soundButton;
    private Button exitButton;
    private Button settingsButton;
    private Button feelingMeterButton;
    private String [] emotions;

    // How long it takes to switch between Game and FeelingMeter mode:
    public final float FADE_TIME = 0.2f;

    /** Creates all global elements.
     *
     * Indicators for different emotions are created. These are used when reading from a file.
     * Astonishment is specified on it's own because the properties file doesn't know Ä.
     * Initially hidden elements are added to a list and made invisible briefly at first as the game loads.
     */
    public GlobalElements() {
        buttonHeight =  windowHeight * 0.07f;
        bigButtonHeight = buttonHeight * 2f;

        emotions = new String[] {
                getLocalization("happiness").toUpperCase(),
                getLocalization("sadness").toUpperCase(),
                getLocalization("anger").toUpperCase(),
                getLocalization("love").toUpperCase(),
                getLocalization("fear").toUpperCase(),
                getLocalization("astonishment").toUpperCase(),
                getLocalization("disgust").toUpperCase()};
        if (getLocalization("language").equals("fi")) {
            emotions[5] = "HÄMMENNYS";
        }

        meters = createMeters();
        settings = createSettings();
        settingsButton = createSettingsButton();
        feelingMeterButton = createFeelingMeterButton();
        addActor(meters);
        addActor(settings);
        addActor(settingsButton);
        addActor(feelingMeterButton);

        initiallyHiddenElements = new ArrayList<>();
        initiallyHiddenElements.add(meters);
        initiallyHiddenElements.add(settings);

        hideBackgroundElementsWhileLoading();
    }

    /** Elements in the initially hidden elements list are made invisible and returned to visible 0.1 seconds later.
     *
     * This is to avoid blinking over the other elements for a few frames when first loading.
     */
    public void hideBackgroundElementsWhileLoading() {
        for (final Actor a: initiallyHiddenElements) {
            a.setVisible(false);
            a.addAction(sequence(moveTo(a.getX(), a.getY(), 0.1f), run(new Runnable() {
                @Override
                public void run() {
                    a.setVisible(true);
                }
            })));
        }
    }

    /** Returns all of the feeling meters as a group.
     *
     * @return group of feeling meters
     */
    public Group getMeters() {
        return meters;
    }

    /** Returns all of the settings buttons as a group.
     *
     * @return group of settings buttons
     */
    public Group getSettings() {
        return settings;
    }

    /** Returns the button to show settings.
     *
     * @return button to show settings
     */
    public Button getSettingsButton() {
        return settingsButton;
    }

    /** Returns the button to show feeling meters.
     *
     * @return button to show feeling meters
     */
    public Button getFeelingMeterButton() {
        return feelingMeterButton;
    }

    /** Returns the button controlling music.
     *
     * @return button controlling music
     */
    public Button getMusicButton() {
        return musicButton;
    }

    /** Returns the button controlling sounds.
     *
     * @return button controlling sounds.
     */
    public Button getSoundButton() {
        return soundButton;
    }

    /** Creates a feeling meter for each of the seven emotions.
     *
     * The meters are hidden initially.
     *
     * @return all meters in a group
     */
    public Group createMeters() {
        Group result = new Group();
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

    /** Updates the feeling meters based on the effects given.
     *
     * The effects come from a screen and have three first letters of the emotion in question as indicators followed by how much that emotion should change.
     *
     * @param effects effects on feeling meters
     */
    public void updateMeters(ArrayList<String> effects) {
        if (effects.size() > 0) {
            audioPlayer.playEmotionSound();
        }
        for (String effect: effects) {
            String indicator = "" + effect.charAt(0) + effect.charAt(1) + effect.charAt(2);
            StringBuilder value = new StringBuilder();
            for (int i = 3; i < effect.length(); i++) {
                value.append(effect.charAt(i));
            }
            int change = Integer.parseInt(value.toString());
            String hap = emotions[0].substring(0, 3);
            String sad = emotions[1].substring(0, 3);
            String ang = emotions[2].substring(0, 3);
            String lov = emotions[3].substring(0, 3);
            String fea = emotions[4].substring(0, 3);
            String ast = emotions[5].substring(0, 3);
            String dis = emotions[6].substring(0, 3);
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

    /** Returns the emotion with the most full meter.
     *
     * @return emotion with the most full meter
     */
    public String getStrongestEmotion() {
        String result = "happiness";
        float largestValue = Math.max(Math.max(Math.max(happiness.getValue(), disgust.getValue()), Math.max(anger.getValue(), fear.getValue())), Math.max(Math.max(sadness.getValue(), astonishment.getValue()), love.getValue()));
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

    /** Returns the list of all emotions.
     *
     * @return list of all emotions in English
     */
    public String [] getEmotions() {
        return emotions;
    }

    /** Creates the button to show the feeling meters.
     *
     * Turns off if the settings button is pressed.
     *
     * @return button to show feeling meters
     */
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

    /** Creates the button to show settings.
     *
     * Turns off if the feeling meter button is pressed.
     *
     * @return button to show settings
     */
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

    /** Creates the buttons that control music and sound.
     *
     * @return group of buttons that control music and sound
     */
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
                audioPlayer.music(musicButton.isChecked());
                saveSettings();
            }
        });
        soundButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                audioPlayer.sound(soundButton.isChecked());
                saveSettings();
            }
        });
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentScreenID = mainMenuScreenID;
                settingsButton.setChecked(false);
                hideSettings();
                desiredBackgroundColor = lightBackgroundColor;
            }
        });
        return result;
    }

    /** Returns a specific feeling meter based on the name given.
     *
     * @param emotion name of the meter
     * @return the meter
     */
    public FeelingMeter getMeter(String emotion) {
        FeelingMeter tempMeter = new FeelingMeter(5f, Color.BLACK, "sadness");
        switch(emotion) {
            case "love": tempMeter = love; break;
            case "happiness": tempMeter =  happiness; break;
            case "anger": tempMeter = anger; break;
            case "astonishment": tempMeter = astonishment; break;
            case "fear": tempMeter = fear; break;
            case "disgust": tempMeter = disgust; break;
            case "sadness": tempMeter = sadness; break;
        }
        return tempMeter;
    }

    /** Fades out all meters.
     *
     * Also makes them the back most item of their screen.
     */
    public void hideMeters() {
        meters.addAction(Actions.fadeOut(FADE_TIME));
        meters.toBack();
    }

    /** Fades in all meters.
     *
     * Also makes them the fore most item of their screen.
     */
    public void showMeters() {
        meters.addAction(Actions.fadeIn(FADE_TIME));
        meters.toFront();
        desiredBackgroundColor = darkBackgroundColor;
    }
    /** Fades out all settings.
     *
     * Also makes them the back most item of their screen.
     */
    public void showSettings() {
        settings.addAction(Actions.fadeIn(FADE_TIME));
        settings.toFront();
        desiredBackgroundColor = darkBackgroundColor;
    }

    /** Fades in all settings.
     *
     * Also makes them the fore most item of their screen.
     */
    public void hideSettings() {
        settings.addAction(Actions.fadeOut(FADE_TIME));
        settings.toBack();
    }

    /** Fades in all elements from the given screen's elements group.
     *
     * Unpauses the answers on the screen and makes the background the light variant.
     * Also makes them the fore most item of their screen.
     *
     * @param thisScreen the screen to show the elements in
     */
    public void showScreenElements(Screen thisScreen) {
        thisScreen.getElements().addAction(Actions.fadeIn(FADE_TIME));
        if (thisScreen.getClass() == ChoiceScreen.class) {
            ((ChoiceScreen) thisScreen).answersSetPause(false);
        }
        thisScreen.getElements().toFront();
        desiredBackgroundColor = lightBackgroundColor;
    }

    /** Fades out all elements from the given screen's elements group.
     *
     * Pauses the answers on the screen.
     * Also makes them the back most item of their screen.
     *
     * @param thisScreen the screen to hide the elements in
     */
    public void hideScreenElements(Screen thisScreen) {
        thisScreen.getElements().addAction(Actions.fadeOut(FADE_TIME));
        if (thisScreen.getClass() == ChoiceScreen.class) {
            ((ChoiceScreen) thisScreen).answersSetPause(true);
        }
        thisScreen.getElements().toBack();
    }
}
