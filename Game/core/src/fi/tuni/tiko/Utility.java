package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

/** The Utility class contains various methods that are used by various other classes.
 *
 * @author Atte Ala-Lahti
 */
public class Utility {

    /** Returns an opaque color based on RGB values from 0-255.
     *
     * @param r red value 0-255
     * @param g green value 0-255
     * @param b blue value 0-255
     * @return opaque color
     */
    public static Color colorMax255(float r, float g, float b) {
        float colorFraction = 1f / 255f;
        return new Color(r*colorFraction, g*colorFraction, b*colorFraction, 1);
    }

    /** Returns the localised version of the provided key.
     *
     * The localization is decided based on the device's language.
     * English is the default. Finnish is also available.
     *
     * @param key name of the localized item
     * @return the localization
     */
    public static String getLocalization(String key) {
        Locale locale = Locale.getDefault();
        I18NBundle myBundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale);
        return myBundle.get(key);
    }
}
