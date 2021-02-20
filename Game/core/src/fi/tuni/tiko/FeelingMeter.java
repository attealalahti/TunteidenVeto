package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Color;

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

public class FeelingMeter extends ProgressBar {
    private static final float MIN_VALUE = 0f;
    private static final float MAX_VALUE = 100f;

    public FeelingMeter(int width, int height) {
        super(0f, 100f, 1f, false, new ProgressBarStyle());
        getStyle().background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("mittari_tyhja.png"))));
        getStyle().knob = getColoredDrawable(0, height, Color.GREEN);
        getStyle().knobBefore = getColoredDrawable(width, height, Color.GREEN);

        setWidth(width);
        setHeight(height);

        setAnimateDuration(0.0f);
        setValue(1f);

        setAnimateDuration(0.25f);
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
        float tempFloat = getValue();
        tempFloat += valueToAdd;
        if(tempFloat > MAX_VALUE) {
            Gdx.app.log("Error", "Value exceeds the maximum limit off: " + MAX_VALUE);
        } else {
            setValue(tempFloat);
        }
    }

    public void reduceValue(float valueToReduce) {
        float tempFloat = getValue();
        tempFloat -= valueToAdd;
        if(tempFloat < MIN_VALUE) {
            Gdx.app.log("Error", "Value exceeds the minimum limit off: " + MIN_VALUE);
        } else {
            setValue(tempFloat);
        }
    }

}
