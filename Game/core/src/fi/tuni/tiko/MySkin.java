package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static fi.tuni.tiko.Utility.colorMax255;

/** The MySkin class holds all textures, fonts and UI styles for the game.
 *
 * The class is used to create a static object that everything in the game refers to when something visual is needed.
 */
public class MySkin extends Skin {

    private Color secondaryColor = colorMax255(234, 158, 128);

    /** Creates the skin.
     * Adds textures, fonts and UI styles.
     */
    public MySkin() {
        addTextures();
        addFonts();
        createStyles();
    }

    /** Adds textures to the skin.
     *
     * Gives the textures names that are used when getting them from the skin.
     */
    public void addTextures() {
        Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        add("white", new Texture(pixmap));
        add("answerBox", new Texture(getPath("box")));
        add("inactiveBox", new Texture(getPath("box3")));
        add("emotionScoreBox", new Texture(getPath("box2")));
        add("menuBox", new Texture(getPath("mainmenubox")));
        add("arrowBox", new Texture(getPath("arrowbox")));
        add("rail", new Texture(getPath("rail")));
        add("questionBox", new Texture(getPath("textbox")));
        add("bigQuestionBox", new Texture(getPath("bigtextbox")));
        add("biggerQuestionBox", new Texture(getPath("biggertextbox")));
        add("settings", new Texture(getPath("hamburgermenu")));
        add("settings_pressed", new Texture(getPath("hamburgermenu_pressed")));
        add("mainMenu", new Texture(getPath("mainmenubutton")));
        add("meterForeground", new Texture(getPath("meter")));
        add("soundOn", new Texture(getPath("sound_on")));
        add("soundOff", new Texture(getPath("sound_off")));
        add("musicOn", new Texture(getPath("music_on")));
        add("musicOff", new Texture(getPath("music_off")));
        add("title", new Texture(getPath("mainmenutitle")));

        add("anger", new Texture(getPath("anger")));
        add("astonishment", new Texture(getPath("astonishment")));
        add("disgust", new Texture(getPath("disgust")));
        add("fear", new Texture(getPath("fear")));
        add("happiness", new Texture(getPath("joy")));
        add("love", new Texture(getPath("love")));
        add("sadness", new Texture(getPath("sadness")));
        String modification = "_button";
        add("angerButton", new Texture(getPath("anger"+modification)));
        add("astonishmentButton", new Texture(getPath("astonishment"+modification)));
        add("disgustButton", new Texture(getPath("disgust"+modification)));
        add("fearButton", new Texture(getPath("fear"+modification)));
        add("happinessButton", new Texture(getPath("joy"+modification)));
        add("loveButton", new Texture(getPath("love"+modification)));
        add("sadnessButton", new Texture(getPath("sadness"+modification)));
        modification = "_pressed";
        add("angerButtonPressed", new Texture(getPath("anger"+modification)));
        add("astonishmentButtonPressed", new Texture(getPath("astonishment"+modification)));
        add("disgustButtonPressed", new Texture(getPath("disgust"+modification)));
        add("fearButtonPressed", new Texture(getPath("fear"+modification)));
        add("happinessButtonPressed", new Texture(getPath("joy"+modification)));
        add("loveButtonPressed", new Texture(getPath("love"+modification)));
        add("sadnessButtonPressed", new Texture(getPath("sadness"+modification)));
        modification = "_body";
        add("anger_body", new Texture(getPath("anger"+modification)));
        add("astonishment_body", new Texture(getPath("astonishment"+modification)));
        add("disgust_body", new Texture(getPath("disgust"+modification)));
        add("fear_body", new Texture(getPath("fear"+modification)));
        add("joy_body", new Texture(getPath("joy"+modification)));
        add("love_body", new Texture(getPath("love"+modification)));
        add("sadness_body", new Texture(getPath("sadness"+modification)));
    }

