package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.windowWidth;

public class ChoiceScreen extends Stage {

    private Skin skin;
    private String question;
    private ArrayList<String> answers;
    private final float boxWidth = windowWidth * 0.9f;
    private final float boxHeight = windowHeight * 0.1f;
    private final float boxMargin = windowHeight * 0.05f;
    private final float xBox = (windowWidth - boxWidth) / 2f;

    public ChoiceScreen(Skin skin, String question, ArrayList<String> answers) {
        this.skin = skin;
        this.question = question;
        this.answers = answers;

        createAnswerBoxes();

        float roomLeft = windowHeight - (answers.size() * (boxHeight + boxMargin) + boxMargin);
        Label questionBox = new Label(question, skin, "question");
        questionBox.setBounds(xBox, windowHeight - roomLeft, boxWidth, roomLeft - boxMargin);
        questionBox.setAlignment(0);
        questionBox.setFontScale(3);
        addActor(questionBox);
    }
    public void createAnswerBoxes() {
        float currentY = boxMargin;
        for (String a: answers) {
            addActor(new AnswerBox(a, skin, xBox, currentY, boxWidth, boxHeight));
            currentY += boxMargin + boxHeight;
        }
    }
}
