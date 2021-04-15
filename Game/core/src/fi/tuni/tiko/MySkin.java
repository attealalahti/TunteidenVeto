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

public class MySkin extends Skin {
    private Texture answerBoxTexture;
    private Texture answerBoxHighlightedTexture;
    private Texture immobileBoxTexture;
    private Texture questionBoxTexture;
    private Texture bigQuestionBoxTexture;
    private Texture biggerQuestionBoxTexture;
    private Texture settingsTexture;
    private Texture settingsPressedTexture;
    private Texture mainMenuTexture;
    private Texture arrowBoxTexture;
    private Texture empty;
    private Texture feelingMeterTexture;
    private Texture musicOnTexture;
    private Texture musicOffTexture;
    private Texture soundOnTexture;
    private Texture soundOffTexture;
    private Texture railTexture;
    private Texture angerTexture;
    private Texture astonishmentTexture;
    private Texture disgustTexture;
    private Texture fearTexture;
    private Texture happinessTexture;
    private Texture loveTexture;
    private Texture sadnessTexture;
    private Texture angerButtonTexture;
    private Texture astonishmentButtonTexture;
    private Texture disgustButtonTexture;
    private Texture fearButtonTexture;
    private Texture happinessButtonTexture;
    private Texture loveButtonTexture;
    private Texture sadnessButtonTexture;
    private Texture angerButtonPressedTexture;
    private Texture astonishmentButtonPressedTexture;
    private Texture disgustButtonPressedTexture;
    private Texture fearButtonPressedTexture;
    private Texture happinessButtonPressedTexture;
    private Texture loveButtonPressedTexture;
    private Texture sadnessButtonPressedTexture;
    private Texture angerBodyTexture;
    private Texture astonishmentBodyTexture;
    private Texture disgustBodyTexture;
    private Texture fearBodyTexture;
    private Texture happinessBodyTexture;
    private Texture loveBodyTexture;
    private Texture sadnessBodyTexture;
    private Color secondaryColor = MainGame.colorMax255(234, 158, 128);

    public MySkin() {
        answerBoxTexture = new Texture(getPath("box"));
        immobileBoxTexture = new Texture(getPath("box2"));
        answerBoxHighlightedTexture = new Texture(getPath("box3"));
        arrowBoxTexture = new Texture(getPath("arrowbox"));
        questionBoxTexture = new Texture(getPath("textbox"));
        bigQuestionBoxTexture = new Texture(getPath("bigtextbox"));
        biggerQuestionBoxTexture = new Texture(getPath("biggertextbox"));
        settingsTexture = new Texture(getPath("hamburgermenu"));
        settingsPressedTexture = new Texture(getPath("hamburgermenu_pressed"));
        mainMenuTexture = new Texture(getPath("mainmenubutton"));
        empty = new Texture(getPath("button"));
        feelingMeterTexture = new Texture(getPath("meter"));
        musicOnTexture = new Texture(getPath("music_on"));
        musicOffTexture = new Texture(getPath("music_off"));
        soundOnTexture = new Texture(getPath("sound_on"));
        soundOffTexture = new Texture(getPath("sound_off"));
        railTexture = new Texture(getPath("rail"));
        angerTexture = new Texture(getPath("anger"));
        astonishmentTexture = new Texture(getPath("astonishment"));
        disgustTexture = new Texture(getPath("disgust"));
        fearTexture = new Texture(getPath("fear"));
        happinessTexture = new Texture(getPath("joy"));
        loveTexture = new Texture(getPath("love"));
        sadnessTexture = new Texture(getPath("sadness"));
        String modification = "_button";
        angerButtonTexture = new Texture(getPath("anger"+modification));
        astonishmentButtonTexture = new Texture(getPath("astonishment"+modification));
        disgustButtonTexture = new Texture(getPath("disgust"+modification));
        fearButtonTexture = new Texture(getPath("fear"+modification));
        happinessButtonTexture = new Texture(getPath("joy"+modification));
        loveButtonTexture = new Texture(getPath("love"+modification));
        sadnessButtonTexture = new Texture(getPath("sadness"+modification));
        modification = "_pressed";
        angerButtonPressedTexture = new Texture(getPath("anger"+modification));
        astonishmentButtonPressedTexture = new Texture(getPath("astonishment"+modification));
        disgustButtonPressedTexture = new Texture(getPath("disgust"+modification));
        fearButtonPressedTexture = new Texture(getPath("fear"+modification));
        happinessButtonPressedTexture = new Texture(getPath("joy"+modification));
        loveButtonPressedTexture = new Texture(getPath("love"+modification));
        sadnessButtonPressedTexture = new Texture(getPath("sadness"+modification));
        modification = "_body";
        angerBodyTexture = new Texture(getPath("anger"+modification));
        astonishmentBodyTexture = new Texture(getPath("astonishment"+modification));
        disgustBodyTexture = new Texture(getPath("disgust"+modification));
        fearBodyTexture = new Texture(getPath("fear"+modification));
        happinessBodyTexture = new Texture(getPath("joy"+modification));
        loveBodyTexture = new Texture(getPath("love"+modification));
        sadnessBodyTexture = new Texture(getPath("sadness"+modification));

        createSkin();
    }

