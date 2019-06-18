package pl.greczkapiotr.javazwirownia;

import pl.greczkapiotr.javazwirownia.menu.Menu;
import pl.greczkapiotr.javazwirownia.menu.MenuSettings;

public class Main {
    public static void main(String[] args) {
        Menu.DisplayMenu("Witamy w Systemie Obsługi Żwirowni!","",new MenuSettings());

        Functions.WaitFor(750);

        IGravelPitSystem gravelPitSystem = new GravelPitManagement();

        gravelPitSystem.Initialize();
        gravelPitSystem.MainMenu();

        Menu.DisplayMenu("Dziękujemy za skorzystanie z programu!", "Wyłączanie aplikacji...", new MenuSettings());//when gets quit command
        Functions.WaitFor(1100);
        System.exit(0);
    }
}