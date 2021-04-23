package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;

import static fi.tuni.tiko.MainGame.currentScreenID;
import static fi.tuni.tiko.MainGame.loadProgress;
import static fi.tuni.tiko.MainGame.margin;
import static fi.tuni.tiko.MainGame.resetProgress;
import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.windowWidth;
import static fi.tuni.tiko.MainGame.globalElements;

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
       switch (screenLink) {
           case 1:
               resetProgress();
               loadProgress();
               currentScreenID = 266;
               break;
           case 2:
               loadProgress();
               break;
           case 3:
               globalElements.hideScreenElements(this);
               globalElements.showSettings();
               currentScreenID = 10000;
               break;
           case 4:
               Gdx.app.exit();
               break;
       }
    }
}