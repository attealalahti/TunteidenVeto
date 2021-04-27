package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.awt.Color;

import static fi.tuni.tiko.MainGame.continueUnlocked;
import static fi.tuni.tiko.MainGame.currentScreenID;
import static fi.tuni.tiko.MainGame.margin;
import static fi.tuni.tiko.MainGame.skin;
import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.globalElements;
import static fi.tuni.tiko.SaveHandler.*;

/** The MainMenuScreen class is a screen shown as the main menu.
 *
 * It has predetermined answers at the bottom and an image of the game's title at the top of the screen.
 * None of its answers need confirmation.
 * It has more complicated effects for answers being chosen than just moving to a new screen.
 *
 * @author Janika Kupila
 */
public class MainMenuScreen extends Screen {

    private float startY = windowHeight * 0.1f;
    private float titleHeight = getBoxWidth() * 0.547f;

    /** Creates the MainMenuScreen.
     *
     * The game's title's size determined based on how much room the answers take up.
     */
    public MainMenuScreen() {
        createAnswerBoxes();
        float roomLeft = windowHeight - startY - getChoices().size() * (getBoxHeight() + margin) + margin;
        Image title = new Image(skin, "title");
        title.setBounds(getBoxX(), windowHeight - roomLeft * 0.5f - titleHeight * 0.5f, getBoxWidth(), titleHeight);
        getElements().addActor(title);
    }

    /** Creates answer boxes.
     *
     * They begin from a fixed height.
     */
    public void createAnswerBoxes() {
        Group answerBoxes = new Group();
        getElements().addActor(answerBoxes);
        float currentY = startY;
        for (int i = 0; i < getChoices().size(); i++) {
            AnswerBoxMovable tempAnswerBox = new AnswerBoxMovable(getChoices().get(i), getBoxX(), currentY, getBoxWidth(), getBoxHeight(), getScreenLinks().get(i));
            answerBoxes.addActor(tempAnswerBox);
            tempAnswerBox.setBackground("menuBox");
            tempAnswerBox.setTextStyle("menuBoxText");
            tempAnswerBox.setConfirmationNeed(false);
            tempAnswerBox.addRail();
            if (i == 2 && !continueUnlocked) {
                answerBoxes.removeActor(tempAnswerBox);
                AnswerBox immobileBox = new AnswerBox(getChoices().get(i), getBoxX(), currentY, getBoxWidth(), getBoxHeight());
                immobileBox.setBackground("inactiveBox");
                immobileBox.setTextStyle("menuBoxText");
                getElements().addActor(immobileBox);
            }
            currentY += margin + getBoxHeight();
        }
    }

    /** What happens when one of the answers is chosen.
     *
     * The choices are loading a new game, continuing an old save, opening settings and closing the game.
     *
     * @param screenLink The ID number that determines which choice has been chosen.
     */
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