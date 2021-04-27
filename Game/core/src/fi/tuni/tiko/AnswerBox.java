package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import static fi.tuni.tiko.MainGame.skin;

/** AnswerBoxes are designed to be wide rectangles that contain some text.
 *
 * The text is a choice or tells how the feeling meters have changed.
 *
 * @author Atte Ala-Lahti
 */
public class AnswerBox extends Group {
    private Image background;
    private Label textBox;
    private float textMargin = 0.14f;
    /** Creates a new AnswerBox.
     *
     * An AnswerBox is comprised of a background image and a text box label to precisely control where the text can be.
     *
     * @param text       the text in the text box
     * @param x          horizontal coordinate in pixels
     * @param y          vertical coordinate in pixels
     * @param width      width of the background in pixels
     * @param height     height of the background in pixels
     */
    public AnswerBox(CharSequence text, float x, float y, float width, float height) {
        background = new Image(skin, "emotionScoreBox");
        background.setBounds(x, y, width, height);

        textBox = new Label(text, skin, "emotionScoreText");
        // Calculating the dimensions of the text box
        float xMargin = textMargin * width;
        float yMargin = textMargin * height;
        textBox.setBounds(x + xMargin * 0.5f, y + yMargin * 0.5f, width - xMargin, height - yMargin);
        textBox.setWrap(true);
        textBox.setAlignment(0);

        addActor(background);
        addActor(textBox);
    }

    /** Sets the image used for the background of the box.
     *
     * The image is found in MySkin.
     *
     * @param drawable name of the image
     */
    public void setBackground(String drawable) {
        background.setDrawable(skin, drawable);
    }

    /** Sets the style of the text in the box
     *
     * The style is found in MySkin.
     *
     * @param style name of the style
     */
    public void setTextStyle(String style) {
        textBox.setStyle(skin.get(style, Label.LabelStyle.class));
    }
}
