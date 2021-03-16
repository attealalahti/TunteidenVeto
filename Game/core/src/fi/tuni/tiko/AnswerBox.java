package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.windowWidth;

/** AnswerBoxes are text boxes that can be moved by dragging them. They stop halfway out from the screen and require a second swipe to confirm.
 * Confirming an AnswerBox calls it's Screen's nextScreen method with the AnswerBox's assigned screenLink,
 * screenLink being the ID of the Screen the AnswerBox's answer leads to.
 * Other AnswerBoxes on the same Screen cannot be dragged once one is on the edge of the screen.
 * @author Atte Ala-Lahti
 */
public class AnswerBox extends Group {
    private float startX;
    private float moveDuration = 0.2f;
    private int screenLink;
    private boolean atEdge = false;
    private boolean unPaused = true;
    private float touchDifferenceX;
    private Label background;
    private Label textBox;
    private float margin = 0.2f;

    /** Creates a new AnswerBox.
     * An AnswerBox is comprised of a background label and a text box label to precisely control where the text can be.
     * In the future, different backgrounds might require different text box sizes.
     *
     * @param text the text in the text box
     * @param skin contains styles for all objects
     * @param x horizontal coordinate in pixels
     * @param y vertical coordinate in pixels
     * @param width width of the background in pixels
     * @param height height of the background in pixels
     * @param screenLink the ID of the screen to move to when this AnswerBox is confirmed
     */
    public AnswerBox(CharSequence text, Skin skin, float x, float y, float width, float height, final int screenLink) {
        this.screenLink = screenLink;
        background = new Label(null, skin);
        textBox = new Label(text, skin, "text");
        background.setBounds(x, y, width, height);

        // Calculating the dimensions of the text box
        float xMargin = margin * width;
        float yMargin = margin * height;
        textBox.setBounds(x + xMargin * 0.5f, y + yMargin * 0.5f, width - xMargin, height - yMargin);
        startX = getX();
        textBox.setFontScaleX(0.0025f * windowWidth);
        textBox.setFontScaleY(0.0015f * windowHeight);
        textBox.setWrap(true);
        textBox.setAlignment(0);

        addActor(background);
        addActor(textBox);

        /* MOVEMENT */
        addListener(new InputListener() {
            // Determining which part of the box was touched
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touchDifferenceX = Gdx.input.getX() - getX();
                return true;
            }
            // The box follows the cursor when dragged
            // If paused, does not move
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                System.out.println("touching");
                /* KNOWN BUG */
                // You can swipe with two fingers to move multiple boxes at the same time.
                boolean canMove = true;
                for (Actor a : getParent().getChildren()) {
                    if (((AnswerBox) a).atEdge) {
                        canMove = false;
                    }
                }
                if ((canMove || atEdge) && unPaused) {
                    setX(Gdx.input.getX() - touchDifferenceX);
                }
            }
            // Upon release moves to the center or the edges based on what is closest
            // If at the edge, can also move off the screen confirming the choice
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
                            Screen c = (Screen) getStage();
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

    /** Toggles pausing. The AnswerBox cannot be moved while paused.
     * Called by the Screen when moving out of game view.
     */
    public void pause() {
        if (unPaused) {
            unPaused = false;
        } else {
            unPaused = true;
        }
    }
}
