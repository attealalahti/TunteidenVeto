package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/** Screen is an abstract class that is used to create different screen layouts for the game.
 * Every screen has a screen ID and some choices that can move the game to a different screen.
 * @author Atte Ala-Lahti
 */
abstract public class Screen extends Stage {

    private int screenID;
    private ArrayList<String> choices;
    private ArrayList<Integer> screenLinks;

    /** Creates a new screen.
     *
     * @param screenID a unique integer used in MainGame to determine which screen to show
     * @param choices text for each of the choices
     * @param screenLinks screen IDs for screens the choices lead to. Must be the same size as choices
     */
    public Screen (int screenID, ArrayList<String> choices, ArrayList<Integer> screenLinks) {
        this.screenID = screenID;
        this.choices = choices;
        this.screenLinks = screenLinks;
        if (choices.size() != screenLinks.size()) {
            throw new IllegalArgumentException("Choice and screen link amounts don't match.");
        }
    }
    public int getScreenID() {
        return screenID;
    }
    public ArrayList<String> getChoices() {
        return choices;
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
}
