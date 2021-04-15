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
    private Texture img;
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

    public void addTexturesToStuff() {
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
    }

    public String getPath(String texture) {
        String pixelDensity = getPixelDensity();
        String folderToUse = pixelDensity + "/";
        String suffix = pixelDensity + ".png";
        return folderToUse + texture + suffix;
    }

    public Skin createSkin() {
        Skin s = new Skin();
        Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        s.add("answer_box", answerBoxTexture);
        s.add("highlighted_box", answerBoxHighlightedTexture);
        s.add("immobile_box", immobileBoxTexture);
        s.add("questionBox", questionBoxTexture);
        s.add("bigQuestionBox", bigQuestionBoxTexture);
        s.add("biggerQuestionBox", biggerQuestionBoxTexture);
        s.add("arrow_box", arrowBoxTexture);
        s.add("white", new Texture(pixmap));
        s.add("default", new BitmapFont());
        s.add("settings", settingsTexture);
        s.add("mainMenu", mainMenuTexture);
        s.add("empty", empty);
        s.add("meter", feelingMeterTexture);
        s.add("soundOn", soundOnTexture);
        s.add("soundOff", soundOffTexture);
        s.add("musicOn", musicOnTexture);
        s.add("musicOff", musicOffTexture);
        s.add("rail_img", railTexture);
        s.add("test", img);
        s.add("defaultFont", createFont("lato", Color.BLACK)); //<<<<<<<<<<<<<<<<<<<<------------- TÄÄÄLLÄ ON FONTIT MIKA
        s.add("mediumFont", createFont("latoMedium", Color.WHITE));
        s.add("mediumItalicFont", createFont("latoMediumItalic", Color.BLACK));
        s.add("italicFont", createFont("latoItalic", Color.BLACK));
        s.add("anger", angerTexture);
        s.add("astonishment", astonishmentTexture);
        s.add("disgust", disgustTexture);
        s.add("fear", fearTexture);
        s.add("happiness", happinessTexture);
        s.add("love", loveTexture);
        s.add("sadness", sadnessTexture);
        s.add("angerButton", angerButtonTexture);
        s.add("astonishmentButton", astonishmentButtonTexture);
        s.add("disgustButton", disgustButtonTexture);
        s.add("fearButton", fearButtonTexture);
        s.add("happinessButton", happinessButtonTexture);
        s.add("loveButton", loveButtonTexture);
        s.add("sadnessButton", sadnessButtonTexture);
        s.add("angerButtonPressed", angerButtonPressedTexture);
        s.add("astonishmentButtonPressed", astonishmentButtonPressedTexture);
        s.add("disgustButtonPressed", disgustButtonPressedTexture);
        s.add("fearButtonPressed", fearButtonPressedTexture);
        s.add("happinessButtonPressed", happinessButtonPressedTexture);
        s.add("loveButtonPressed", loveButtonPressedTexture);
        s.add("sadnessButtonPressed", sadnessButtonPressedTexture);
        s.add("angerBody", angerBodyTexture);
        s.add("astonishmentBody", astonishmentBodyTexture);
        s.add("disgustBody", disgustBodyTexture);
        s.add("fearBody", fearBodyTexture);
        s.add("happinessBody", happinessBodyTexture);
        s.add("loveBody", loveBodyTexture);
        s.add("sadnessBody", sadnessBodyTexture);




        s.add("settings_pressed", settingsPressedTexture);

        Label.LabelStyle answerStyle = new Label.LabelStyle();
        answerStyle.background = s.newDrawable("answer_box");
        answerStyle.font = s.getFont("default");

        Label.LabelStyle answerHighlightedStyle = new Label.LabelStyle();
        answerHighlightedStyle.background = s.newDrawable("highlighted_box");
        answerHighlightedStyle.font = s.getFont("default");

        Label.LabelStyle immobileAnswerStyle = new Label.LabelStyle();
        immobileAnswerStyle.background = s.newDrawable("immobile_box");
        immobileAnswerStyle.font = s.getFont("mediumItalicFont");

        Label.LabelStyle questionStyle = new Label.LabelStyle();
        questionStyle.background = s.newDrawable("questionBox");
        questionStyle.font = s.getFont("defaultFont");

        Label.LabelStyle questionStyleBig = new Label.LabelStyle();
        questionStyleBig.background = s.newDrawable("bigQuestionBox");
        questionStyleBig.font = s.getFont("defaultFont");

        Label.LabelStyle questionStyleBigger = new Label.LabelStyle();
        questionStyleBigger.background = s.newDrawable("biggerQuestionBox");
        questionStyleBigger.font = s.getFont("defaultFont");

        Label.LabelStyle arrowStyle = new Label.LabelStyle();
        arrowStyle.background = s.newDrawable("arrow_box");
        arrowStyle.font = s.getFont("default");

        Label.LabelStyle textBoxStyle = new Label.LabelStyle();
        textBoxStyle.background = s.newDrawable("white", Color.CLEAR);
        textBoxStyle.font = s.getFont("defaultFont");

        Label.LabelStyle emotionScore = new Label.LabelStyle();
        emotionScore.background = s.newDrawable("white", Color.CLEAR);
        emotionScore.font = s.getFont("mediumItalicFont");

        Label.LabelStyle dayText = new Label.LabelStyle();
        dayText.background = s.newDrawable("white", Color.CLEAR);
        dayText.font = s.getFont("mediumFont");

        Label.LabelStyle imageCaption = new Label.LabelStyle();
        imageCaption.background = s.newDrawable("white", Color.CLEAR);
        imageCaption.font = s.getFont("italicFont");

        Button.ButtonStyle feelingsButtonStyle = new Button.ButtonStyle();
        feelingsButtonStyle.up = s.newDrawable("empty");
        feelingsButtonStyle.down = s.newDrawable("empty", Color.DARK_GRAY);

        Button.ButtonStyle settingsButtonStyle = new Button.ButtonStyle();
        settingsButtonStyle.up = s.newDrawable("settings");
        settingsButtonStyle.down = s.newDrawable("settings_pressed", Color.WHITE);
        settingsButtonStyle.checked = s.newDrawable("settings_pressed", Color.WHITE);

        Button.ButtonStyle soundStyle = new Button.ButtonStyle();
        soundStyle.up = s.newDrawable("soundOff");
        soundStyle.down = s.newDrawable("soundOff", secondaryColor);
        soundStyle.checked = s.newDrawable("soundOn");
        soundStyle.checkedDown = s.newDrawable("soundOn", secondaryColor);

        Button.ButtonStyle musicStyle = new Button.ButtonStyle();
        musicStyle.up = s.newDrawable("musicOff");
        musicStyle.down = s.newDrawable("musicOff", secondaryColor);
        musicStyle.checked = s.newDrawable("musicOn");
        musicStyle.checkedDown = s.newDrawable("musicOn", secondaryColor);

        Button.ButtonStyle exitStyle = new Button.ButtonStyle();
        exitStyle.up = s.newDrawable("mainMenu");
        exitStyle.down = s.newDrawable("mainMenu", secondaryColor);

        Button.ButtonStyle buttonStyleAlt = new Button.ButtonStyle();
        buttonStyleAlt.up = s.newDrawable("test");
        buttonStyleAlt.down = s.newDrawable("test", Color.DARK_GRAY);

        Label.LabelStyle feelingMeterForeGroundStyle = new Label.LabelStyle();
        feelingMeterForeGroundStyle.font = s.getFont("default");
        feelingMeterForeGroundStyle.background = s.newDrawable("meter");

        Label.LabelStyle railStyle = new Label.LabelStyle();
        railStyle.font = s.getFont("default");
        railStyle.background = s.newDrawable("rail_img");

        Label.LabelStyle angerStyle = new Label.LabelStyle();
        angerStyle.font = s.getFont("default");
        angerStyle.background = s.newDrawable("anger");

        Label.LabelStyle astonishmentStyle = new Label.LabelStyle();
        astonishmentStyle.font = s.getFont("default");
        astonishmentStyle.background = s.newDrawable("astonishment");

        Label.LabelStyle disgustStyle = new Label.LabelStyle();
        disgustStyle.font = s.getFont("default");
        disgustStyle.background = s.newDrawable("disgust");

        Label.LabelStyle fearStyle = new Label.LabelStyle();
        fearStyle.font = s.getFont("default");
        fearStyle.background = s.newDrawable("fear");

        Label.LabelStyle happinessStyle = new Label.LabelStyle();
        happinessStyle.font = s.getFont("default");
        happinessStyle.background = s.newDrawable("happiness");

        Label.LabelStyle loveStyle = new Label.LabelStyle();
        loveStyle.font = s.getFont("default");
        loveStyle.background = s.newDrawable("love");

        Label.LabelStyle sadnessStyle = new Label.LabelStyle();
        sadnessStyle.font = s.getFont("default");
        sadnessStyle.background = s.newDrawable("sadness");

        Button.ButtonStyle happinessButtonStyle = new Button.ButtonStyle();
        happinessButtonStyle.up = s.newDrawable("happinessButton");
        happinessButtonStyle.down = s.newDrawable("happinessButtonPressed");
        happinessButtonStyle.checked = s.newDrawable("happinessButtonPressed");

        Button.ButtonStyle sadnessButtonStyle = new Button.ButtonStyle();
        sadnessButtonStyle.up = s.newDrawable("sadnessButton");
        sadnessButtonStyle.down = s.newDrawable("sadnessButtonPressed");
        sadnessButtonStyle.checked = s.newDrawable("sadnessButtonPressed");

        Button.ButtonStyle angerButtonStyle = new Button.ButtonStyle();
        angerButtonStyle.up = s.newDrawable("angerButton");
        angerButtonStyle.down = s.newDrawable("angerButtonPressed");
        angerButtonStyle.checked = s.newDrawable("angerButtonPressed");

        Button.ButtonStyle loveButtonStyle = new Button.ButtonStyle();
        loveButtonStyle.up = s.newDrawable("loveButton");
        loveButtonStyle.down = s.newDrawable("loveButtonPressed");
        loveButtonStyle.checked = s.newDrawable("loveButtonPressed");

        Button.ButtonStyle disgustButtonStyle = new Button.ButtonStyle();
        disgustButtonStyle.up = s.newDrawable("disgustButton");
        disgustButtonStyle.down = s.newDrawable("disgustButtonPressed");
        disgustButtonStyle.checked = s.newDrawable("disgustButtonPressed");

        Button.ButtonStyle fearButtonStyle = new Button.ButtonStyle();
        fearButtonStyle.up = s.newDrawable("fearButton");
        fearButtonStyle.down = s.newDrawable("fearButtonPressed");
        fearButtonStyle.checked = s.newDrawable("fearButtonPressed");

        Button.ButtonStyle astonishmentButtonStyle = new Button.ButtonStyle();
        astonishmentButtonStyle.up = s.newDrawable("astonishmentButton");
        astonishmentButtonStyle.down = s.newDrawable("astonishmentButtonPressed");
        astonishmentButtonStyle.checked = s.newDrawable("astonishmentButtonPressed");

        Label.LabelStyle angerBodyStyle = new Label.LabelStyle();
        angerBodyStyle.font = s.getFont("default");
        angerBodyStyle.background = s.newDrawable("angerBody");

        Label.LabelStyle astonishmentBodyStyle = new Label.LabelStyle();
        astonishmentBodyStyle.font = s.getFont("default");
        astonishmentBodyStyle.background = s.newDrawable("astonishmentBody");

        Label.LabelStyle disgustBodyStyle = new Label.LabelStyle();
        disgustBodyStyle.font = s.getFont("default");
        disgustBodyStyle.background = s.newDrawable("disgustBody");

        Label.LabelStyle fearBodyStyle = new Label.LabelStyle();
        fearBodyStyle.font = s.getFont("default");
        fearBodyStyle.background = s.newDrawable("fearBody");

        Label.LabelStyle happinessBodyStyle = new Label.LabelStyle();
        happinessBodyStyle.font = s.getFont("default");
        happinessBodyStyle.background = s.newDrawable("happinessBody");

        Label.LabelStyle loveBodyStyle = new Label.LabelStyle();
        loveBodyStyle.font = s.getFont("default");
        loveBodyStyle.background = s.newDrawable("loveBody");

        Label.LabelStyle sadnessBodyStyle = new Label.LabelStyle();
        sadnessBodyStyle.font = s.getFont("default");
        sadnessBodyStyle.background = s.newDrawable("sadnessBody");




        s.add("answer_movable", answerStyle);
        s.add("answer_static", immobileAnswerStyle);
        s.add("answer_highlighted", answerHighlightedStyle);
        s.add("question", questionStyle);
        s.add("bigQuestion", questionStyleBig);
        s.add("biggerQuestion", questionStyleBigger);
        s.add("arrow", arrowStyle);
        s.add("feelings", feelingsButtonStyle);
        s.add("alt", buttonStyleAlt);
        s.add("text", textBoxStyle);
        s.add("emotionScoreText", emotionScore);
        s.add("dayText", dayText);
        s.add("imageCaptionText", imageCaption);
        s.add("settings", settingsButtonStyle);
        s.add("sound", soundStyle);
        s.add("music", musicStyle);
        s.add("exit", exitStyle);
        s.add("feelingMeterForeground", feelingMeterForeGroundStyle);
        s.add("rail", railStyle);
        s.add("anger", angerStyle);
        s.add("astonishment", astonishmentStyle);
        s.add("disgust", disgustStyle);
        s.add("fear", fearStyle);
        s.add("happiness", happinessStyle);
        s.add("love", loveStyle);
        s.add("sadness", sadnessStyle);
        s.add("anger", angerButtonStyle);
        s.add("astonishment", astonishmentButtonStyle);
        s.add("disgust", disgustButtonStyle);
        s.add("fear", fearButtonStyle);
        s.add("happiness", happinessButtonStyle);
        s.add("love", loveButtonStyle);
        s.add("sadness", sadnessButtonStyle);
        s.add("anger_body", angerBodyStyle);
        s.add("astonishment_body", astonishmentBodyStyle);
        s.add("disgust_body", disgustBodyStyle);
        s.add("fear_body", fearBodyStyle);
        s.add("joy_body", happinessBodyStyle);
        s.add("love_body", loveBodyStyle);
        s.add("sadness_body", sadnessBodyStyle);



        return s;
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
