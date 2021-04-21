package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import static fi.tuni.tiko.MainGame.margin;
import static fi.tuni.tiko.MainGame.skin;
import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.windowWidth;
import static fi.tuni.tiko.MainGame.mainMenuChecker;

public class MainMenuScreen extends Screen {

    private Group answerBoxes;
    private final float boxWidth = windowWidth * 0.9f;
    private final float boxHeight = windowHeight * 0.1f;
    private final float buttonHeight = windowHeight * 0.07f;
    private final float xBox = (windowWidth - boxWidth) / 2f;
    private final float questionBoxHeight = windowHeight * 0.4f;
    private final float roomForAnswers = windowHeight - questionBoxHeight - margin * 2f - buttonHeight;


    public MainMenuScreen() {
        createAnswerBoxes();
    }

    public void createAnswerBoxes() {
        answerBoxes = new Group();
        float currentY = margin + buttonHeight + roomForAnswers / 4 - boxHeight * 0.5f;

        for (int i = 0; i < 4; i++) {
            answerBoxes.addActor(new AnswerBoxMovable(getChoices().get(i), xBox, currentY, boxWidth, boxHeight, getScreenLinks().get(i)));
            currentY += roomForAnswers / 4;
        }
        for(int i = 0; i < answerBoxes.getChildren().size; i++) {
            ((AnswerBox) answerBoxes.getChild(i)).setBackground("menuBox");
            ((AnswerBox) answerBoxes.getChild(i)).setTextStyle("menuBoxText");
            ((AnswerBoxMovable) answerBoxes.getChild(i)).setConfirmation(false);
        }
        getElements().addActor(answerBoxes);
    }

   @Override
    public void nextScreen(int screenLink) {
        mainMenuChecker = screenLink;
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

