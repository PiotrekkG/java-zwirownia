package pl.greczkapiotr.javazwirownia.order;

import pl.greczkapiotr.javazwirownia.material.Material;
import pl.greczkapiotr.javazwirownia.material.SizeVariant;
import pl.greczkapiotr.javazwirownia.Functions;
import pl.greczkapiotr.javazwirownia.menu.*;

import java.util.*;

public class OrderMenu {
    public static void ShowOrderMenu(MenuSettings menuSettings, List<Order> ordersList, List<Material> warehouseList) {
        String s;

        String tempText, description;
        int id, type, size, quantity, sizeIdFrom, sizeIdTo;
        boolean takeFromEach = false;
        List<SizeVariant> tempSize = new ArrayList<>();

        while (true) {
            Menu.DisplayMenu("Zamówienia", "1) (s)twórz zamówienie\n2) zamówienia do (r)ealizacji\n3) zamówienia (z)realizowane\n0) (p)owrót\n\nWpisz cyfrę znajdującą się po lewej stronie lub znak w nawiasach,\nby przejść do wybranej opcji.", true, menuSettings);

            s = Functions.GetInputString("Wybrana opcja", List.of("0", "p", "1", "2", "3", "s", "r", "z"));
            System.out.println("\n\n");
            switch (s) {

                case "1": //tworzenie zamówienia
                case "s":
                    System.out.println("...");

                    tempText = "Wybierz typ surowca:\n";
                    for (int i = 0; i < warehouseList.size(); i++) {
                        tempText += (i + 1) + ") " + warehouseList.get(i).materialType + "\n";
                    }
                    Menu.DisplayMenu("Tworzenie zamówienia", tempText + "\nWpisz cyfrę znajdującą się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                    type = Functions.GetInputInt("Wybrany typ", new int[]{1, warehouseList.size()});

                    Material tempMaterial = warehouseList.get(type - 1);

                    System.out.println("\n\n");
                    tempText = "Rozmiar:\n1) wybierz jeden rozmiar\n2) wybierz przedział rozmiarów\n3) wszystkie dostępne rozmiary\n";
                    Menu.DisplayMenu("Tworzenie zamówienia", tempText + "\nWpisz cyfrę znajdującą się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                    size = Functions.GetInputInt("Opcje rozmiaru", List.of(1, 2, 3));

                    switch (size) {
                        case 1: //jeden rozmiar
                            System.out.println("\n\n");
                            tempText = "Wybierz rozmiar:\n";
                            for (int i = 0; i < SizeVariant.values().length; i++) {
                                tempText += (i + 1) + ") " + SizeVariant.values()[i] + "\n";
                            }
                            Menu.DisplayMenu("Tworzenie zamówienia", tempText + "\nWpisz cyfrę znajdującą się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                            tempSize.add(SizeVariant.values()[Functions.GetInputInt("Wybrany rozmiar", new int[]{1, SizeVariant.values().length})]);
                            break;
                        case 2: //przedział
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
                            sizeIdTo = Functions.GetInputInt("Wybrany rozmiar max", new int[]{sizeIdFrom + 1, SizeVariant.values().length}) - 1;

                            for (int i = sizeIdFrom; i < sizeIdTo; i++) //dodajemy do tablicy tempSize rozmiary w podanym przedziale
                                tempSize.add(SizeVariant.values()[i]);

                            break;
                        default:
                        case 3: //wszystkie
                            System.out.println("\n\n");
                            tempText = "Jak chcesz zrealizować zamówienie:\n1) odejmij łącznie ilość w miarę równomiernie\n2) odejmij ilość od każdego z osobna\n";
                            Menu.DisplayMenu("Tworzenie zamówienia", tempText + "\nWpisz cyfrę znajdującą się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                            takeFromEach = Functions.GetInputInt("Typ usuwania", List.of(1, 2)) == 2;
                            break;
                    }

                    System.out.println("\n\n");
                    tempText = "Ilość surowca:\n";
                    Menu.DisplayMenu("Tworzenie zamówienia", tempText + "\nPodaj własną nieujemną wartość liczbową.", false, menuSettings);

                    quantity = Functions.GetInputInt("Wpisz ilość", new int[]{1, 1000000});

                    System.out.println("\n\n");
                    Menu.DisplayMenu("Tworzenie zamówienia", "Wpisz opis zamówienia (0-65 znaków)", menuSettings);
                    description = Functions.GetInputString("Wpisz opis", new int[]{0, 65});

                    ordersList.add(new Order(description, tempMaterial, tempSize, takeFromEach, quantity));

                    Functions.PressKeyToContinue();
                    break;

                case "2": //zamówienia do realizacji
                case "r":
                    tempText = "Wybierz zamówienie do realizacji:\n-1) powrót do menu\n";
                    List<Integer> idsList = new ArrayList<>();
                    for (int i = 0; i < ordersList.size(); i++) {
                        Order o = ordersList.get(i);
                        if (!o.isRealized) {
                            idsList.add(i);
                            tempText += "ID: " + i + ") " + o.orderDescription + " - zamówienie surowca: " + o.materialTarget.materialType + " [jednostek: " + o.shortUnitDescription() + "]" + "\n";
                        }
                    }
                    if (idsList.size() == 0) {
                        Menu.DisplayMenu("Zamówienia do realizacji", "Lista zamówień do realizacji jest pusta.", menuSettings);
                        Functions.PressKeyToContinue();
                    } else {
                        idsList.add(-1);
                        Menu.DisplayMenu("Zamówienia do realizacji", tempText + "\nWpisz numer id zamówienia znajdujące się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                        id = Functions.GetInputInt("Wybrane zamówienie", idsList);

                        if (id != -1) {
                            Order o = ordersList.get(id);
                            Menu.DisplayMenu("Zarządzanie zamówieniem", o + "\n\n1) (z)realizuj zamówienie\n2) (u)suń zamówienie\n0) (p)owrót\n\nWpisz cyfrę znajdującą się po lewej stronie lub znak w nawiasach,\nby przejść do wybranej opcji.", true, menuSettings);
                            s = Functions.GetInputString("Wybrana opcja", List.of("0", "p", "1", "2", "z", "u"));

                            switch (s) {
                                case "1":
                                case "z":
                                    o.RealizeOrder();

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
                                    System.out.println(":Powrót do menu zamówień:");
                                    break;
                                default: //nigdy nie powinien do tego momentu program dojść - GetInputString filtruje
                                    System.out.println("Błędny wybór.");
                                    break;
                            }
                        }
                    }
                    break;

                case "3": //zamówienia zrelizowane
                case "z":
                    tempText = "Wybierz zrealizowane zamówienie:\n-1) powrót do menu\n";
                    List<Integer> idsRealizedList = new ArrayList<>();
                    for (int i = 0; i < ordersList.size(); i++) {
                        Order o = ordersList.get(i);
                        if (o.isRealized) {
                            idsRealizedList.add(i);
                            tempText += "ID: " + i + ") " + o.orderDescription + " - zamówienie surowca: " + o.materialTarget.materialType + " [jednostek do pobrania: " + o.shortUnitDescription() + "]" + "\n";
                        }
                    }
                    if (idsRealizedList.size() == 0) {
                        Menu.DisplayMenu("Zamówienia zrealizowane", "Lista zrealizowanych zamówień jest pusta.", menuSettings);
                        Functions.PressKeyToContinue();
                    } else {
                        idsRealizedList.add(-1);
                        Menu.DisplayMenu("Zamówienia zrealizowane", tempText + "\nWpisz numer id zamówienia znajdujące się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                        id = Functions.GetInputInt("Wybrane zamówienie", idsRealizedList);

                        if (id != -1) {
                            Menu.DisplayMenu("Zarządzanie zamówieniem", ordersList.get(id) + "\n\n1) (u)suń zamówienie\n0) (p)owrót\n\nWpisz cyfrę znajdującą się po lewej stronie lub znak w nawiasach,\nby przejść do wybranej opcji.", true, menuSettings);
                            s = Functions.GetInputString("Wybrana opcja", List.of("0", "p", "1", "u"));

                            switch (s) {
                                case "1":
                                case "u":
                                    ordersList.remove(id);
                                    System.out.println("Zamówienie zostało usunięte!");

                                    Functions.PressKeyToContinue();
                                    break;
                                case "0":
                                case "p":
                                    System.out.println(":Powrót do menu zamówień:");
                                    break;
                                default: //nigdy nie powinien do tego momentu program dojść - GetInputString filtruje
                                    System.out.println("Błędny wybór.");
                                    break;
                            }
                        }
                    }
                    break;

                case "0": //powrót
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