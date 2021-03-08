package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.windowWidth;

public class ChoiceScreen extends Stage {

    private int screenID;
    private Skin skin;
    private String question;
    private ArrayList<String> answers;
    private ArrayList<Integer> screenLinks;
    private final float boxWidth = windowWidth * 0.9f;
    private final float boxHeight = windowHeight * 0.1f;
    private final float boxMargin = windowHeight * 0.025f;
    private final float xBox = (windowWidth - boxWidth) / 2f;
    int nextScreensID = 0;

    public ChoiceScreen(int screenID, Skin skin, String question, ArrayList<String> answers, ArrayList<Integer> screenLinks) {
        this.screenID = screenID;
        this.skin = skin;
        this.question = question;
        this.answers = answers;
        this.screenLinks = screenLinks;

        createAnswerBoxes();

        float roomLeft = windowHeight - (answers.size() * (boxHeight + boxMargin) + boxMargin);
        Label questionBox = new Label(question, skin, "question");
        questionBox.setBounds(xBox, windowHeight - roomLeft, boxWidth, roomLeft - boxMargin);
        questionBox.setAlignment(0);
        questionBox.setFontScaleX(0.005f * windowWidth);
        questionBox.setFontScaleY(0.003f * windowHeight);
        questionBox.setWrap(true);
        addActor(questionBox);
    }
    public void createAnswerBoxes() {
        float currentY = boxMargin;
        for (int i = 0; i < answers.size(); i++) {
            addActor(new AnswerBox(answers.get(i), skin, xBox, currentY, boxWidth, boxHeight, screenLinks.get(i)));
            currentY += boxMargin + boxHeight;
        }
    }

    /** Happens when one of the answers is selected and confirmed.
     *
     * @param screenLink The ID number of the next screen.
     */
    public void nextScreen(int screenLink) {
        nextScreensID = screenLink;
        System.out.println("Link to this screen: " + screenLink);
    }

    public int getNextScreensID() {
        return nextScreensID;
    }
}
