package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.windowWidth;

public class ChoiceScreen extends Stage {
    public ChoiceScreen(Skin skin) {
        float boxWidth = windowWidth * 0.9f;
        float boxHeight = windowHeight * 0.1f;
        float boxMargin = windowHeight * 0.05f;
        float xBox = (windowWidth - boxWidth) / 2f;

        float currentY = boxMargin;
        for (int i = 0; i < 3; i++) {
            addActor(new AnswerBox("Laatikko numero " + i, skin, xBox, currentY, boxWidth, boxHeight));
            currentY += boxMargin + boxHeight;
        }
    }
}
