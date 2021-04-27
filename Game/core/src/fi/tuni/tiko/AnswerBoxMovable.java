package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static fi.tuni.tiko.MainGame.windowWidth;
import static fi.tuni.tiko.MainGame.skin;

/** These AnswerBoxes can be moved by dragging them.
 *
 * They stop halfway out from the screen and require a second swipe to confirm.
 * Confirming an AnswerBox calls it's Screen's nextScreen method with the AnswerBox's assigned screenLink,
 * screenLink being the ID of the Screen the AnswerBox's answer leads to.
 * Other AnswerBoxes on the same Screen cannot be dragged once one is on the edge of the screen.
 * @author Atte Ala-Lahti
 */
public class AnswerBoxMovable extends AnswerBox {
    private boolean needsConfirmation = true;
    private float moveDuration = 0.2f;
    private CharSequence text;
    private boolean atEdge = false;
    private boolean currentlyTouched = false;
    private boolean paused = false;
    private float touchDifferenceX;
    private float startX;
    private float y;
    private float height;

    /** Creates a new movable AnswerBox.
     *
     * An AnswerBox is comprised of a background image and a text label to precisely control where the text can be.
     * Listeners are added to handle movement.
     *
     * @param text the text in the text box
     * @param x horizontal coordinate in pixels
     * @param y vertical coordinate in pixels
     * @param width width of the background in pixels
     * @param height height of the background in pixels
     * @param screenLink the ID of the screen to move to when this AnswerBox is confirmed
     */
    public AnswerBoxMovable(final CharSequence text, float x, float y, float width, float height, final int screenLink) {
        super(text, x, y, width, height);
        this.text = text;
        this.y = y;
        this.height = height;

        setBackground("answerBox");
        setTextStyle("answerBoxText");
        startX = getX();


        /* MOVEMENT */
        addListener(new InputListener() {
            // Determining which part of the box was touched
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                currentlyTouched = true;
                for (Actor a : getParent().getChildren()) {
                    if ((((AnswerBoxMovable) a).atEdge || ((AnswerBoxMovable) a).currentlyTouched) && (!text.equals(((AnswerBoxMovable) a).text))) {
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
                boolean canMove = true;
                for (Actor a : getParent().getChildren()) {
                    if ((((AnswerBoxMovable) a).atEdge || ((AnswerBoxMovable) a).currentlyTouched) && (!text.equals(((AnswerBoxMovable) a).text))) {
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
                //AudioPlayer.playSound(swipeSound); // <<<<<<<<<<------------------------------------------------------------------------ MIKA TÄÄLLÄ SE SOUND PLAY ON!!!!!
                currentlyTouched = false;
                float moveX = startX;
                boolean actionConfirmed = false;
                boolean movingToEdge = false;
                if (getParent().getChildren().size > 1 && needsConfirmation) {
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
                final boolean FINAL_EDGE = movingToEdge;

                if (actionConfirmed && moveX != getX()) {
                    addAction(sequence(moveTo(moveX, getY(), moveDuration), run(new Runnable() {
                        @Override
                        public void run() {
                            Screen c = (Screen) getStage();
                            c.nextScreen(screenLink);
                        }
                    })));
                } else if (moveX != getX()) {
                    addAction(sequence(moveTo(moveX, getY(), moveDuration), run(new Runnable() {
                        @Override
                        public void run() {
                            if (FINAL_EDGE) {
                                atEdge = true;
                            }
                            for (Actor a : getParent().getChildren()) {
                                if (!text.equals(((AnswerBoxMovable) a).text)) {
                                    if (atEdge) {
                                        ((AnswerBoxMovable) a).setBackground("inactiveBox");
                                    } else {
                                        ((AnswerBoxMovable) a).setBackground("answerBox");
                                    }
                                }
                            }
                        }
                    })));
                }
            }
        });
    }

    /** Sets pausing. The AnswerBox cannot be moved while paused.
     * Called by the Screen when moving out of game view.
     *
     * @param pause true: pause, false: unpause
     */
    public void setPause(boolean pause) {
        paused = pause;
    }

    /** Adds a rail to list of elements on the AnswerBox's screen.
     *
     * The rail is a dark rectangle that the box slides on.
     */
    public void addRail() {
        Image rail = new Image(skin, "rail");
        rail.setBounds(-windowWidth, y, windowWidth * 3f, height);
        ((Screen) getStage()).getElements().addActor(rail);
        rail.toBack();
    }

    /** Set whether the answer needs to be confirmed with a second swipe or not.
     *
     * If a second swipe is not necessary, even a tap while choose the answer.
     *
     * @param value true: needs confirmation or false: doesn't need confirmation
     */
    public void setConfirmationNeed(boolean value) {
        needsConfirmation = value;
    }
}
