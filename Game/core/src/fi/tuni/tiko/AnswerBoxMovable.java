package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.SnapshotArray;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static fi.tuni.tiko.MainGame.windowHeight;
import static fi.tuni.tiko.MainGame.windowWidth;
import static fi.tuni.tiko.MainGame.skin;

/** AnswerBoxes are text boxes that can be moved by dragging them. They stop halfway out from the screen and require a second swipe to confirm.
 * Confirming an AnswerBox calls it's Screen's nextScreen method with the AnswerBox's assigned screenLink,
 * screenLink being the ID of the Screen the AnswerBox's answer leads to.
 * Other AnswerBoxes on the same Screen cannot be dragged once one is on the edge of the screen.
 * @author Atte Ala-Lahti
 */
public class AnswerBoxMovable extends AnswerBox {
    private float moveDuration = 0.2f;
    private int screenLink;
    private boolean atEdge = false;
    private boolean currentlyTouched = false;
    private boolean paused = false;
    private float touchDifferenceX;
    private Label rail;
    private float startX;
    private float y;
    /** Creates a new AnswerBox.
     * An AnswerBox is comprised of a background label and a text box label to precisely control where the text can be.
     * In the future, different backgrounds might require different text box sizes.
     *
     * @param text the text in the text box
     * @param x horizontal coordinate in pixels
     * @param y vertical coordinate in pixels
     * @param width width of the background in pixels
     * @param height height of the background in pixels
     * @param screenLink the ID of the screen to move to when this AnswerBox is confirmed
     */
    public AnswerBoxMovable(CharSequence text, float x, float y, float width, float height, final int screenLink) {
        super(text, x, y, width, height);
        this.screenLink = screenLink;
        this.y = y;

        rail = new Label(null, skin, "rail");
        rail.setBounds(-windowWidth, y, windowWidth * 3f, height);
        addActor(rail);
        rail.toBack();
        getBackground().setStyle(skin.get("answer_movable", Label.LabelStyle.class));

        startX = getX();


        /* MOVEMENT */
        addListener(new InputListener() {
            // Determining which part of the box was touched
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                currentlyTouched = true;
                for (Actor a : getParent().getChildren()) {
                    System.out.println(!equals(a));
                    if ((((AnswerBoxMovable) a).atEdge || ((AnswerBoxMovable) a).currentlyTouched) && (screenLink != ((AnswerBoxMovable) a).screenLink)) {
                        currentlyTouched = false;
                    }
                }
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
                    System.out.println(!equals(a));
                    if ((((AnswerBoxMovable) a).atEdge || ((AnswerBoxMovable) a).currentlyTouched) && (screenLink != ((AnswerBoxMovable) a).screenLink)) {
                        canMove = false;
                    }
                }
                if ((canMove || atEdge) && !paused) {
                    setX(Gdx.input.getX() - touchDifferenceX);
                }
            }
            // Upon release moves to the center or the edges based on what is closest
            // If at the edge, can also move off the screen confirming the choice
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                currentlyTouched = false;
                float moveX = startX;
                boolean actionConfirmed = false;
                boolean movingToEdge = false;
                if (getParent().getChildren().size > 1) {
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
                    if (getX() < startX - windowWidth * 0.5f && atEdge) {
                        moveX = startX - windowWidth;
                        actionConfirmed = true;
                    } else if (getX() > startX + windowWidth * 0.5f && atEdge) {
                        moveX = startX + windowWidth;
                        actionConfirmed = true;
                    }
                } else if (getX() < startX) {
                    moveX = startX - windowWidth;
                    actionConfirmed = true;
                } else {
                    moveX = startX + windowWidth;
                    actionConfirmed = true;
                }
                final boolean finalEdge = movingToEdge;

                if (actionConfirmed) {
                    addAction(sequence(moveTo(moveX, getY(), moveDuration), run(new Runnable() {
                        @Override
                        public void run() {
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

    /** Sets pausing. The AnswerBox cannot be moved while paused.
     * Called by the Screen when moving out of game view.
     */
    public void setPause(boolean pause) {
        paused = pause;
    }
}
