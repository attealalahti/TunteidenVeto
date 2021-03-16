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
