package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import static fi.tuni.tiko.MainGame.skin;
import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.windowWidth;


public class AnswerBox extends Group {
    private Label background;
    private Label textBox;
    private float textMargin = 0.14f;
    /**
     * Creates a new AnswerBox.
     * An AnswerBox is comprised of a background label and a text box label to precisely control where the text can be.
     * In the future, different backgrounds might require different text box sizes.
     *
     * @param text       the text in the text box
     * @param x          horizontal coordinate in pixels
     * @param y          vertical coordinate in pixels
     * @param width      width of the background in pixels
     * @param height     height of the background in pixels
     */
    public AnswerBox(CharSequence text, float x, float y, float width, float height) {
        background = new Label(null, skin, "answer_static");
        textBox = new Label(text, skin, "emotionScoreText");
        background.setBounds(x, y, width, height);

        // Calculating the dimensions of the text box
        float xMargin = textMargin * width;
        float yMargin = textMargin * height;
        textBox.setBounds(x + xMargin * 0.5f, y + yMargin * 0.5f, width - xMargin, height - yMargin);
        /*
        textBox.setFontScaleX(0.000324f * windowWidth);
        textBox.setFontScaleY(0.00018f * windowHeight);
         */
        textBox.setWrap(true);
        textBox.setAlignment(0);

        addActor(background);
        addActor(textBox);
    }
    public Label getBackground() {
        return background;
    }

    public Label getTextBox() {
        return textBox;
    }
}
