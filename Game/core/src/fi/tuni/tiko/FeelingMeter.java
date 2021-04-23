package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    private Image foreground;

    private final float MIN_VALUE = 0f;
    private final float MAX_VALUE = 100f;
    private final float textWidth = meterHeight;
    private final float textHeight = textWidth / 3f;
    private final float imageMargin = windowWidth * 0.05f;
    private final float combinedWidth = windowWidth * 0.8f;
    private final float meterWidth = combinedWidth - imageMargin - textWidth;
    private final float imageWidth = textWidth * (2f/3f);

    public FeelingMeter(float y, Color color, String imageStyle) {
        Label caption = new Label(MainGame.getLocalization(imageStyle), skin, "meterCaptionText");
        caption.setBounds((windowWidth - combinedWidth) * 0.5f, y, textWidth, textHeight);
        caption.setAlignment((int)textHeight - 1, 0);
        addActor(caption);

        Image image = new Image(skin, imageStyle);
        image.setBounds((windowWidth - combinedWidth + textWidth - imageWidth) * 0.5f, y + textHeight, imageWidth, imageWidth);
        addActor(image);

        meter = new ProgressBar(MIN_VALUE, MAX_VALUE, 1f, false, new ProgressBar.ProgressBarStyle());
        meter.getStyle().knobBefore = getColoredDrawable((int)(meterWidth / MAX_VALUE), (int)meterHeight, color);
        meter.getStyle().knobAfter = getColoredDrawable((int)(meterWidth / MAX_VALUE), (int)meterHeight, Color.WHITE);
        meter.setBounds(caption.getX() + textWidth + imageMargin, y, meterWidth, meterHeight);
        meter.setAnimateDuration(0.25f);
        meter.setValue(50f);
        addActor(meter);

        foreground = new Image(skin, "meterForeground");
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
    public float getValue() {
        return meter.getValue();
    }
    public void setValue(float value) {
        meter.setValue(value);
    }
}
