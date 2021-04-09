package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import java.util.ArrayList;

import static fi.tuni.tiko.MainGame.margin;
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

    private Group answerBoxes;
    private ArrayList<String> answerEffects;
    private final float boxWidth = windowWidth * 0.9f;
    private final float boxHeight = windowHeight * 0.1f;
    private final float buttonHeight = windowHeight * 0.07f;
    private final float xBox = (windowWidth - boxWidth) / 2f;
    private final int questionSizeThreshold = 200;
    private float roomForAnswers;
    private String question;

    /** Creates a new ChoiceScreen.
     *
     * @param screenID a unique integer used in MainGame to determine which screen to show
     * @param question text for the big text box
     * @param answers text for each of the choices
     * @param screenLinks screen IDs for screens the choices lead to. Must be the same size as choices
     */
    public ChoiceScreen(int screenID, String question, final ArrayList<String> answers, ArrayList<Integer> screenLinks, ArrayList<String> answerEffects) {
        super(screenID, answers, screenLinks);
        this.question = question;
        this.answerEffects = answerEffects;

        float questionBoxHeight = windowHeight * 0.4f;
        String questionBoxStyle = "question";
        if (question.length() > questionSizeThreshold && answers.size() == 1 && answerEffects.size() == 0) {
            questionBoxHeight = windowHeight * 0.6f;
            questionBoxStyle = "bigQuestion";
        }
        roomForAnswers = windowHeight - questionBoxHeight - margin * 2f - buttonHeight;

        createAnswerBoxes();

        // Create the question text box
        float roomLeft = windowHeight - (answers.size() * (boxHeight + margin) + margin + buttonHeight + margin);
        Label questionBackground = new Label(null, skin, questionBoxStyle);
        //questionBackground.setBounds(xBox, windowHeight - roomLeft, boxWidth, roomLeft - margin);
        questionBackground.setBounds(xBox, windowHeight - questionBoxHeight - margin, boxWidth, questionBoxHeight);


        Label questionText = new Label(question, skin, "text");
        questionText.setBounds(
                questionBackground.getX() + questionBackground.getWidth() * 0.04f,
                questionBackground.getY() + questionBackground.getHeight() * 0.08f,
                questionBackground.getWidth() * 0.9f,
                questionBackground.getHeight() * 0.88f
        );
        questionText.setAlignment(0);
        questionText.setFontScaleX(0.00045f * windowWidth);
        questionText.setFontScaleY(0.00025f * windowHeight);
        questionText.setWrap(true);

        Group questionBox = new Group();
        questionBox.addActor(questionBackground);
        questionBox.addActor(questionText);
        getElements().addActor(questionBox);
    }
    public void createAnswerBoxes() {
        answerBoxes = new Group();
        if (answerEffects.size() == 0) {
            //float currentY = margin * 2 + buttonHeight;
            float currentY = margin + buttonHeight + roomForAnswers / (float) (getChoices().size()+1) - boxHeight * 0.5f;
            for (int i = 0; i < getChoices().size(); i++) {
                answerBoxes.addActor(new AnswerBoxMovable(getChoices().get(i), xBox, currentY, boxWidth, boxHeight, getScreenLinks().get(i)));
                //currentY += margin + boxHeight;
                currentY += roomForAnswers / (float) (getChoices().size()+1);
            }
        } else {
            //float currentY = margin * 2 + buttonHeight;
            float currentY = margin + buttonHeight + roomForAnswers / (float) (getChoices().size()+2) - boxHeight * 0.5f;
            AnswerBoxMovable arrowBox = new AnswerBoxMovable(null, xBox, currentY, boxWidth, boxHeight, getScreenLinks().get(0));
            arrowBox.getBackground().setStyle(skin.get("arrow", Label.LabelStyle.class));
            answerBoxes.addActor(arrowBox);
            currentY += roomForAnswers / (float) (getChoices().size()+2);
            getElements().addActor(new AnswerBox(getChoices().get(0), xBox, currentY, boxWidth, boxHeight));
        }
        getElements().addActor(answerBoxes);
    }

    public void answersSetPause(boolean pause) {
        for (Actor a: answerBoxes.getChildren()) {
            AnswerBoxMovable ab = (AnswerBoxMovable) a;
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
        for (Actor a: answerBoxes.getChildren()) {
            ((AnswerBoxMovable) a).addRail();
        }
    }
    public ArrayList<String> getEffects() {
        return answerEffects;
    }

    public String getQuestion() {
        return question;
    }
}
