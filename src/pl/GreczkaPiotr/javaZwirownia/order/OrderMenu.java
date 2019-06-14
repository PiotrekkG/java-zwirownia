package pl.GreczkaPiotr.javaZwirownia.order;

import pl.GreczkaPiotr.javaZwirownia.Functions;
import pl.GreczkaPiotr.javaZwirownia.material.Material;
import pl.GreczkaPiotr.javaZwirownia.menu.*;

import java.util.List;

public class OrderMenu {
    public static void ShowOrderMenu(MenuSettings menuSettings, List<Order> ordersList, List<Material> warehouseList) {
        String s = "";
        while (true) {
            Menu.DisplayMenu("Zamówienia", "1) (s)twórz zamówienie\n2) zamówienia do (r)ealizacji\n3) zamówienia (z)realizowane\n0) (p)owrót\n\nWpisz cyfrę znajdującą się lewej stronie lub znak w nawiasach,\nby przejść do wybranej opcji.", true, menuSettings);

            s = Functions.GetInputString("Wybrana opcja", new String[]{"0", "p", "1", "2", "3", "s", "r", "z"});
            switch (s) {
                case "1":
                case "s":
                    System.out.println("Tworzenie zamówienia...");

                    Functions.PressKeyToContinue();
                    break;
                case "2":
                case "r":
                    System.out.println("Zamówienia do realizacji...");

                    Functions.PressKeyToContinue();
                    break;
                case "3":
                case "z":
                    System.out.println("Zamówienia zrealizowane...");

                    Functions.PressKeyToContinue();
                    break;
                case "0":
                case "p":
                    System.out.println(":Powrót do ekranu głównego:");
                    return;
                default: //nigdy nie powinien do tego momentu program dojść - GetInputString filtruje
                    System.out.println("Błędny wybór.");
                    break;
            }
        }
    }
}
