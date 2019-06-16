package pl.GreczkaPiotr.javaZwirownia.order;

import pl.GreczkaPiotr.javaZwirownia.Functions;
import pl.GreczkaPiotr.javaZwirownia.material.Material;
import pl.GreczkaPiotr.javaZwirownia.material.SizeVariant;
import pl.GreczkaPiotr.javaZwirownia.material.MaterialTypes;
import pl.GreczkaPiotr.javaZwirownia.menu.*;

import java.util.*;

public class OrderMenu {
    public static void ShowOrderMenu(MenuSettings menuSettings, List<Order> ordersList, List<Material> warehouseList) {
        String s = "";

        String tempText, description;
        int id, type, size, quantity, sizeIdFrom, sizeIdTo;
        boolean takeFromEach = false;
        List<SizeVariant> tempSize = new ArrayList<>();
        Material tempMaterial = null;

        while (true) {
            Menu.DisplayMenu("Zamówienia", "1) (s)twórz zamówienie\n2) zamówienia do (r)ealizacji\n3) zamówienia (z)realizowane\n0) (p)owrót\n\nWpisz cyfrę znajdującą się po lewej stronie lub znak w nawiasach,\nby przejść do wybranej opcji.", true, menuSettings);

            s = Functions.GetInputString("Wybrana opcja", List.of("0", "p", "1", "2", "3", "s", "r", "z"));
            System.out.println("\n\n");
            switch (s) {
                case "1":
                case "s":
                    System.out.println("...");

                    tempText = "Wybierz typ surowca:\n";
                    for (int i = 0; i < warehouseList.size(); i++) {
                        tempText += (i + 1) + ") " + warehouseList.get(i).materialType + "\n";
                    }
                    Menu.DisplayMenu("Tworzenie zamówienia", tempText + "\nWpisz cyfrę znajdującą się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                    type = Functions.GetInputInt("Wybrany typ", new int[]{1, warehouseList.size()});

                    tempMaterial = warehouseList.get(type - 1);

                    System.out.println("\n\n");
                    tempText = "Rozmiar:\n1) wszystkie dostępne\n2) wybierz jeden\n3) wybierz przedział\n";
                    Menu.DisplayMenu("Tworzenie zamówienia", tempText + "\nWpisz cyfrę znajdującą się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                    size = Functions.GetInputInt("Opcje rozmiaru", List.of(1, 2, 3));

                    switch (size) {
                        case 1:
                            System.out.println("\n\n");
                            tempText = "Jak chcesz zrealizować zamówienie:\n1) odejmij łącznie ilość w miarę równomiernie\n2) odejmij ilość od każdego z osobna\n";
                            Menu.DisplayMenu("Tworzenie zamówienia", tempText + "\nWpisz cyfrę znajdującą się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                            takeFromEach = Functions.GetInputInt("Typ usuwania", List.of(1, 2)) == 2;
                            break;
                        case 2:
                            System.out.println("\n\n");
                            tempText = "Wybierz rozmiar:\n";
                            for (int i = 0; i < SizeVariant.values().length; i++) {
                                tempText += (i + 1) + ") " + SizeVariant.values()[i] + "\n";
                            }
                            Menu.DisplayMenu("Tworzenie zamówienia", tempText + "\nWpisz cyfrę znajdującą się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                            tempSize.add(SizeVariant.values()[Functions.GetInputInt("Wybrany rozmiar", new int[]{1, SizeVariant.values().length})]);
                            break;
                        case 3:
                        default:
                            System.out.println("\n\n");
                            tempText = "Jak chcesz zrealizować zamówienie:\n1) odejmij łącznie ilość w miarę równomiernie\n2) odejmij ilość od każdego z osobna\n";
                            Menu.DisplayMenu("Tworzenie zamówienia", tempText + "\nWpisz cyfrę znajdującą się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                            takeFromEach = Functions.GetInputInt("Typ usuwania", List.of(1, 2)) == 2;

                            System.out.println("\n\n");
                            tempText = "Wybierz początek przedziału rozmiaru:\n";
                            for (int i = 0; i < SizeVariant.values().length; i++) {
                                tempText += (i + 1) + ") " + SizeVariant.values()[i] + "\n";
                            }
                            Menu.DisplayMenu("Tworzenie zamówienia", tempText + "\nWpisz cyfrę znajdującą się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                            sizeIdFrom = Functions.GetInputInt("Wybrany rozmiar min", new int[]{1, SizeVariant.values().length}) - 1;

                            System.out.println("\n\n");
                            tempText = "Wybierz koniec przedziału rozmiaru:\n";
                            for (int i = sizeIdFrom; i < SizeVariant.values().length; i++) {
                                tempText += (i + 1) + ") " + SizeVariant.values()[i] + "\n";
                            }
                            Menu.DisplayMenu("Tworzenie zamówienia", tempText + "\nWpisz cyfrę znajdującą się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                            sizeIdTo = Functions.GetInputInt("Wybrany rozmiar max", new int[]{sizeIdFrom + 1, SizeVariant.values().length}) -1;

                            for(int i = sizeIdFrom; i < sizeIdTo; i++)
                                tempSize.add(SizeVariant.values()[i]);

                            break;
                    }

                    System.out.println("\n\n");
                    tempText = "Ilość surowca:\n";
                    Menu.DisplayMenu("Tworzenie zamówienia", tempText + "\nPodaj własną nieujemną wartość liczbową.", false, menuSettings);

                    quantity = Functions.GetInputInt("Wpisz ilość", new int[]{1,10000});

                    System.out.println("\n\n");
                    Menu.DisplayMenu("Tworzenie zamówienia", "Wpisz opis zamówienia (0-35 znaków)", menuSettings);
                    description = Functions.GetInputString("Wpisz opis", new int[]{0, 35});

                    ordersList.add(new Order(description, tempMaterial, tempSize, takeFromEach, quantity));

                    Functions.PressKeyToContinue();
                    break;
                case "2":
                case "r":
                    tempText = "Wybierz zamówienie do realizacji:\n";
                    List<Integer> idsList = new ArrayList<>();
                    for (int i = 0; i < ordersList.size(); i++) {
                        if (!ordersList.get(i).isRealized) {
                            idsList.add(i);
                            tempText += "ID: " + i + ") " + ordersList.get(i).orderDescription + " - zamówienie surowca: " + ordersList.get(i).materialTarget.materialType + " (jednostek: " + (ordersList.get(i).takeFromEach ? (ordersList.get(i).sizeVariant.size() == 0 ? "łącznie " + (ordersList.get(i).quantity * SizeVariant.values().length) + " (każdy rozmiar po " + ordersList.get(i).quantity + " sztuk)" : "łącznie " + (ordersList.get(i).quantity * ordersList.get(i).sizeVariant.size()) + " (każdy rozmiar po " + ordersList.get(i).quantity + " sztuk)") : "łącznie " + ordersList.get(i).quantity) + ")" + "\n";
                        }
                    }
                    Menu.DisplayMenu("Zamówienia do realizacji", tempText + "\nWpisz numer id zamówienia znajdujące się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                    id = Functions.GetInputInt("Wybrane zamówienie", idsList);

                    Menu.DisplayMenu("Zarządzanie zamówieniem", ordersList.get(id) + "\n\n1) (z)realizuj zamówienie\n2) (u)suń zamówienie\n0) (p)owrót\n\nWpisz cyfrę znajdującą się po lewej stronie lub znak w nawiasach,\nby przejść do wybranej opcji.", true, menuSettings);
                    s = Functions.GetInputString("Wybrana opcja", List.of("0", "p", "1", "2", "z", "u"));

                    switch (s) {
                        case "1":
                        case "z":
                            Boolean realizeState = ordersList.get(id).RealizeOrder();
                            if (realizeState == null)
                                System.out.println("Zamówienie nie zostało zrealizowane - żwirownia nie posiada takich materiałów!");
                            else if (!realizeState)
                                System.out.println("Brak wystarczających materiałów, aby zrealizować zamówienie!");
                            else
                                System.out.println("Zamówienia zostało zrealizowane!");

                            Functions.PressKeyToContinue();
                            break;
                        case "2":
                        case "u":
                            ordersList.remove(id);
                            System.out.println("Zamówienie zostało usunięte!");

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
                    break;
                case "3":
                case "z":
                    tempText = "Wybierz zrealizowane zamówienie:\n";
                    List<Integer> idRealizedList = new ArrayList<>();
                    for (int i = 0; i < ordersList.size(); i++) {
                        if (ordersList.get(i).isRealized) {
                            idRealizedList.add(i);
                            tempText += "ID: " + i + ") " + ordersList.get(i).orderDescription + " - zamówienie surowca: " + ordersList.get(i).materialTarget.materialType + " (jednostek do pobrania: " + (ordersList.get(i).takeFromEach ? (ordersList.get(i).sizeVariant.size() == 0 ? "łącznie " + (ordersList.get(i).quantity * SizeVariant.values().length) + " (każdy rozmiar po " + ordersList.get(i).quantity + " sztuk)" : "łącznie " + (ordersList.get(i).quantity * ordersList.get(i).sizeVariant.size()) + " (każdy rozmiar po " + ordersList.get(i).quantity + " sztuk)") : "łącznie" + ordersList.get(i).quantity) + ")" + "\n";
                        }
                    }
                    Menu.DisplayMenu("Zamówienia zrealizowane", tempText + "\nWpisz numer id zamówienia znajdujące się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                    id = Functions.GetInputInt("Wybrane zamówienie", idRealizedList);

                    Menu.DisplayMenu("Zarządzanie zamówieniem", ordersList.get(id) + "\n\n1) (u)suń zamówienie\n0) (p)owrót\n\nWpisz cyfrę znajdującą się po lewej stronie lub znak w nawiasach,\nby przejść do wybranej opcji.", true, menuSettings);
                    s = Functions.GetInputString("Wybrana opcja", Arrays.asList(new String[]{"0", "p", "1", "u"}));

                    switch (s) {
                        case "1":
                        case "u":
                            ordersList.remove(id);
                            System.out.println("Zamówienie zostało usunięte!");

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
