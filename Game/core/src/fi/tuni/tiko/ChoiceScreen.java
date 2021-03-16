package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;

import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.windowWidth;

public class ChoiceScreen extends Screen {

    private String question;
    private Group game;
    private Group meters;
    private Group answerBoxes;
    private final float boxWidth = windowWidth * 0.9f;
    private final float boxHeight = windowHeight * 0.1f;
    private final float margin = windowHeight * 0.025f;
    private final float xBox = (windowWidth - boxWidth) / 2f;
    private final float buttonHeight = windowHeight * 0.07f;
    private final float meterWidth = windowWidth * 0.8f;
    private final float meterHeight = windowHeight * 0.1f;
    private final float FADE_TIME = 0.2f;
    private boolean gameView = true;

    public ChoiceScreen(int screenID, Skin skin, String question, ArrayList<String> answers, ArrayList<Integer> screenLinks) {
        super(screenID, skin, answers, screenLinks);
        this.question = question;

        answerBoxes = new Group();
        game = new Group();
        meters = new Group();
        addActor(game);
        addActor(meters);

        createAnswerBoxes();

        float roomLeft = windowHeight - (answers.size() * (boxHeight + margin) + margin + buttonHeight + margin);
        Label questionBox = new Label(question, skin, "question");
        questionBox.setBounds(xBox, windowHeight - roomLeft, boxWidth, roomLeft - margin);
        questionBox.setAlignment(0);
        questionBox.setFontScaleX(0.005f * windowWidth);
        questionBox.setFontScaleY(0.003f * windowHeight);
        questionBox.setWrap(true);
        game.addActor(questionBox);

        Button feelingMeterButton = new Button(skin);
        feelingMeterButton.setBounds(windowWidth * 0.5f - buttonHeight * 0.5f, margin, buttonHeight, buttonHeight);
        addActor(feelingMeterButton);
        //feelingMeterButton.setStyle(skin.get("alt", Button.ButtonStyle.class));

        float meterLocationHeight = meterHeight * 7 + margin * 7;
        float currentY = windowHeight - meterLocationHeight;
        for (int i = 0; i < 7; i++) {
            Label myLabel = new Label(i + "", skin, "question");
            myLabel.setBounds(windowWidth * 0.5f - meterWidth * 0.5f, currentY, meterWidth, meterHeight);
            myLabel.setAlignment(0);
            myLabel.setFontScaleX(0.005f * windowWidth);
            myLabel.setFontScaleY(0.003f * windowHeight);
            meters.addActor(myLabel);
            currentY += margin + meterHeight;
        }
        meters.toBack();
        meters.addAction(Actions.fadeOut(0));

        feelingMeterButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (gameView) {
                    game.addAction(Actions.fadeOut(FADE_TIME));
                    meters.addAction(Actions.fadeIn(FADE_TIME));
                    game.toBack();
                    meters.toFront();
                    gameView = false;
                } else {
                    game.addAction(Actions.fadeIn(FADE_TIME));
                    meters.addAction(Actions.fadeOut(FADE_TIME));
                    meters.toBack();
                    game.toFront();
                    gameView = true;
                }
                pauseAnswers();
            }
        });
    }
    public void createAnswerBoxes() {
        float currentY = margin * 2 + buttonHeight;
        for (int i = 0; i < getChoices().size(); i++) {
            answerBoxes.addActor(new AnswerBox(getChoices().get(i), getSkin(), xBox, currentY, boxWidth, boxHeight, getScreenLinks().get(i)));
            currentY += margin + boxHeight;
        }
        game.addActor(answerBoxes);
    }

    public void pauseAnswers() {
        for (Actor a: answerBoxes.getChildren()) {
            AnswerBox ab = (AnswerBox) a;
            ab.pause();
        }
    }
}
