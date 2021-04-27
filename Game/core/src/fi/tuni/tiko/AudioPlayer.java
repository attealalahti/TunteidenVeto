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
    private Sound emotionSound = Gdx.audio.newSound(Gdx.files.internal("audio/piano.mp3"));
    private boolean canPlayMusic = true;
    private boolean canPlaySound = true;

    /** playMenuMusic method plays the menu music if it is playable
     *
     * if the canPlayMusic boolean is set to true, the music user wants to play is played.
     */
    public void playMenuMusic() {
        if(canPlayMusic) {
            backgroundMusic.play();
            backgroundMusic.setLooping(true);
        }
    }

    /** Sets the music's on state.
     *
     * If set to on, will start playing music.
     * If set to off, will pause the music.
     *
     * @param onState true: on, false: off
     */
    public void music(boolean onState) {
        canPlayMusic = onState;
        if (onState) {
            playMenuMusic();
        } else {
            backgroundMusic.pause();
        }
    }

    /** Sets the sound's on state.
     *
     * @param onState true: on, false: off
     */
    public void sound(boolean onState) {
        canPlaySound = onState;
    }

    /** playSwipeSound method plays the Swipe sound if it is allowed
     */
    public void playEmotionSound() {
        if (canPlaySound) {
            emotionSound.play();
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
    public boolean getMusicBoolean() {
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
    public boolean getSoundBoolean() {
        return  canPlaySound;
    }

    /** Disposes all music and sounds.
     */
    public void dispose() {
        backgroundMusic.dispose();
        emotionSound.dispose();
    }

}
