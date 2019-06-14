package pl.GreczkaPiotr.javaZwirownia;

import pl.GreczkaPiotr.javaZwirownia.material.*;
import pl.GreczkaPiotr.javaZwirownia.menu.*;
import pl.GreczkaPiotr.javaZwirownia.order.*;

import java.util.ArrayList;
import java.util.List;

public class GravelPitManagment implements IGravelPitSystem {
    public static GravelPitManagment main = new GravelPitManagment();

    public boolean silentMode = false;
    public List<Material> warehouseList = new ArrayList<>();
    public List<Order> ordersList = new ArrayList<>();
    public MenuSettings menuSettings = new MenuSettings(0, '=', '|', '*');

    @Override
    public void Initialize() {
        silentMode = true;

        //dodajemy do tablicy materiały, jakie produkuje żwirownia, można je zainicjować z wartością/ilością początkową
        warehouseList.add(new Material(MaterialTypes.Sand, 54));
        warehouseList.add(new Material(MaterialTypes.Gravel, 7));
        warehouseList.add(new Material(MaterialTypes.Rock, 20));
        warehouseList.add(new Material(MaterialTypes.Chippings));
        warehouseList.add(new Material(MaterialTypes.GravelMix));

        silentMode = false;
        ShowMainMenu();
    }

    @Override
    public void production(MaterialTypes materialType, int quantity) {
        for (Material m : warehouseList) {
            if (m.materialType == materialType) {
                m.AddQuantity(quantity);
                break;
            }
        }
    }

    @Override
    public void production(MaterialTypes materialType, SizeVariant materialSize, int quantity) {
        for (Material m : warehouseList) {
            if (m.materialType == materialType) {
                m.AddQuantity(quantity, materialSize);
                break;
            }
        }
    }

    @Override
    public boolean order(MaterialTypes materialType, int quantity) {
        for (Material m : warehouseList) {
            if (m.materialType == materialType) {
                return m.TakeFrom(quantity, false);
            }
        }
        return false;
    }

    @Override
    public boolean order(MaterialTypes materialType, SizeVariant materialSize, int quantity) {
        for (Material m : warehouseList) {
            if (m.materialType == materialType) {
                return m.TakeFrom(quantity, materialSize);
            }
        }
        return false;
    }

    @Override
    public boolean order(MaterialTypes materialType, SizeVariant minMaterialSize, SizeVariant maxMaterialSize, int quantity) {
        return false;
    }


    private void ShowMainMenu() {
        String s = "";
        while (true) {
            Menu.DisplayMenu("System Obsługi Żwirowni", "1) (m)agazyn\n2) (z)amówienia\n0) w(y)jście\n\nWpisz cyfrę znajdującą się lewej stronie lub znak w nawiasach,\nby przejść do wybranej opcji.", menuSettings);

            s = Functions.GetInputString("Wybrana opcja", new String[]{"0", "y", "1", "2", "m", "z"});
            switch (s) {
                case "1":
                case "m":
                    MaterialsMenu.ShowMaterialsMenu(menuSettings, warehouseList);
                    break;
                case "2":
                case "z":
                    OrderMenu.ShowOrderMenu(menuSettings, ordersList, warehouseList);
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