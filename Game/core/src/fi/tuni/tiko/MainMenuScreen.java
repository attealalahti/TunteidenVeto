package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;

import static fi.tuni.tiko.MainGame.margin;
import static fi.tuni.tiko.MainGame.skin;
import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.windowWidth;

public class MainMenuScreen extends Screen {

    private Group elements;
    private Group answerBoxes;
    private ArrayList<String> answerEffects;
    private final float boxWidth = windowWidth * 0.9f;
    private final float boxHeight = windowHeight * 0.1f;
    private final float buttonHeight = windowHeight * 0.07f;
    private final float xBox = (windowWidth - boxWidth) / 2f;
    private final float questionBoxHeight = windowHeight * 0.4f;
    private final float roomForAnswers = windowHeight - questionBoxHeight - margin * 2f - buttonHeight;
    private Texture img;
    public static int checker = 0;

    public MainMenuScreen(int screenID, final ArrayList<String> answers, ArrayList<Integer> screenLinks) {
        super(screenID, answers, screenLinks);

        // Create groups for easy access of different elements
        elements = new Group();
        addActor(elements);

        createAnswerBoxes();
    }

    public void createAnswerBoxes() {
        answerBoxes = new Group();
        //float currentY = margin * 2 + buttonHeight;
        float currentY = margin + buttonHeight + roomForAnswers / 4 - boxHeight * 0.5f;
        for (int i = 0; i < 4; i++) {
            answerBoxes.addActor(new AnswerBoxMovable(getChoices().get(i), xBox, currentY, boxWidth, boxHeight, getScreenLinks().get(i)));
            //currentY += margin + boxHeight;
            currentY += roomForAnswers / 4;
        }
        elements.addActor(answerBoxes);
    }

    public Group getGameElements() {
        return elements;
    }

   @Override
    public void nextScreen(int screenLink) {
        checker = screenLink;
    }

    public static int getChecker() {
        return checker;
    }
}


/** @Override
public void show() {

}

 @Override
 public void render(float delta) {

 }

 @Override
 public void resize(int width, int height) {

 }

 @Override
 public void pause() {

 }

 @Override
 public void resume() {

 }

 @Override
 public void hide() {

 }

 @Override
 public void dispose() {

 } **/

