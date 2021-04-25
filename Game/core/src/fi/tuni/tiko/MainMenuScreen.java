package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static fi.tuni.tiko.MainGame.currentScreenID;
import static fi.tuni.tiko.MainGame.margin;
import static fi.tuni.tiko.MainGame.skin;
import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.globalElements;
import static fi.tuni.tiko.SaveHandler.*;

public class MainMenuScreen extends Screen {

    private Group answerBoxes;
    private float startY = windowHeight * 0.1f;
    private float titleHeight = getBoxWidth() * 0.547f;

    public MainMenuScreen() {
        createAnswerBoxes();
        float roomLeft = windowHeight - startY - getChoices().size() * (getBoxHeight() + margin) + margin;
        Image title = new Image(skin, "title");
        title.setBounds(getBoxX(), windowHeight - roomLeft * 0.5f - titleHeight * 0.5f, getBoxWidth(), titleHeight);
        getElements().addActor(title);
    }

    public void createAnswerBoxes() {
        answerBoxes = new Group();
        getElements().addActor(answerBoxes);
        float currentY = startY;
        for (int i = 0; i < getChoices().size(); i++) {
            AnswerBoxMovable tempAnswerBox = new AnswerBoxMovable(getChoices().get(i), getBoxX(), currentY, getBoxWidth(), getBoxHeight(), getScreenLinks().get(i));
            answerBoxes.addActor(tempAnswerBox);
            tempAnswerBox.setBackground("menuBox");
            tempAnswerBox.setTextStyle("menuBoxText");
            tempAnswerBox.setConfirmationNeed(false);
            tempAnswerBox.addRail();
            currentY += margin + getBoxHeight();
        }
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