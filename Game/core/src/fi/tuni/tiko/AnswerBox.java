package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static fi.tuni.tiko.MainGame.windowWidth;

public class AnswerBox extends Label {
    private float startX;
    private float moveDuration = 0.2f;
    private int screenLink;
    public boolean atEdge = false;
    private float touchDifferenceX;
    public AnswerBox(CharSequence text, Skin skin, float x, float y, float width, float height, final int screenLink) {
        super(text, skin);
        this.screenLink = screenLink;
        setBounds(x, y, width, height);
        startX = x;
        setFontScale(2);
        setWrap(true);
        setAlignment(0);
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touchDifferenceX = Gdx.input.getX() - getX();
                return true;
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                /* KNOWN BUG */
                // You can swipe with two fingers to move multiple boxes at the same time.
                boolean canMove = true;
                for (Actor a : getStage().getActors()) {
                    if (a.getClass() == AnswerBox.class) {
                        if (((AnswerBox) a).atEdge) {
                            canMove = false;
                        }
                    }
                }
                if (canMove || atEdge) {
                    setX(Gdx.input.getX() - touchDifferenceX);
                }
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                float moveX = startX;
                boolean movingToEdge = false;
                if (getX() < startX - windowWidth * 0.25f) {
                    moveX = startX - windowWidth * 0.5f;
                    movingToEdge = true;
                } else if (getX() > startX + windowWidth * 0.25f) {
                    moveX = startX + windowWidth * 0.5f;
                    movingToEdge = true;
                } else {
                    atEdge = false;
                    movingToEdge = false;
                }
                final boolean finalEdge = movingToEdge;
                boolean actionConfirmed = false;
                if (getX() < startX - windowWidth * 0.5f && atEdge) {
                    moveX = startX - windowWidth;
                    actionConfirmed = true;
                } else if (getX() > startX + windowWidth * 0.5f && atEdge) {
                    moveX = startX + windowWidth;
                    actionConfirmed = true;
                }
                if (actionConfirmed) {
                    addAction(sequence(moveTo(moveX, getY(), moveDuration), run(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Switches to next screen.");
                            ChoiceScreen c = (ChoiceScreen) getStage();
                            c.nextScreen(screenLink);
                        }
                    })));
                } else {
                    addAction(sequence(moveTo(moveX, getY(), moveDuration), run(new Runnable() {
                        @Override
                        public void run() {
                            if (finalEdge) {
                                atEdge = true;
                            }
                        }
                    })));
                }
            }
        });
    }
}
