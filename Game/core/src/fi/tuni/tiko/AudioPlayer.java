package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/** AudioPlayer class is used to play different audios in the game
 *
 * @author Mika Kivennenä
 */
public class AudioPlayer {
    private Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/backgroundMusic.mp3"));
    private Sound swipeSound = Gdx.audio.newSound(Gdx.files.internal("audio/swipe3.mp3"));
    private static boolean canPlayMusic= true;
    private static boolean canPlaySound = true;

    /** playMenuMusic method play the menu music if it is playable
     *
     * if the canPlayMusic boolean is set to true, the music user wants to play is played.
     *
     * @author Mika Kivennenä
     */
    public void playMenuMusic() {
        if(canPlayMusic) {
            backgroundMusic.play();
        }
    }

    /** playMusic method plays the music if it is allowed
     *
     * User gives Music variable as a parameter and
     *
     * @author Mika Kivennenä
     */
    public static void playMusic(Music songToPlay) {
        if(canPlayMusic) {
            songToPlay.play();
        }
    }

    /** playSwipeSound method plays the Swipe sound if it is allowed
     *
     *
     * @author Mika Kivennenä
     */
    public void playSwipeSound() {
       // swipeSound.play();
    }

    /** playSound method plays the given sound if allowed
     *
     * if playing a sound is enabled then this method will play the sound user gives as a parameter.
     *
     * @param sound variable is the sound that is played
     * @author Mika Kivennenä
     */
    public static void playSound(Sound sound) {
        if(canPlaySound) {
            sound.play();
        }
    }

    /** Sets music to be playable or not playable
     *
     * @param bool is used to determine if music is enabled or disabled
     * @author Mika Kivennenä
     */
    public void setMusicBoolean(Boolean bool) {
        canPlayMusic = bool;
    }

    /** Returns the canPlayMusic boolean
     *
     * Returns the current state of canPlayMusic boolean.
     *
     * @return canPlayMusic boolean is returned
     * @author Mika Kivennenä
     */
    public Boolean getMusicBoolean() {
        return canPlayMusic;
    }

    /** Sets sound to be playable or not playable
     *
     * @param bool is used to determine if sound is enabled or disabled
     * @author Mika Kivennenä
     */
    public void setSoundBoolean(Boolean bool) {
        canPlaySound = bool;
    }

    /** Returns the canPlaySound boolean
     *
     * Returns the current state of canPlaySound boolean.
     *
     * @return canPlaySound boolean is returned
     * @author Mika Kivennenä
     */
    public Boolean getSoundBoolean() {
        return  canPlaySound;
    }

}
