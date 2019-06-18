package pl.greczkapiotr.javazwirownia.menu;

public class MenuSettings {
    public int preferredWidth;
    public char horizontalBorderChar;
    public char verticalBorderChar;
    public char cornersChar;

    public MenuSettings() {
        this(0, '=', '|', '+');
    }

    public MenuSettings(int preferredWidth) {
        this(preferredWidth, '=', '|', '+');
    }

    public MenuSettings(int preferredWidth, char horizontalBorderChar, char verticalBorderChar, char cornersChar) {
        this.preferredWidth = preferredWidth;
        this.horizontalBorderChar = horizontalBorderChar;
        this.verticalBorderChar = verticalBorderChar;
        this.cornersChar = cornersChar;
        //preferredWidth, horizontalBorderChar, verticalBorderChar, cornersChar
    }
}