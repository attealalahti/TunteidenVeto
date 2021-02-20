package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
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
    private boolean touching = false;
    private float touchDifferenceX;
    private float touchDifferenceY;
    public AnswerBox(CharSequence text, Skin skin, float x, float y, float width, float height) {
        super(text, skin);
        setBounds(x, y, width, height);
        startX = x;
        setFontScale(2);
        setWrap(true);

        /*
        addListener(new ActorGestureListener() {
           public void fling (InputEvent event, float velocityX, float velocityY) {
               float moveX = startX;
               if (getX() < startX - windowWidth * 0.25f) {
                   moveX = startX - windowWidth * 0.5f;
               } else if (getX() > startX + windowWidth * 0.25f) {
                   moveX = startX + windowWidth * 0.5f;
               }
               boolean actionConfirmed = false;
               if (getX() < startX - windowWidth * 0.5f) {
                   moveX = startX - windowWidth;
                   actionConfirmed = true;
               } else if (getX() > startX + windowWidth * 0.5f) {
                   moveX = startX + windowWidth;
                   actionConfirmed = true;
               }
               if (actionConfirmed) {
                    addAction(sequence(moveTo(moveX, getY(), moveDuration), run(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Switches to next screen.");
                        }
                    })));
               } else {
                   addAction(moveTo(moveX, getY(), moveDuration));
               }
           }
        });
         */
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float touchX = Gdx.input.getX();

                touchDifferenceX = touchX - getX();



                return true;
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                setX(Gdx.input.getX() - touchDifferenceX);
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                float moveX = startX;
                if (getX() < startX - windowWidth * 0.25f) {
                    moveX = startX - windowWidth * 0.5f;
                } else if (getX() > startX + windowWidth * 0.25f) {
                    moveX = startX + windowWidth * 0.5f;
                }
                boolean actionConfirmed = false;
                if (getX() < startX - windowWidth * 0.5f) {
                    moveX = startX - windowWidth;
                    actionConfirmed = true;
                } else if (getX() > startX + windowWidth * 0.5f) {
                    moveX = startX + windowWidth;
                    actionConfirmed = true;
                }
                if (actionConfirmed) {
                    addAction(sequence(moveTo(moveX, getY(), moveDuration), run(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Switches to next screen.");
                        }
                    })));
                } else {
                    addAction(moveTo(moveX, getY(), moveDuration));
                }
            }
        });
    }
    /*
    @Override
    public void act(float delta) {
        super.act(delta);
        if (Gdx.input.justTouched()) {

        }

    }

     */
}
