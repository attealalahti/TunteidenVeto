package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Color;

import static fi.tuni.tiko.MainGame.meterHeight;
import static fi.tuni.tiko.MainGame.skin;
import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.windowWidth;

public class FeelingMeter extends Group {
    private ProgressBar meter;
    private Label foreground;

    private final float MIN_VALUE = 0f;
    private final float MAX_VALUE = 100f;
    private float meterWidth = windowWidth * 0.8f;

    public FeelingMeter(float y, Color color) {
        meter = new ProgressBar(0f, 100f, 1f, false, new ProgressBar.ProgressBarStyle());
        meter.getStyle().knobBefore = getColoredDrawable((int)(meterWidth / MAX_VALUE), (int)meterHeight, color);
        meter.getStyle().knobAfter = getColoredDrawable((int)(meterWidth / MAX_VALUE), (int)meterHeight, Color.WHITE);
        meter.setBounds(windowWidth * 0.5f - meterWidth * 0.5f, y, meterWidth, meterHeight);
        meter.setAnimateDuration(0.25f);
        meter.setValue(50f);
        addActor(meter);

        foreground = new Label(null, skin, "feelingMeterForeground");
        float tempMargin = 1.02f;
        foreground.setBounds(
                meter.getX() + 0.5f * (meter.getWidth() * (1-tempMargin)),
                meter.getY() + 0.5f * (meter.getHeight() * (1-tempMargin)),
                meter.getWidth() * tempMargin,
                meter.getHeight() * tempMargin
        );
        addActor(foreground);
    }

    public Drawable getColoredDrawable(int width, int height, Color col) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(col);
        pixmap.fill();

        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));

        pixmap.dispose();

        return drawable;
    }

    public void addValue(float valueToAdd) {
        float tempFloat = meter.getValue();
        tempFloat += valueToAdd;
        if(tempFloat > MAX_VALUE) {
            tempFloat = MAX_VALUE;
        } else if (tempFloat < MIN_VALUE){
            tempFloat = MIN_VALUE;
        }
        meter.setValue(tempFloat);
    }
}
