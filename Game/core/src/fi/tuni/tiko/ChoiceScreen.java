package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    private Label dayBox;
    private ArrayList<String> answerEffects;
    private final float boxWidth = windowWidth * 0.9f;
    private final float boxHeight = windowHeight * 0.1f;
    private final float buttonHeight = windowHeight * 0.07f;
    private final float xBox = (windowWidth - boxWidth) * 0.5f;
    private final float dayBoxHeight = windowHeight * 0.03f;
    private float captionHeight = windowHeight * 0.06f;
    private final int questionSizeThreshold = 200;
    private float roomForAnswers = windowHeight - dayBoxHeight - margin - buttonHeight;
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

        dayBox = new Label("Viikonpäivä", skin, "dayText");
        dayBox.setBounds(xBox, windowHeight - margin - dayBoxHeight, boxWidth, dayBoxHeight);
        dayBox.setAlignment(0);
        dayBox.setWrap(true);
        addActor(dayBox);
        getElements().addActor(dayBox);


        if (!question.contains("[")) {
            float questionBoxHeight = windowHeight * 0.4f;
            String questionBoxStyle = "questionBox";
            if (question.length() > questionSizeThreshold && ((answers.size() == 2 && answerEffects.size() == 0) || (answers.size() == 1 && answerEffects.size() > 0))) {
                questionBoxHeight = windowHeight * 0.5f;
                questionBoxStyle = "bigQuestionBox";
            } else if (question.length() > questionSizeThreshold && answers.size() == 1 && answerEffects.size() == 0) {
                questionBoxHeight = windowHeight * 0.6f;
                questionBoxStyle = "biggerQuestionBox";
            }
            roomForAnswers -= questionBoxHeight;

            // Create the question text box
            Image questionBackground = new Image(skin, questionBoxStyle);
            questionBackground.setBounds(xBox, windowHeight - dayBoxHeight - questionBoxHeight - margin * 2f, boxWidth, questionBoxHeight);

            Label questionText = new Label(question, skin, "questionBoxText");
            questionText.setBounds(
                    questionBackground.getX() + questionBackground.getWidth() * 0.04f,
                    questionBackground.getY() + questionBackground.getHeight() * 0.08f,
                    questionBackground.getWidth() * 0.9f,
                    questionBackground.getHeight() * 0.88f
            );
            questionText.setAlignment(0);
            questionText.setWrap(true);

            Group questionBox = new Group();
            questionBox.addActor(questionBackground);
            questionBox.addActor(questionText);
            getElements().addActor(questionBox);
        } else {
            boolean stop = false;
            int index = 0;
            for (int i = question.length()-1; i > 0 && !stop ; i--) {
                if (question.charAt(i) == '[') {
                    index = i;
                    stop = true;
                }
            }
            StringBuilder style = new StringBuilder();
            for (int i = index+1; i < question.length()-1; i++) {
                style.append(question.charAt(i));
            }
            StringBuilder captionText = new StringBuilder();
            for (int i = 0; i < index-1; i++) {
                captionText.append(question.charAt(i));
            }
            if (captionText.toString().equals("")) {
                captionHeight = 0;
            }

            Image image = new Image(skin, style.toString());
            float imageHeight = windowHeight - margin - dayBoxHeight - margin - margin - captionHeight - buttonHeight - answers.size()*(margin + boxHeight);
            if (imageHeight > windowHeight * 0.5f) {
                imageHeight = windowHeight * 0.5f;
            }
            float imageWidth = imageHeight * 0.5f;
            image.setBounds((windowWidth - imageWidth) * 0.5f, windowHeight - dayBoxHeight - margin * 2f - imageHeight, imageWidth, imageHeight);

            Label caption = new Label(captionText.toString(), skin, "imageCaptionText");
            caption.setBounds(xBox, image.getY() - margin - captionHeight, boxWidth, boxHeight);
            caption.setAlignment(0);
            caption.setWrap(true);

            Group bodyImage = new Group();
            bodyImage.addActor(image);
            bodyImage.addActor(caption);
            getElements().addActor(bodyImage);

            roomForAnswers -= imageHeight + captionHeight;
        }

        createAnswerBoxes();
    }
    public void createAnswerBoxes() {
        answerBoxes = new Group();
        if (answerEffects.size() == 0) {
            float currentY = buttonHeight + roomForAnswers / (float) (getChoices().size()+1) - boxHeight * 0.5f;
            for (int i = 0; i < getChoices().size(); i++) {
                answerBoxes.addActor(new AnswerBoxMovable(getChoices().get(i), xBox, currentY, boxWidth, boxHeight, getScreenLinks().get(i)));
                currentY += roomForAnswers / (float) (getChoices().size()+1);
            }
        } else {
            float currentY = buttonHeight + roomForAnswers / (float) (getChoices().size()+2) - boxHeight * 0.5f;
            AnswerBoxMovable arrowBox = new AnswerBoxMovable(null, xBox, currentY, boxWidth, boxHeight, getScreenLinks().get(0));
            arrowBox.setBackground("arrowBox");
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
    public void addGlobalElements(GlobalElements globalElements, String weekDay) {
        addActor(globalElements.getFeelingMeterButton());
        addActor(globalElements.getMeters());
        addActor(globalElements.getSettingsButton());
        addActor(globalElements.getSettings());
        globalElements.getFeelingMeterButton().toBack();
        globalElements.getMeters().toBack();
        globalElements.getSettings().toBack();

        dayBox.setText(weekDay.toUpperCase());

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
