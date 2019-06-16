package pl.GreczkaPiotr.javaZwirownia;

import pl.GreczkaPiotr.javaZwirownia.material.*;
import pl.GreczkaPiotr.javaZwirownia.menu.*;
import pl.GreczkaPiotr.javaZwirownia.order.*;

import java.util.ArrayList;
import java.util.List;

public class GravelPitManagment implements IGravelPitSystem {
    public static GravelPitManagment main = new GravelPitManagment();

    public boolean silentMode = false;
    private List<Material> warehouseList = new ArrayList<>();
    private List<Order> orderList = new ArrayList<>();
    private MenuSettings menuSettings = new MenuSettings(0, '=', '|', '*');

    @Override
    public void Initialize() {
        silentMode = true;

        //dodajemy do tablicy materiały, jakie produkuje żwirownia, można je zainicjować z wartością/ilością początkową
        warehouseList.add(new Material(MaterialTypes.GravelMix));
        warehouseList.add(new Material(MaterialTypes.Sand, 54));
        warehouseList.add(new Material(MaterialTypes.Sand, 12)); //na przykład drugi rodzaj piasku - dlatego nowy obiekt
        warehouseList.add(new Material(MaterialTypes.Chippings));
        warehouseList.add(new Material(MaterialTypes.Rock, 186));
        warehouseList.add(new Material(MaterialTypes.Gravel, 7));
        warehouseList.add(new Material(MaterialTypes.ExampleType, 250));

        orderList.add(new Order("Zamówienie na pospółkę", warehouseList.get(0), true, 1 ));
        orderList.add(new Order("zamówienie grysu", warehouseList.get(3), 9 ));
        orderList.add(new Order("piasek pod drogę", warehouseList.get(2), SizeVariant.Big, 25));
        orderList.add(new Order("kamień (różne rozmiary)", warehouseList.get(4), List.of(SizeVariant.VerySmall, SizeVariant.Small, SizeVariant.Medium, SizeVariant.Big), 17));
        orderList.add(new Order("zamówienie konkretnych ilości kamienia", warehouseList.get(4), List.of(SizeVariant.VerySmall, SizeVariant.Small, SizeVariant.Medium),  true,17));
        orderList.get(2).isRealized = true;

        silentMode = false;
    }

    @Override
    public void MainMenu() {
        String s = "";
        while (true) {
            Menu.DisplayMenu("System Obsługi Żwirowni", "1) (m)agazyn\n2) (z)amówienia\n0) w(y)jście\n\nWpisz cyfrę znajdującą się po lewej stronie lub znak w nawiasach,\nby przejść do wybranej opcji.", menuSettings);

            s = Functions.GetInputString("Wybrana opcja", List.of("0", "y", "1", "2", "m", "z"));
            switch (s) {
                case "1":
                case "m":
                    MaterialsMenu.ShowMaterialsMenu(menuSettings, warehouseList);
                    break;
                case "2":
                case "z":
                    OrderMenu.ShowOrderMenu(menuSettings, orderList, warehouseList);
                    break;
                case "0":
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