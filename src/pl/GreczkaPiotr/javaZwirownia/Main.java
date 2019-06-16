package pl.GreczkaPiotr.javaZwirownia;

import pl.GreczkaPiotr.javaZwirownia.menu.Menu;
import pl.GreczkaPiotr.javaZwirownia.menu.MenuSettings;

public class Main {
    public static void main(String[] args) {
        Menu.DisplayMenu("Witamy w Systemie Obsługi Żwirowni!","",new MenuSettings());

        Functions.WaitFor(750);

        GravelPitManagment.main.Initialize();
        GravelPitManagment.main.MainMenu();

        Menu.DisplayMenu("Dziękujemy za skorzystanie z programu!", "Wyłączanie aplikacji...", new MenuSettings());//when gets quit command
        Functions.WaitFor(1100);
        System.exit(0);
    }
}