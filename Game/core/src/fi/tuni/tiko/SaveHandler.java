package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import static fi.tuni.tiko.MainGame.audioPlayer;
import static fi.tuni.tiko.MainGame.continueUnlocked;
import static fi.tuni.tiko.MainGame.currentScreenID;
import static fi.tuni.tiko.MainGame.globalElements;
import static fi.tuni.tiko.MainGame.nextScreenID;
import static fi.tuni.tiko.MainGame.weekDay;
import static fi.tuni.tiko.Utility.getLocalization;

/** The SaveHandler class handles the saving, loading and resetting of the game.
 *
 * The settings are saved separately.
 *
 * @author Atte Ala-Lahti
 */
public class SaveHandler {
    /** Saves the game.
     *
     * Saves the current screen, current day, state of the meters and settings.
     */
    public static void saveProgress() {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");
        prefs.putInteger("screen", nextScreenID);
        prefs.putString("day", weekDay);
        prefs.putFloat("happiness", globalElements.getMeter("happiness").getValue());
        prefs.putFloat("sadness", globalElements.getMeter("sadness").getValue());
        prefs.putFloat("anger", globalElements.getMeter("anger").getValue());
        prefs.putFloat("love", globalElements.getMeter("love").getValue());
        prefs.putFloat("astonishment", globalElements.getMeter("astonishment").getValue());
        prefs.putFloat("fear", globalElements.getMeter("fear").getValue());
        prefs.putFloat("disgust", globalElements.getMeter("disgust").getValue());
        saveSettings();
        saveContinueUnlock();
        prefs.flush();
    }
    /** Loads the game
     *
     * Loads the screen, day, state of the meters and settings.
     * The default day is Monday, default screen is 266 and default state of the meters is half full.
     * Global elements are briefly hidden while the game loads.
     */
    public static void loadProgress() {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");
        currentScreenID = prefs.getInteger("screen", 266);
        weekDay = prefs.getString("day", getLocalization("monday"));
        float meterDefault = 50;
        globalElements.getMeter("happiness").setValue(prefs.getFloat("happiness", meterDefault));
        globalElements.getMeter("sadness").setValue(prefs.getFloat("sadness", meterDefault));
        globalElements.getMeter("anger").setValue(prefs.getFloat("anger", meterDefault));
        globalElements.getMeter("love").setValue(prefs.getFloat("love", meterDefault));
        globalElements.getMeter("astonishment").setValue(prefs.getFloat("astonishment", meterDefault));
        globalElements.getMeter("fear").setValue(prefs.getFloat("fear", meterDefault));
        globalElements.getMeter("disgust").setValue(prefs.getFloat("disgust", meterDefault));
        loadSettings();
        loadContinueUnlock();
        globalElements.hideBackgroundElementsWhileLoading();
    }

    /** Saves the settings.
     *
     * Saves music and sound options.
     */
    public static void saveSettings() {
        Preferences prefs = Gdx.app.getPreferences("MySettings");
        prefs.putBoolean("music", audioPlayer.getMusicBoolean());
        prefs.putBoolean("sound", audioPlayer.getSoundBoolean());
        prefs.flush();
    }

    /** Loads the settings.
     *
     * The default setting for music and sounds is on.
     */
    public static void loadSettings() {
        Preferences prefs = Gdx.app.getPreferences("MySettings");
        audioPlayer.setMusicBoolean(prefs.getBoolean("music", true));
        audioPlayer.setSoundBoolean(prefs.getBoolean("sound", true));
        globalElements.getMusicButton().setChecked(audioPlayer.getMusicBoolean());
        globalElements.getSoundButton().setChecked(audioPlayer.getSoundBoolean());
    }

    /** Resets the game's progress.
     *
     * Settings are not reset.
     */
    public static void resetProgress() {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");
        prefs.clear();
        prefs.flush();
    }

    /** Loads whether there is an old save already.
     *
     * Default is false.
     */
    public static void loadContinueUnlock() {
        Preferences prefs = Gdx.app.getPreferences("MyContinue");
        continueUnlocked = prefs.getBoolean("continue", false);
    }

    /** Saves whether there is an old save already.
     */
    public static void saveContinueUnlock() {
        Preferences prefs = Gdx.app.getPreferences("MyContinue");
        prefs.putBoolean("continue", continueUnlocked);
        prefs.flush();
    }
}
