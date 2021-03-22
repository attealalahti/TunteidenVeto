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

/** FeelingMeter is a progressbar used to showcase different values
 * FeelingMeter is used to keep track of the choices that player makes
 * It shows progress for all emotions and how players choices effect them.
 *
 * @author Mika Kivennenä
 */

public class FeelingMeter extends ProgressBar {
    private static final float MIN_VALUE = 0f;
    private static final float MAX_VALUE = 100f;


    /** Constructor for the FeelingMeter class
     *
     * @param width determines the width of the meter
     * @param height determines the height of the meter
     * @author Mika Kivennenä
     */
    public FeelingMeter(int width, int height) {
        super(0f, 100f, 1f, false, new ProgressBarStyle());
        getStyle().background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("mittari_tyhja.png"))));
        getStyle().knob = getColoredDrawable(0, height, Color.GREEN);
        getStyle().knobBefore = getColoredDrawable(width, height, Color.GREEN);

        setWidth(width);
        setHeight(height);

        setAnimateDuration(0.1f);
        setValue(1f);

        setAnimateDuration(0.25f);
    }

    /** Creates a drawable texture used to fill the meter
     *
     * @param width determines the width of the meter filling
     * @param height determines the height of the meter filling
     * @param color defines the fillable color of the meter filling.
     * @author Mika Kivennenä
     */
    public Drawable getColoredDrawable(int width, int height, Color col) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(col);
        pixmap.fill();

        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));

        pixmap.dispose();

        return drawable;
    }

    /** Increases the value on the feeling meter
     *
     * @param valueToAdd float is the given parameter that is used to increase the feeling meters value
     * @author Mika Kivennenä
     */
    public void addValue(float valueToAdd) {
        float tempFloat = getValue();
        tempFloat += valueToAdd;
        if(tempFloat > MAX_VALUE) {
            setValue(MAX_VALUE);
        } else {
            setValue(tempFloat);
        }
    }

    /** Decreases the value on the feeling meter
     *
     * @param valueToReduce float is the given parameter that is used to decrease the feeling meters value
     * @author Mika Kivennenä
     */
    public void reduceValue(float valueToReduce) {
        float tempFloat = getValue();
        tempFloat -= valueToReduce;
        if(tempFloat < MIN_VALUE) {
            setValue(0);
        } else {
            setValue(tempFloat);
        }
    }

}
