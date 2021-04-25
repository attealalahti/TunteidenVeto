package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public class Utility {
    public static Color colorMax255(float r, float g, float b) {
        float colorFraction = 1f / 255f;
        return new Color(r*colorFraction, g*colorFraction, b*colorFraction, 1);
    }
    public static String getLocalization(String key) {
        Locale locale = Locale.getDefault();
        I18NBundle myBundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale);
        return myBundle.get(key);
    }
}
