package pl.greczkapiotr.javazwirownia;

import pl.greczkapiotr.javazwirownia.material.*;
import pl.greczkapiotr.javazwirownia.menu.*;
import pl.greczkapiotr.javazwirownia.order.*;

import java.util.ArrayList;
import java.util.List;

public class GravelPitManagement implements IGravelPitSystem {
    public static GravelPitManagement main;

    public boolean silentMode = true; //ewentualne logi są niewidoczne przy inicjowaniu
    private List<Material> warehouseList = new ArrayList<>();
    private List<Order> orderList = new ArrayList<>();
    private MenuSettings menuSettings = new MenuSettings(0, '=', '|', '*');

    GravelPitManagement() { //w tym wypadku nie musi być publiczna
        main = this;
    }

    @Override
    public void Initialize() {
        String loadExample = Functions.GetInputString("Chcesz załadować dane przykładowe? t/n", List.of("t", "n", "g"));

        if (loadExample.equals("t")) {//jeśli "t", to wartości zostają wpisane jako przykład działania

            System.out.println("\nWprowadzanie wartości przykładowych do systemu...\n\n");

            //dodajemy do tablicy materiały jakie produkuje żwirownia, można je zainicjować z wartością/ilością początkową
            warehouseList.add(new Material(MaterialTypes.GravelMix));
            warehouseList.add(new Material(MaterialTypes.Sand, 54));
            warehouseList.add(new Material(MaterialTypes.Chippings));
            warehouseList.add(new Material(MaterialTypes.Rock, 186));
            warehouseList.add(new Material(MaterialTypes.Gravel, 25));
            warehouseList.add(new Material(MaterialTypes.ExampleType, 250));

            warehouseList.get(5).TakeFrom(73, SizeVariant.Small);
            warehouseList.get(5).TakeFrom(99, SizeVariant.Medium);
            warehouseList.get(5).TakeFrom(12, SizeVariant.Big);

            warehouseList.get(4).AddQuantity(7, SizeVariant.Small);
            warehouseList.get(4).AddQuantity(12, SizeVariant.Big);
            warehouseList.get(4).AddQuantity(24, SizeVariant.VeryBig);
            warehouseList.get(4).TakeFrom(2, SizeVariant.Medium);

            orderList.add(new Order("Zamówienie na pospółkę", warehouseList.get(0), true, 1));
            orderList.add(new Order("zamówienie grysu", warehouseList.get(2), 9));
            orderList.add(new Order("piasek pod drogę", warehouseList.get(1), SizeVariant.Big, 25));
            orderList.get(orderList.size() - 1).isRealized = true;
            orderList.add(new Order("kamień (różne rozmiary)", warehouseList.get(3), List.of(SizeVariant.VerySmall, SizeVariant.Small, SizeVariant.Medium, SizeVariant.Big), 17));
            orderList.add(new Order("zamówienie konkretnych ilości kamienia", warehouseList.get(3), List.of(SizeVariant.VerySmall, SizeVariant.Small, SizeVariant.Medium), true, 17));
            orderList.add(new Order("Zamówienie żwiru - konkretne ilości", warehouseList.get(4), List.of(SizeVariant.Small, SizeVariant.Medium, SizeVariant.Big), true, 25));
            orderList.add(new Order("Zamówienie żwiru - do pobrania łącznie", warehouseList.get(4), List.of(SizeVariant.Small, SizeVariant.Medium, SizeVariant.Big), 25 * 3));

        } else {

            //dodajemy do tablicy materiały jakie produkuje żwirownia, można je zainicjować z wartością/ilością początkową (dla każdego rozmiaru)
            warehouseList.add(new Material(MaterialTypes.Sand));
            warehouseList.add(new Material(MaterialTypes.Gravel));
            warehouseList.add(new Material(MaterialTypes.Rock));
            warehouseList.add(new Material(MaterialTypes.Chippings));
            warehouseList.add(new Material(MaterialTypes.GravelMix));

        }

    }

    @Override
    public void MainMenu() {
        String s;

        while (true) {
            Menu.DisplayMenu("System Obsługi Żwirowni", "1) (m)agazyn\n2) (z)amówienia\n0) w(y)jście\n\nWpisz cyfrę znajdującą się po lewej stronie lub znak w nawiasach,\nby przejść do wybranej opcji.", menuSettings);

            s = Functions.GetInputString("Wybrana opcja", List.of("0", "y", "1", "2", "m", "z"));
            switch (s) {

                case "1"://magazyn
                case "m":
                    MaterialsMenu.ShowMaterialsMenu(menuSettings, warehouseList);
                    break;

                case "2"://zamówienia
                case "z":
                    OrderMenu.ShowOrderMenu(menuSettings, orderList, warehouseList);
                    break;

                case "0"://wyjście
                case "y":
                    System.out.println(":Wyjście z systemu:");
                    return;

                default: //nigdy nie powinien do tego momentu program dojść - GetInputString filtruje
                    System.out.println("Błędny wybór.");
                    break;
            }
        }
    }
}