    /** Adds fonts to the skin.
     *
     * Gives the fonts names that are used when getting them from the skin.
     */
    public void addFonts() {
        add("answerBoxFont", createFont("lato", 14.2f, Color.BLACK));
        add("questionBoxFont", createFont("lato", 19f ,Color.BLACK));
        add("meterCaptionFont", createFont("latoItalic", 14.2f, Color.BLACK));
        add("imageCaptionFont", createFont("latoItalic", 19f, Color.BLACK));
        add("menuFont", createFont("latoLight", 26,  Color.BLACK));
        add("dayFont", createFont("latoMedium", 19f, Color.WHITE));
        add("emotionScoreFont", createFont("latoMediumItalic", 14.2f, Color.BLACK));
    }

    /** Adds textures to the skin.
     *
     * Gives the textures names that are used when getting them from the skin.
     */
    public void createStyles() {
        Label.LabelStyle menuText = new Label.LabelStyle();
        menuText.background = newDrawable("white", Color.CLEAR);
        menuText.font = getFont("menuFont");

        Label.LabelStyle answerBoxText = new Label.LabelStyle();
        answerBoxText.background = newDrawable("white", Color.CLEAR);
        answerBoxText.font = getFont("answerBoxFont");

        Label.LabelStyle questionBoxText = new Label.LabelStyle();
        questionBoxText.background = newDrawable("white", Color.CLEAR);
        questionBoxText.font = getFont("questionBoxFont");

        Label.LabelStyle emotionScoreText = new Label.LabelStyle();
        emotionScoreText.background = newDrawable("white", Color.CLEAR);
        emotionScoreText.font = getFont("emotionScoreFont");

        Label.LabelStyle dayText = new Label.LabelStyle();
        dayText.background = newDrawable("white", Color.CLEAR);
        dayText.font = getFont("dayFont");

        Label.LabelStyle imageCaption = new Label.LabelStyle();
        imageCaption.background = newDrawable("white", Color.CLEAR);
        imageCaption.font = getFont("imageCaptionFont");

        Label.LabelStyle meterCaption = new Label.LabelStyle();
        meterCaption.background = newDrawable("white", Color.CLEAR);
        meterCaption.font = getFont("meterCaptionFont");

        Button.ButtonStyle settingsButtonStyle = new Button.ButtonStyle();
        settingsButtonStyle.up = newDrawable("settings");
        settingsButtonStyle.down = newDrawable("settings_pressed", Color.WHITE);
        settingsButtonStyle.checked = newDrawable("settings_pressed", Color.WHITE);

        Button.ButtonStyle soundStyle = new Button.ButtonStyle();
        soundStyle.up = newDrawable("soundOff");
        soundStyle.down = newDrawable("soundOff", secondaryColor);
        soundStyle.checked = newDrawable("soundOn");
        soundStyle.checkedDown = newDrawable("soundOn", secondaryColor);

        Button.ButtonStyle musicStyle = new Button.ButtonStyle();
        musicStyle.up = newDrawable("musicOff");
        musicStyle.down = newDrawable("musicOff", secondaryColor);
        musicStyle.checked = newDrawable("musicOn");
        musicStyle.checkedDown = newDrawable("musicOn", secondaryColor);

        Button.ButtonStyle exitStyle = new Button.ButtonStyle();
        exitStyle.up = newDrawable("mainMenu");
        exitStyle.down = newDrawable("mainMenu", secondaryColor);

        Button.ButtonStyle happinessButtonStyle = new Button.ButtonStyle();
        happinessButtonStyle.up = newDrawable("happinessButton");
        happinessButtonStyle.down = newDrawable("happinessButtonPressed");
        happinessButtonStyle.checked = newDrawable("happinessButtonPressed");

        Button.ButtonStyle sadnessButtonStyle = new Button.ButtonStyle();
        sadnessButtonStyle.up = newDrawable("sadnessButton");
        sadnessButtonStyle.down = newDrawable("sadnessButtonPressed");
        sadnessButtonStyle.checked = newDrawable("sadnessButtonPressed");

        Button.ButtonStyle angerButtonStyle = new Button.ButtonStyle();
        angerButtonStyle.up = newDrawable("angerButton");
        angerButtonStyle.down = newDrawable("angerButtonPressed");
        angerButtonStyle.checked = newDrawable("angerButtonPressed");

        Button.ButtonStyle loveButtonStyle = new Button.ButtonStyle();
        loveButtonStyle.up = newDrawable("loveButton");
        loveButtonStyle.down = newDrawable("loveButtonPressed");
        loveButtonStyle.checked = newDrawable("loveButtonPressed");

        Button.ButtonStyle disgustButtonStyle = new Button.ButtonStyle();
        disgustButtonStyle.up = newDrawable("disgustButton");
        disgustButtonStyle.down = newDrawable("disgustButtonPressed");
        disgustButtonStyle.checked = newDrawable("disgustButtonPressed");

        Button.ButtonStyle fearButtonStyle = new Button.ButtonStyle();
        fearButtonStyle.up = newDrawable("fearButton");
        fearButtonStyle.down = newDrawable("fearButtonPressed");
        fearButtonStyle.checked = newDrawable("fearButtonPressed");

        Button.ButtonStyle astonishmentButtonStyle = new Button.ButtonStyle();
        astonishmentButtonStyle.up = newDrawable("astonishmentButton");
        astonishmentButtonStyle.down = newDrawable("astonishmentButtonPressed");
        astonishmentButtonStyle.checked = newDrawable("astonishmentButtonPressed");

        add("answerBoxText", answerBoxText);
        add("questionBoxText", questionBoxText);
        add("emotionScoreText", emotionScoreText);
        add("dayText", dayText);
        add("imageCaptionText", imageCaption);
        add("meterCaptionText", meterCaption);
        add("menuBoxText", menuText);
        add("settings", settingsButtonStyle);
        add("sound", soundStyle);
        add("music", musicStyle);
        add("exit", exitStyle);
        add("anger", angerButtonStyle);
        add("astonishment", astonishmentButtonStyle);
        add("disgust", disgustButtonStyle);
        add("fear", fearButtonStyle);
        add("happiness", happinessButtonStyle);
        add("love", loveButtonStyle);
        add("sadness", sadnessButtonStyle);
    }
    public String getPath(String texture) {
        String pixelDensity = getPixelDensity();
        String folderToUse = pixelDensity + "/";
        String suffix = pixelDensity + ".png";
        return folderToUse + texture + suffix;
    }

    /** getPixelDensity method is used to show correct images
     *
     * This method uses getDensity() method to determine screen pixel density
     * and sets the correct folder to which the images in the project are being used from.
     *
     * @return returns a string that is used to choose right folder for images
     * @author Mika Kivennenä
     */
    public String getPixelDensity() {
        float density = Gdx.graphics.getDensity();
        String result = "xxxhdpi";
        if (density <= 3) {
            result = "xxhdpi";
        }
        if (density <= 2) {
            result = "xhdpi";
        }
        if (density <= 1.5f) {
            result = "hdpi";
        }
        if (density <= 1) {
            result = "mdpi";
        }
        if (density <= 0.75f) {
            result = "ldpi";
        }
        return result;
    }

    /** createFont creates and returns a BitmapFont to be used
     *
     * This method uses FreeTypeFont to create a BitmapFont from an existing font file in the project font folder.
     * @return returns a BitmapFont
     * @author Mika Kivennenä
     */
    public BitmapFont createFont(String nameOfFont, float size, Color color) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/"+nameOfFont+".ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = (int)(size * Gdx.graphics.getDensity());
        fontParameter.borderColor = color;
        fontParameter.color = color;

        BitmapFont font = fontGenerator.generateFont(fontParameter);
        return font;
    }
}
