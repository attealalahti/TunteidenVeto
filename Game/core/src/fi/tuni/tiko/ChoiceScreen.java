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

/** ChoiceScreen is the Screen type used for most of the game.
 *
 * It has the day of the week written at the top. Below that is a text box for advancing the story.
 * Below that are the answer boxes that lead to different screens based on their screen links.
 * Below those is room for the feeling meter and settings buttons. They are added to the screen when it becomes active.
 * Alternatively the text box can be replaced by an image and a caption if the text for the text box ends with the path to the image in square brackets.
 *
 * @author Atte Ala-Lahti
 */
public class ChoiceScreen extends Screen {

    private Group answerBoxes;
    private Label dayBox;
    private ArrayList<String> answers;
    private ArrayList<String> answerEffects;
    private final float buttonHeight = windowHeight * 0.07f;
    private final float dayBoxHeight = windowHeight * 0.03f;
    private float captionHeight = windowHeight * 0.06f;
    private final int questionSizeThreshold = 200;
    private float roomForAnswers = windowHeight - dayBoxHeight - margin - buttonHeight;
    private String question;

    /** Creates a new ChoiceScreen.
     *
     * @param screenID a unique integer used in MainGame to determine which screen to show
     * @param question text for the text box
     * @param answers text for each of the choices
     * @param screenLinks screen IDs for screens the choices lead to. Must be the same size as choices
     * @param answerEffects the effects the choices will have on the feeling meters, made up of three letters identifying the emotion followed by how much that emotion should be changed
     */
    public ChoiceScreen(int screenID, String question, final ArrayList<String> answers, ArrayList<Integer> screenLinks, ArrayList<String> answerEffects) {
        super(screenID, answers, screenLinks);
        this.question = question;
        this.answers = answers;
        this.answerEffects = answerEffects;

        createDayText();
        if (!question.contains("[")) {
            createQuestionBox();
        } else {
            createImageWithCaption();
        }
        createAnswerBoxes();
    }

    /** Creates a label used to display the current day of the week.
     *
     * Initially has placeholder text that is replaced when the screen becomes active.
     */
    public void createDayText() {
        dayBox = new Label("dayOfTheWeek", skin, "dayText");
        dayBox.setBounds(getBoxX(), windowHeight - margin - dayBoxHeight, getBoxWidth(), dayBoxHeight);
        dayBox.setAlignment(0);
        dayBox.setWrap(true);
        addActor(dayBox);
        getElements().addActor(dayBox);
    }

    /** Creates a question box with text that is used to advance the story and possibly ask for a decision from the player.
     *
     * The question box is a group made of a separate label and image to ensure the text keeps within the image.
     * The box can be three different sizes based on the length of the text to be put in it.
     * Removes the amount of space it take up from the roomForAnswers variable used to determine the positions of the answer boxes.
     */
    public void createQuestionBox() {
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

        Image questionBackground = new Image(skin, questionBoxStyle);
        questionBackground.setBounds(getBoxX(), windowHeight - dayBoxHeight - questionBoxHeight - margin * 2f, getBoxWidth(), questionBoxHeight);

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
    }

    /** Creates an image and a caption below it based on the question text.
     *
     * The image is chosen based on the path specified with the square brackets at the end of the text.
     * If no caption is provided, the height of the caption label is reduced to zero.
     * Removes the amount of space the image and caption take up from the roomForAnswers variable used to determine the positions of the answer boxes.
     */
    public void createImageWithCaption() {
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
        float imageHeight = windowHeight - margin - dayBoxHeight - margin - margin - captionHeight - buttonHeight - answers.size()*(margin + getBoxHeight());
        if (imageHeight > windowHeight * 0.5f) {
            imageHeight = windowHeight * 0.5f;
        }
        float imageWidth = imageHeight * 0.5f;
        image.setBounds((windowWidth - imageWidth) * 0.5f, windowHeight - dayBoxHeight - margin * 2f - imageHeight, imageWidth, imageHeight);

        Label caption = new Label(captionText.toString(), skin, "imageCaptionText");
        caption.setBounds(getBoxX(), image.getY() - margin - captionHeight, getBoxWidth(), getBoxHeight());
        caption.setAlignment(0);
        caption.setWrap(true);

        Group bodyImage = new Group();
        bodyImage.addActor(image);
        bodyImage.addActor(caption);
        getElements().addActor(bodyImage);

        roomForAnswers -= imageHeight + captionHeight;
    }

    /** Creates a group of answer boxes based on the choice texts and screen links.
     *
     * If there are effects to the feeling meters, the answer box is made not movable and an additional answer box with an arrow is created below.
     * That arrow box is used to move to the next screen.
     */
    public void createAnswerBoxes() {
        answerBoxes = new Group();
        if (answerEffects.size() == 0) {
            float currentY = buttonHeight + roomForAnswers / (float) (getChoices().size()+1) - getBoxHeight() * 0.5f;
            for (int i = 0; i < getChoices().size(); i++) {
                answerBoxes.addActor(new AnswerBoxMovable(getChoices().get(i), getBoxX(), currentY, getBoxWidth(), getBoxHeight(), getScreenLinks().get(i)));
                currentY += roomForAnswers / (float) (getChoices().size()+1);
            }
        } else {
            float currentY = buttonHeight + roomForAnswers / (float) (getChoices().size()+2) - getBoxHeight() * 0.5f;
            AnswerBoxMovable arrowBox = new AnswerBoxMovable("", getBoxX(), currentY, getBoxWidth(), getBoxHeight(), getScreenLinks().get(0));
            arrowBox.setBackground("arrowBox");
            answerBoxes.addActor(arrowBox);
            currentY += roomForAnswers / (float) (getChoices().size()+2);
            getElements().addActor(new AnswerBox(getChoices().get(0), getBoxX(), currentY, getBoxWidth(), getBoxHeight()));
        }
        getElements().addActor(answerBoxes);
    }

    /** Pauses or unpauses the answer boxes of the screen.
     *
     * When paused, the answer boxes cannot be moved.
     *
     * @param pause the pause value to set the answer boxes, true pauses, false unpauses
     */
    public void answersSetPause(boolean pause) {
        for (Actor a: answerBoxes.getChildren()) {
            AnswerBoxMovable ab = (AnswerBoxMovable) a;
            ab.setPause(pause);
        }
    }

    /** Called when the screen becomes active. Adds elements used for all screens.
     *
     * Also sets the day of the week and adds rails to movable answer boxes.
     *
     * @param globalElements all elements used in all screens
     * @param weekDay the day of the week to be displayed at the top of the screen
     */
    public void activate(GlobalElements globalElements, String weekDay) {
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
        globalElements.hideBackgroundElementsWhileLoading();
    }

    /** Returns the list of effects the choices will have on the feeling meters.
     *
     * The effects are expressed as the three first letters of the emotion followed by how much to change it.
     *
     * @return list of effects on feeling meters
     */
    public ArrayList<String> getEffects() {
        return answerEffects;
    }

    /** Returns the text used for the question box or the image with the caption.
     *
     * @return question box text
     */
    public String getQuestion() {
        return question;
    }
}
