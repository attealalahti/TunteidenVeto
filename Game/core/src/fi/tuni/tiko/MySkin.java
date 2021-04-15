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

    private Color secondaryColor = MainGame.colorMax255(234, 158, 128);

    public MySkin() {
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

        add("answer_box", new Texture(getPath("box")));
        add("highlighted_box", new Texture(getPath("box3")));
        add("immobile_box", new Texture(getPath("box2")));
        add("questionBox", new Texture(getPath("textbox")));
        add("bigQuestionBox", new Texture(getPath("bigtextbox")));
        add("biggerQuestionBox", new Texture(getPath("biggertextbox")));
        add("arrow_box", new Texture(getPath("arrowbox")));
        add("white", new Texture(pixmap));
        add("default", new BitmapFont());
        add("settings", new Texture(getPath("hamburgermenu")));
        add("mainMenu", new Texture(getPath("mainmenubutton")));
        add("empty", new Texture(getPath("button")));
        add("meter", new Texture(getPath("meter")));
        add("soundOn", new Texture(getPath("sound_on")));
        add("soundOff", new Texture(getPath("sound_off")));
        add("musicOn", new Texture(getPath("music_on")));
        add("musicOff", new Texture(getPath("music_off")));
        add("rail_img", new Texture(getPath("rail")));

        add("defaultFont", createFont("lato", Color.BLACK)); //<<<<<<<<<<<<<<<<<<<<------------- TÄÄÄLLÄ ON FONTIT MIKA
        add("mediumFont", createFont("latoMedium", Color.WHITE));
        add("mediumItalicFont", createFont("latoMediumItalic", Color.BLACK));
        add("italicFont", createFont("latoItalic", Color.BLACK));
        add("lightFont", createFont("latoLight", Color.BLACK));
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
        add("angerBody", new Texture(getPath("anger"+modification)));
        add("astonishmentBody", new Texture(getPath("astonishment"+modification)));
        add("disgustBody", new Texture(getPath("disgust"+modification)));
        add("fearBody", new Texture(getPath("fear"+modification)));
        add("happinessBody", new Texture(getPath("joy"+modification)));
        add("loveBody", new Texture(getPath("love"+modification)));
        add("sadnessBody", new Texture(getPath("sadness"+modification)));
        add("settings_pressed", new Texture(getPath("hamburgermenu_pressed")));

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
    @Override
    public void dispose() {
        super.dispose();

    }
}
