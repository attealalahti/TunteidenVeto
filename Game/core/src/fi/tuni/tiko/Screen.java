package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/** Screen is an abstract class that is used to create different screen layouts for the game.
 * Every screen has a screen ID and some choices that can move the game to a different screen.
 * @author Atte Ala-Lahti
 */
abstract public class Screen extends Stage {

    private int screenID;
    private ArrayList<String> temporaryList;
    private ArrayList<Integer> screenLinks;
    private Group elements = new Group();

    /** Creates a new screen.
     *
     * @param screenID a unique integer used in MainGame to determine which screen to show
     * @param choices text for each of the choices
     * @param screenLinks screen IDs for screens the choices lead to. Must be the same size as choices
     */
    public Screen (int screenID, ArrayList<String> choices, ArrayList<Integer> screenLinks) {
        this.screenID = screenID;
        this.temporaryList = choices;
        this.screenLinks = screenLinks;
        addActor(elements);
        if (choices.size() != screenLinks.size()) {
            throw new IllegalArgumentException("Choice and screen link amounts don't match.");
        }
    }
    public Screen() {
        this.screenID = 999;
        this.temporaryList = createAnswers();
        this.screenLinks = createScreenLinks();
        addActor(elements);
    }
    public ArrayList<String> createAnswers() {
        ArrayList<String> temporaryList = new ArrayList<>();
        temporaryList.add("POISTU PELISTÄ");
        temporaryList.add("ASETUKSET");
        temporaryList.add("JATKA PELIÄ");
        temporaryList.add("UUSI PELI");
        return temporaryList;
    }
    public ArrayList<Integer> createScreenLinks() {
        ArrayList<Integer> menuScreenLinks = new ArrayList<Integer>();
        menuScreenLinks.add(4);
        menuScreenLinks.add(3);
        menuScreenLinks.add(2);
        menuScreenLinks.add(1);
        return menuScreenLinks;
    }


    public int getScreenID() {
        return screenID;
    }
    public ArrayList<String> getChoices() {
        return temporaryList;
    }
    public ArrayList<Integer> getScreenLinks() {
        return screenLinks;
    }
    /** Called by an AnswerBox when it is selected and confirmed.
     *
     * @param screenLink The ID number of the next screen.
     */
    public void nextScreen(int screenLink) {
        MainGame.currentScreenID = screenLink;
    }

    /** This method creates an array of screens containing longer story texts.
     *
     * This method takes in the story text, divides it into different screens for easier readibility.
     * @param lengthOfMaxPerScreen is the float given to determine the length of the story text.
     * @param storyText containing the story text that will be split into separate parts
     * @return an array of strings
     * @author Mika Kivennenä
     */
    public String[] createStoryScreens(float lengthOfMaxPerScreen, String storyText) {
        int amountOfScreens = (int)(Math.ceil(storyText.length() / lengthOfMaxPerScreen));
        int characterIndex = 0;
        int screenIndex = 0;
        String[] stringArr = new String[amountOfScreens];

        // This loop runs until the current character is the last character of the text.
        while(characterIndex < storyText.length()) {
            String tempString = "";
            // Saves the story text one character at a time into a temporary string that is later added to the story array.
            for(int i = 0; i<lengthOfMaxPerScreen; i++) {
                tempString += storyText.charAt(characterIndex);
                characterIndex++;
            }
            // Adds the tempString into the string array of the current screen index. Increases screen index by one.
            stringArr[screenIndex] = tempString;
            screenIndex++;
        }
        return stringArr;
    }

    /**
     *
     * @return a group that should contain all visual elements of the screen
     */
    public Group getElements() {
        return elements;
    }
}
