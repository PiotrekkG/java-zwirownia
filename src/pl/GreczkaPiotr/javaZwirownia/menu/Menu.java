package pl.GreczkaPiotr.javaZwirownia.menu;

public class Menu {
    /**
     * Wyświetla menu na podstawie podanych argumentów
     * @param title tytuł dla menu
     * @param description opis dla menu, jeśli nie jest pusty, to zostanie oddzielony od tytułu
     * @param showBottomBorder czy wyświetlić ramkę u dołu menu
     * @param preferredWidth jeśli mniejsze mniejsze niż 5, to dostosuje się na pewno do szerokości tekstu (tytułu)
     * */
    public static void DisplayMenu(String title, String description, boolean showBottomBorder, int preferredWidth, char horizontalBorderChar, char verticalBorderChar, char cornersChar) {
        DisplayMenu(title, description, showBottomBorder, new MenuSettings(preferredWidth, horizontalBorderChar, verticalBorderChar, cornersChar));
    }

    /**
     * Wyświetla menu na podstawie podanych argumentów pełne menu (z ramką u dołu)
     * @param title tytuł dla menu
     * @param description opis dla menu, jeśli nie jest pusty, to zostanie oddzielony od tytułu
     * @param menuSettings ustawienia wyglądu
     * */
    public static void DisplayMenu(String title, String description, MenuSettings menuSettings) {
        DisplayMenu(title, description, true, menuSettings);
    }

    /**
     * Wyświetla menu na podstawie podanych argumentów
     * @param title tytuł dla menu
     * @param description opis dla menu, jeśli nie jest pusty, to zostanie oddzielony od tytułu
     * @param showBottomBorder czy wyświetlić ramkę u dołu menu
     * @param menuSettings jeśli mniejsze mniejsze niż 5, to dostosuje się na pewno do szerokości tekstu (tytułu)
     * */
    public static void DisplayMenu(String title, String description, boolean showBottomBorder, MenuSettings menuSettings) {
        String output = "\n", borderTemplate = "";

        if (title.length() + 4 > menuSettings.preferredWidth)
            menuSettings.preferredWidth = title.length() + 4;

        borderTemplate += menuSettings.cornersChar;
        for (int i = 0; i < menuSettings.preferredWidth - 2; i++) {
            borderTemplate += menuSettings.horizontalBorderChar;
        }
        borderTemplate += menuSettings.cornersChar + "\n";

        output += borderTemplate;
        output += menuSettings.verticalBorderChar + " " + title;
        for (int i = 0; i < menuSettings.preferredWidth - title.length() - 4; i++) {
            output += " ";
        }
        output += " " + menuSettings.verticalBorderChar + "\n";

        if (description != "") {
            output += borderTemplate;

            String descriptionLines[] = description.split("\n");
            for (var line : descriptionLines) {
                if (line.length() == 0)
                    output += borderTemplate;
                else
                    output += menuSettings.verticalBorderChar + " " + line + "\n";
            }
        }

        if (showBottomBorder)
            output += borderTemplate;

        System.out.println(output);
    }
}
