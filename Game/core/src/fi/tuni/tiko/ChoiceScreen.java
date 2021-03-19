package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;

import javax.xml.stream.FactoryConfigurationError;

import sun.applet.Main;

import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.windowWidth;

/** ChoiceScreen is a Screen that has a big text box at the top of the screen and some AnswerBoxes below it.
 * There is also a button at the bottom to switch to FeelingMeter viewing mode and back.
 * 7 (currently mock-up) FeelingMeters are created at the start and then shown in FeelingMeter viewing mode.
 * All sizes are predetermined based on the device's screen size.
 * @author Atte Ala-Lahti
 */
public class ChoiceScreen extends Screen {

    private String question;
    private Group game;
    private Group meters;
    private Group answerBoxes;
    private Group settings;
    private final float boxWidth = windowWidth * 0.9f;
    private final float boxHeight = windowHeight * 0.1f;
    // How much space is in between elements:
    private final float margin = windowHeight * 0.025f;
    private final float xBox = (windowWidth - boxWidth) / 2f;
    private final float buttonHeight = windowHeight * 0.07f;
    private final float bigButtonHeight = buttonHeight * 2f;
    private final float meterWidth = windowWidth * 0.8f;
    private final float meterHeight = windowHeight * 0.1f;
    // How long it takes to switch between Game and FeelingMeter mode:
    private final float FADE_TIME = 0.2f;

    /** Creates a new ChoiceScreen.
     *
     * @param screenID a unique integer used in MainGame to determine which screen to show
     * @param question text for the big text box
     * @param answers text for each of the choices
     * @param screenLinks screen IDs for screens the choices lead to. Must be the same size as choices
     */
    public ChoiceScreen(int screenID, String question, final ArrayList<String> answers, ArrayList<Integer> screenLinks) {
        super(screenID, answers, screenLinks);
        this.question = question;

        // Create groups for easy access of different elements
        answerBoxes = new Group();
        game = new Group();
        meters = new Group();
        settings = new Group();
        addActor(game);
        addActor(meters);
        addActor(settings);

        createAnswerBoxes();

        // Create the big text box
        float roomLeft = windowHeight - (answers.size() * (boxHeight + margin) + margin + buttonHeight + margin);
        Label questionBox = new Label(question, MainGame.skin, "question");
        questionBox.setBounds(xBox, windowHeight - roomLeft, boxWidth, roomLeft - margin);
        questionBox.setAlignment(0);
        questionBox.setFontScaleX(0.005f * windowWidth);
        questionBox.setFontScaleY(0.003f * windowHeight);
        questionBox.setWrap(true);
        game.addActor(questionBox);

        // Create settings button
        final Button settingsButton = new Button(MainGame.skin, "settings");
        settingsButton.setBounds((windowWidth / 3f) * 2f - buttonHeight * 0.5f, margin, buttonHeight, buttonHeight);
        addActor(settingsButton);

        // Create buttons for settings
        final Button musicButton = new Button(MainGame.skin, "alt");
        final Button soundsButton = new Button(MainGame.skin, "alt");
        final Button exitButton = new Button(MainGame.skin, "alt");
        float centerX = windowWidth * 0.5f - bigButtonHeight * 0.5f;
        float centerY = windowHeight * 0.5f - bigButtonHeight * 0.5f;
        musicButton.setBounds(centerX, centerY + bigButtonHeight + margin, bigButtonHeight, bigButtonHeight);
        soundsButton.setBounds(centerX, centerY, bigButtonHeight, bigButtonHeight);
        exitButton.setBounds(centerX, centerY - bigButtonHeight - margin, bigButtonHeight, bigButtonHeight);
        settings.addActor(musicButton);
        settings.addActor(soundsButton);
        settings.addActor(exitButton);
        settings.toBack();
        settings.addAction(Actions.fadeOut(0));
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

            }
        });
        musicButton.setChecked(MainGame.musicOn);
        soundsButton.setChecked(MainGame.soundsOn);
        // MAKE IT SO SETTINGS AND FEELINGMETERS ARE NOT CREATED INDIVIDUALLY FOR EACH SCREEN

        // Create the FeelingMeter button
        final Button feelingMeterButton = new Button(MainGame.skin, "happiness");
        feelingMeterButton.setBounds((windowWidth / 3f) - buttonHeight * 0.5f, margin, buttonHeight, buttonHeight);
        addActor(feelingMeterButton);
        //feelingMeterButton.setStyle(MainGame.skin.get("alt", Button.ButtonStyle.class));

        // Create FeelingMeters
        float meterLocationHeight = meterHeight * 7 + margin * 7;
        float currentY = windowHeight - meterLocationHeight;
        for (int i = 0; i < 7; i++) {
            Label myLabel = new Label(i + "", MainGame.skin, "question");
            myLabel.setBounds(windowWidth * 0.5f - meterWidth * 0.5f, currentY, meterWidth, meterHeight);
            myLabel.setAlignment(0);
            myLabel.setFontScaleX(0.005f * windowWidth);
            myLabel.setFontScaleY(0.003f * windowHeight);
            meters.addActor(myLabel);
            currentY += margin + meterHeight;
        }
        // Hide the meters initially
        meters.toBack();
        meters.addAction(Actions.fadeOut(0));

        // Toggle between between the views
        feelingMeterButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!feelingMeterButton.isChecked()) {
                    meters.addAction(Actions.fadeOut(FADE_TIME));
                    meters.toBack();
                    if (!settingsButton.isChecked()) {
                        game.addAction(Actions.fadeIn(FADE_TIME));
                        answersSetPause(false);
                        game.toFront();
                    }
                } else {
                    meters.addAction(Actions.fadeIn(FADE_TIME));
                    meters.toFront();
                    game.addAction(Actions.fadeOut(FADE_TIME));
                    answersSetPause(true);
                    game.toBack();
                    if (settingsButton.isChecked()) {
                        settingsButton.setChecked(false);
                    }
                }
            }
        });
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!settingsButton.isChecked()) {
                    settings.addAction(Actions.fadeOut(FADE_TIME));
                    settings.toBack();
                    if (!feelingMeterButton.isChecked()) {
                        game.addAction(Actions.fadeIn(FADE_TIME));
                        answersSetPause(false);
                        game.toFront();
                    }
                } else {
                    settings.addAction(Actions.fadeIn(FADE_TIME));
                    settings.toFront();
                    game.addAction(Actions.fadeOut(FADE_TIME));
                    answersSetPause(true);
                    game.toBack();
                    if (feelingMeterButton.isChecked()) {
                        feelingMeterButton.setChecked(false);
                    }
                }
            }
        });
    }
    public void createAnswerBoxes() {
        float currentY = margin * 2 + buttonHeight;
        for (int i = 0; i < getChoices().size(); i++) {
            answerBoxes.addActor(new AnswerBox(getChoices().get(i), xBox, currentY, boxWidth, boxHeight, getScreenLinks().get(i)));
            currentY += margin + boxHeight;
        }
        game.addActor(answerBoxes);
    }

    public void answersSetPause(boolean pause) {
        for (Actor a: answerBoxes.getChildren()) {
            AnswerBox ab = (AnswerBox) a;
            ab.setPause(pause);
        }
    }
}
