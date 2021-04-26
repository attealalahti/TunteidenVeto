package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import static fi.tuni.tiko.MainGame.mainMenuScreenID;
import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.windowWidth;
import static fi.tuni.tiko.Utility.getLocalization;

/** Screen is an abstract class that is used to create different screen layouts for the game.
 *
 * Every screen has a screen ID, some choices that can move the game to a different screen and a group that houses all it's elements.
 *
 * @author Atte Ala-Lahti
 */
abstract public class Screen extends Stage {

    private int screenID;
    private ArrayList<Integer> screenLinks;
    private ArrayList<String> temporaryList;
    private Group elements = new Group();

    private final float boxWidth = windowWidth * 0.9f;
    private final float boxHeight = windowHeight * 0.1f;
    private final float boxX = (windowWidth - boxWidth) * 0.5f;

    /** Creates a new screen. The basic constructor.
     *
     * All parameters need to be specified.
     *
     * @param screenID a unique integer used in MainGame to determine which screen to show
     * @param choices text for each of the choices
     * @param screenLinks list of screen IDs for screens the choices lead to. Must be the same size as choices
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

    /** Creates a screen with the specification of the main menu.
     *
     * Used in the construction of the MainMenuScreen class.
     */
    public Screen() {
        this.screenID = mainMenuScreenID;
        this.temporaryList = createMainMenuAnswers();
        this.screenLinks = createMainMenuScreenLinks();
        addActor(elements);
    }

    /** Returns the choices of the main menu.
     *
     * Used for constructing the main menu.
     *
     * @return a list of choices on the main menu
     */
    public ArrayList<String> createMainMenuAnswers() {
        ArrayList<String> temporaryList = new ArrayList<>();
        temporaryList.add(getLocalization("exitGame").toUpperCase());
        temporaryList.add(getLocalization("settings").toUpperCase());
        temporaryList.add(getLocalization("continue").toUpperCase());
        temporaryList.add(getLocalization("newGame").toUpperCase());
        return temporaryList;
    }

    /** Returns the IDs for the choices for the main menu.
     *
     * The MainMenuScreen class will determine what to do based on these numbers.
     * Used for constructing the main menu.
     *
     * @return a list of IDs for the main menu choices
     */
    public ArrayList<Integer> createMainMenuScreenLinks() {
        ArrayList<Integer> menuScreenLinks = new ArrayList<Integer>();
        menuScreenLinks.add(4);
        menuScreenLinks.add(3);
        menuScreenLinks.add(2);
        menuScreenLinks.add(1);
        return menuScreenLinks;
    }

    /** Returns the screen's identifying number.
     *
     * @return screenID
     */
    public int getScreenID() {
        return screenID;
    }

    /** Returns the text for each of the possible choices.
     *
     * @return list of choices
     */
    public ArrayList<String> getChoices() {
        return temporaryList;
    }

    /** Returns the IDs of screens the choices on this screen lead.
     *
     * @return list of screen IDs
     */
    public ArrayList<Integer> getScreenLinks() {
        return screenLinks;
    }

    /** Called by an AnswerBoxMovable when it is selected and confirmed.
     *
     * @param screenLink The ID number of the next screen.
     */
    public void nextScreen(int screenLink) {
        MainGame.currentScreenID = screenLink;
    }

    /** Returns a group that should contain all actor elements specific to this screen.
     *
     * @return a group of all elements of the screen
     */
    public Group getElements() {
        return elements;
    }

    /** Returns a default width that the boxes on the screen can use.
     *
     * @return width of the boxes on the screen
     */
    public float getBoxWidth() {
        return boxWidth;
    }

    /** Returns a default height that the answer boxes on the screen can use.
     *
     * @return height of the answer boxes on the screen
     */
    public float getBoxHeight() {
        return boxHeight;
    }

    /** Returns an x position that will center an object with the default box width on the screen.
     *
     * @return centered x position for objects of default box width
     */
    public float getBoxX() {
        return boxX;
    }
}
