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

/*
How to use in Main Class
MAIN
private Stage stage;
FeelingMeter feelingMeter;

CREATE
stage = new Stage();
feelingMeter = new FeelingMeter(100, 10, 0f, 100f);
feelingMeter.setPosition(10, Gdx.graphics.getHeight() - 20);
stage.addActor(feelingMeter);

RENDER
feelingMeter.setValue(feelingMeter.getValue() - 0.1f);
stage.draw();
stage.act();
 */

public class FeelingMeter extends Group {
    private ProgressBar meter;
    private Label foreground;

    private static final float MIN_VALUE = 0f;
    private static final float MAX_VALUE = 100f;
    private float meterWidth = windowWidth * 0.8f;

    public FeelingMeter(float y, Color color) {
        meter = new ProgressBar(0f, 100f, 1f, false, new ProgressBar.ProgressBarStyle());
        meter.getStyle().knobBefore = getColoredDrawable((int)(meterWidth / 100f), (int)meterHeight, color);
        meter.setBounds(windowWidth * 0.5f - meterWidth * 0.5f, y, meterWidth, meterHeight);
        meter.setAnimateDuration(0.25f);
        meter.setValue(50f);
        addActor(meter);

        foreground = new Label(null, skin, "feelingMeterForeground");
        foreground.setBounds(meter.getX(), meter.getY(), meter.getWidth(), meter.getHeight());
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
            Gdx.app.log("Error", "Value exceeds the maximum limit off: " + MAX_VALUE);
        } else {
            meter.setValue(tempFloat);
        }
    }

    public void reduceValue(float valueToReduce) {
        float tempFloat = meter.getValue();
        tempFloat -= valueToReduce;
        if(tempFloat < MIN_VALUE) {
            Gdx.app.log("Error", "Value exceeds the minimum limit off: " + MIN_VALUE);
        } else {
            meter.setValue(tempFloat);
        }
    }

}
