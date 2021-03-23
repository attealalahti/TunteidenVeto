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
import static fi.tuni.tiko.MainGame.skin;

/** ChoiceScreen is a Screen that has a big text box at the top of the screen and some AnswerBoxes below it.
 * There is also a button at the bottom to switch to FeelingMeter viewing mode and back.
 * 7 (currently mock-up) FeelingMeters are created at the start and then shown in FeelingMeter viewing mode.
 * All sizes are predetermined based on the device's screen size.
 * @author Atte Ala-Lahti
 */
public class ChoiceScreen extends Screen {

    private Group game;
    private Group answerBoxes;
    private final float boxWidth = windowWidth * 0.9f;
    private final float boxHeight = windowHeight * 0.1f;
    private final float buttonHeight = windowHeight * 0.07f;
    // How much space is in between elements:
    private final float margin = windowHeight * 0.025f;
    private final float xBox = (windowWidth - boxWidth) / 2f;

    /** Creates a new ChoiceScreen.
     *
     * @param screenID a unique integer used in MainGame to determine which screen to show
     * @param question text for the big text box
     * @param answers text for each of the choices
     * @param screenLinks screen IDs for screens the choices lead to. Must be the same size as choices
     */
    public ChoiceScreen(int screenID, String question, final ArrayList<String> answers, ArrayList<Integer> screenLinks) {
        super(screenID, answers, screenLinks);

        // Create groups for easy access of different elements
        game = new Group();
        addActor(game);

        createAnswerBoxes();

        // Create the question text box
        float roomLeft = windowHeight - (answers.size() * (boxHeight + margin) + margin + buttonHeight + margin);
        Label questionBackground = new Label(null, skin, "question");
        questionBackground.setBounds(xBox, windowHeight - roomLeft, boxWidth, roomLeft - margin);

        Label questionText = new Label(question, skin, "text");
        questionText.setBounds(
                questionBackground.getX() + questionBackground.getWidth() * 0.04f,
                questionBackground.getY() + questionBackground.getHeight() * 0.08f,
                questionBackground.getWidth() * 0.9f,
                questionBackground.getHeight() * 0.88f
        );
        questionText.setAlignment(0);
        questionText.setFontScaleX(0.00045f * windowWidth);
        questionText.setFontScaleY(0.00027f * windowHeight);
        questionText.setWrap(true);

        Group questionBox = new Group();
        questionBox.addActor(questionBackground);
        questionBox.addActor(questionText);
        game.addActor(questionBox);

    }
    public void createAnswerBoxes() {
        answerBoxes = new Group();
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
    public void addGlobalElements(Button feelingMeterButton, Group meters, Button settingsButton, Group settings) {
        addActor(feelingMeterButton);
        addActor(meters);
        addActor(settingsButton);
        addActor(settings);
        feelingMeterButton.toBack();
        meters.toBack();
        settings.toBack();
    }
    public Group getGameElements() {
        return game;
    }
}