    public String getPath(String texture) {
        String pixelDensity = getPixelDensity();
        String folderToUse = pixelDensity + "/";
        String suffix = pixelDensity + ".png";
        return folderToUse + texture + suffix;
    }

    public void createSkin() {
        Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        add("answer_box", answerBoxTexture);
        add("highlighted_box", answerBoxHighlightedTexture);
        add("immobile_box", immobileBoxTexture);
        add("questionBox", questionBoxTexture);
        add("bigQuestionBox", bigQuestionBoxTexture);
        add("biggerQuestionBox", biggerQuestionBoxTexture);
        add("arrow_box", arrowBoxTexture);
        add("white", new Texture(pixmap));
        add("default", new BitmapFont());
        add("settings", settingsTexture);
        add("mainMenu", mainMenuTexture);
        add("empty", empty);
        add("meter", feelingMeterTexture);
        add("soundOn", soundOnTexture);
        add("soundOff", soundOffTexture);
        add("musicOn", musicOnTexture);
        add("musicOff", musicOffTexture);
        add("rail_img", railTexture);
        add("defaultFont", createFont("lato", Color.BLACK)); //<<<<<<<<<<<<<<<<<<<<------------- TÄÄÄLLÄ ON FONTIT MIKA
        add("mediumFont", createFont("latoMedium", Color.WHITE));
        add("mediumItalicFont", createFont("latoMediumItalic", Color.BLACK));
        add("italicFont", createFont("latoItalic", Color.BLACK));
        add("anger", angerTexture);
        add("astonishment", astonishmentTexture);
        add("disgust", disgustTexture);
        add("fear", fearTexture);
        add("happiness", happinessTexture);
        add("love", loveTexture);
        add("sadness", sadnessTexture);
        add("angerButton", angerButtonTexture);
        add("astonishmentButton", astonishmentButtonTexture);
        add("disgustButton", disgustButtonTexture);
        add("fearButton", fearButtonTexture);
        add("happinessButton", happinessButtonTexture);
        add("loveButton", loveButtonTexture);
        add("sadnessButton", sadnessButtonTexture);
        add("angerButtonPressed", angerButtonPressedTexture);
        add("astonishmentButtonPressed", astonishmentButtonPressedTexture);
        add("disgustButtonPressed", disgustButtonPressedTexture);
        add("fearButtonPressed", fearButtonPressedTexture);
        add("happinessButtonPressed", happinessButtonPressedTexture);
        add("loveButtonPressed", loveButtonPressedTexture);
        add("sadnessButtonPressed", sadnessButtonPressedTexture);
        add("angerBody", angerBodyTexture);
        add("astonishmentBody", astonishmentBodyTexture);
        add("disgustBody", disgustBodyTexture);
        add("fearBody", fearBodyTexture);
        add("happinessBody", happinessBodyTexture);
        add("loveBody", loveBodyTexture);
        add("sadnessBody", sadnessBodyTexture);
        add("settings_pressed", settingsPressedTexture);

        Label.LabelStyle answerStyle = new Label.LabelStyle();
        answerStyle.background = newDrawable("answer_box");
        answerStyle.font = getFont("default");

        Label.LabelStyle answerHighlightedStyle = new Label.LabelStyle();
        answerHighlightedStyle.background = newDrawable("highlighted_box");
        answerHighlightedStyle.font = getFont("default");

        Label.LabelStyle immobileAnswerStyle = new Label.LabelStyle();
        immobileAnswerStyle.background = newDrawable("immobile_box");
        immobileAnswerStyle.font = getFont("mediumItalicFont");

        Label.LabelStyle questionStyle = new Label.LabelStyle();
        questionStyle.background = newDrawable("questionBox");
        questionStyle.font = getFont("defaultFont");

        Label.LabelStyle questionStyleBig = new Label.LabelStyle();
        questionStyleBig.background = newDrawable("bigQuestionBox");
        questionStyleBig.font = getFont("defaultFont");

        Label.LabelStyle questionStyleBigger = new Label.LabelStyle();
        questionStyleBigger.background = newDrawable("biggerQuestionBox");
        questionStyleBigger.font = getFont("defaultFont");

        Label.LabelStyle arrowStyle = new Label.LabelStyle();
        arrowStyle.background = newDrawable("arrow_box");
        arrowStyle.font = getFont("default");

        Label.LabelStyle textBoxStyle = new Label.LabelStyle();
        textBoxStyle.background = newDrawable("white", Color.CLEAR);
        textBoxStyle.font = getFont("defaultFont");

        Label.LabelStyle emotionScore = new Label.LabelStyle();
        emotionScore.background = newDrawable("white", Color.CLEAR);
        emotionScore.font = getFont("mediumItalicFont");

        Label.LabelStyle dayText = new Label.LabelStyle();
        dayText.background = newDrawable("white", Color.CLEAR);
        dayText.font = getFont("mediumFont");

        Label.LabelStyle imageCaption = new Label.LabelStyle();
        imageCaption.background = newDrawable("white", Color.CLEAR);
        imageCaption.font = getFont("italicFont");

        Button.ButtonStyle feelingsButtonStyle = new Button.ButtonStyle();
        feelingsButtonStyle.up = newDrawable("empty");
        feelingsButtonStyle.down = newDrawable("empty", Color.DARK_GRAY);

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

        Label.LabelStyle feelingMeterForeGroundStyle = new Label.LabelStyle();
        feelingMeterForeGroundStyle.font = getFont("default");
        feelingMeterForeGroundStyle.background = newDrawable("meter");

        Label.LabelStyle railStyle = new Label.LabelStyle();
        railStyle.font = getFont("default");
        railStyle.background = newDrawable("rail_img");

        Label.LabelStyle angerStyle = new Label.LabelStyle();
        angerStyle.font = getFont("default");
        angerStyle.background = newDrawable("anger");

        Label.LabelStyle astonishmentStyle = new Label.LabelStyle();
        astonishmentStyle.font = getFont("default");
        astonishmentStyle.background = newDrawable("astonishment");

        Label.LabelStyle disgustStyle = new Label.LabelStyle();
        disgustStyle.font = getFont("default");
        disgustStyle.background = newDrawable("disgust");

        Label.LabelStyle fearStyle = new Label.LabelStyle();
        fearStyle.font = getFont("default");
        fearStyle.background = newDrawable("fear");

        Label.LabelStyle happinessStyle = new Label.LabelStyle();
        happinessStyle.font = getFont("default");
        happinessStyle.background = newDrawable("happiness");

        Label.LabelStyle loveStyle = new Label.LabelStyle();
        loveStyle.font = getFont("default");
        loveStyle.background = newDrawable("love");

        Label.LabelStyle sadnessStyle = new Label.LabelStyle();
        sadnessStyle.font = getFont("default");
        sadnessStyle.background = newDrawable("sadness");

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

        Label.LabelStyle angerBodyStyle = new Label.LabelStyle();
        angerBodyStyle.font = getFont("default");
        angerBodyStyle.background = newDrawable("angerBody");

        Label.LabelStyle astonishmentBodyStyle = new Label.LabelStyle();
        astonishmentBodyStyle.font = getFont("default");
        astonishmentBodyStyle.background = newDrawable("astonishmentBody");

        Label.LabelStyle disgustBodyStyle = new Label.LabelStyle();
        disgustBodyStyle.font = getFont("default");
        disgustBodyStyle.background = newDrawable("disgustBody");

        Label.LabelStyle fearBodyStyle = new Label.LabelStyle();
        fearBodyStyle.font = getFont("default");
        fearBodyStyle.background = newDrawable("fearBody");

        Label.LabelStyle happinessBodyStyle = new Label.LabelStyle();
        happinessBodyStyle.font = getFont("default");
        happinessBodyStyle.background = newDrawable("happinessBody");

        Label.LabelStyle loveBodyStyle = new Label.LabelStyle();
        loveBodyStyle.font = getFont("default");
        loveBodyStyle.background = newDrawable("loveBody");

        Label.LabelStyle sadnessBodyStyle = new Label.LabelStyle();
        sadnessBodyStyle.font = getFont("default");
        sadnessBodyStyle.background = newDrawable("sadnessBody");




        add("answer_movable", answerStyle);
        add("answer_static", immobileAnswerStyle);
        add("answer_highlighted", answerHighlightedStyle);
        add("question", questionStyle);
        add("bigQuestion", questionStyleBig);
        add("biggerQuestion", questionStyleBigger);
        add("arrow", arrowStyle);
        add("feelings", feelingsButtonStyle);
        add("text", textBoxStyle);
        add("emotionScoreText", emotionScore);
        add("dayText", dayText);
        add("imageCaptionText", imageCaption);
        add("settings", settingsButtonStyle);
        add("sound", soundStyle);
        add("music", musicStyle);
        add("exit", exitStyle);
        add("feelingMeterForeground", feelingMeterForeGroundStyle);
        add("rail", railStyle);
        add("anger", angerStyle);
        add("astonishment", astonishmentStyle);
        add("disgust", disgustStyle);
        add("fear", fearStyle);
        add("happiness", happinessStyle);
        add("love", loveStyle);
        add("sadness", sadnessStyle);
        add("anger", angerButtonStyle);
        add("astonishment", astonishmentButtonStyle);
        add("disgust", disgustButtonStyle);
        add("fear", fearButtonStyle);
        add("happiness", happinessButtonStyle);
        add("love", loveButtonStyle);
        add("sadness", sadnessButtonStyle);
        add("anger_body", angerBodyStyle);
        add("astonishment_body", astonishmentBodyStyle);
        add("disgust_body", disgustBodyStyle);
        add("fear_body", fearBodyStyle);
        add("joy_body", happinessBodyStyle);
        add("love_body", loveBodyStyle);
        add("sadness_body", sadnessBodyStyle);
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
        String tempString = "";

        if(density < 1) {
            tempString = "ldpi";
        }
        else if(density >= 1f && density < 2f) {
            tempString = "mdpi";
        }
        else if(density >= 2f && density < 3f) {
            tempString = "hdpi";
        }
        else if(density >= 3f && density < 4f) {
            tempString = "xhdpi";
        }

        return tempString;
    }

    /** createFont creates and returns a BitmapFont to be used
     *
     * This method uses FreeTypeFont to create a BitmapFont from an existing font file in the project font folder.
     * @return returns a BitmapFont
     * @author Mika Kivennenä
     */
    public BitmapFont createFont(String nameOfFont, Color color) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/"+nameOfFont+".ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();


        fontParameter.size = getFontSize();
        fontParameter.borderColor = color;
        fontParameter.color = color;

        BitmapFont font = fontGenerator.generateFont(fontParameter);
        return font;
    }

    /** getFontSize method is used to set size for the font
     *
     * This method uses getDensity() method to determine screen pixel density
     * and calculates the correct font size based on pixel density
     *
     * @return returns an integer that is used to set fontSize
     * @author Mika Kivennenä
     */
    public int getFontSize() {
        float density = Gdx.graphics.getDensity();
        int tempInt = 0;

        if(density < 1) {
            tempInt = 120;
        } else {
            tempInt = 60 * (int)density;
        }

        return tempInt;
    }
}
