package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

abstract public class Screen extends Stage {

    private int screenID;
    private Skin skin;
    private ArrayList<String> choices;
    private ArrayList<Integer> screenLinks;
    int nextScreensID = 0;

    /** Screen is an abstract class that is used to create different screen layouts for the game.
     * Every screen has a screen ID and some choices that moves the game to a different screen.
     *
     * @param screenID a unique integer used in MainGame to determine which screen to show
     * @param skin contains styles for all objects
     * @param choices text for each of the choices
     * @param screenLinks screen IDs for screens the choices lead to. Must be the same size as choices
     * @author Atte Ala-Lahti
     */
    public Screen (int screenID, Skin skin, ArrayList<String> choices, ArrayList<Integer> screenLinks) {
        this.screenID = screenID;
        this.skin = skin;
        this.choices = choices;
        this.screenLinks = screenLinks;
        if (choices.size() != screenLinks.size()) {
            throw new IllegalArgumentException("Choice and screen link amounts don't match.");
        }
    }

    public Skin getSkin() {
        return skin;
    }
    public ArrayList<String> getChoices() {
        return choices;
    }
    public ArrayList<Integer> getScreenLinks() {
        return screenLinks;
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
