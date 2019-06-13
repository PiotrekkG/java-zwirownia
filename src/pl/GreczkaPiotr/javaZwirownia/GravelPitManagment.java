package pl.GreczkaPiotr.javaZwirownia;

import pl.GreczkaPiotr.javaZwirownia.material.Material;
import pl.GreczkaPiotr.javaZwirownia.material.MaterialTypes;
import pl.GreczkaPiotr.javaZwirownia.material.SizeVariant;
import pl.GreczkaPiotr.javaZwirownia.order.Order;

import java.util.ArrayList;
import java.util.List;

public class GravelPitManagment implements IGravelPitSystem {
    public static GravelPitManagment main = new GravelPitManagment();

    public boolean silentMode = false;
    public List<Material> warehouseList = new ArrayList<>();
    public List<Order> orderList = new ArrayList<>();

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


    public void DisplayMenu(String title, String description, boolean showBottomBorder, int tryWidth) {
        Functions.DisplayMenu(title, description, showBottomBorder, tryWidth, '=', '|', '*');
    }

    public void DisplayMenu(String title, String description, boolean showBottomBorder) {
        DisplayMenu(title, description, showBottomBorder, 0);
    }


    private void ShowMainMenu() {
        String s = "";
        while (true) {
            DisplayMenu("System Obsługi Żwirowni", "1) (m)agazyn\n2) (z)amówienia\n0) w(y)jście\n\nWpisz cyfrę znajdującą się lewej stronie lub znak w nawiasach,\nby przejść do wybranej opcji.", true, 45);

            s = Functions.GetInputString("Wybrana opcja", new String[]{"0", "y", "1", "2", "m", "z"});
            switch (s) {
                case "1":
                case "m":
                    ShowMaterialsMenu();
                    break;
                case "2":
                case "z":
                    ShowOrderMenu();
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

    private void ShowMaterialsMenu() {
        String s = "";

        String tempText;
        int type, size, quantity;
        boolean takeFromEach = false;
        Material tempMaterial = null;

        while (true) {
            DisplayMenu("Magazyn", "1) (s)tan magazynu\n2) (u)zupełnij surowiec\n3) (o)dejmij surowiec\n0) (p)owrót\n\nWpisz cyfrę znajdującą się lewej stronie lub znak w nawiasach,\nby przejść do wybranej opcji.", true, 45);

            s = Functions.GetInputString("Wybrana opcja", new String[]{"0", "p", "1", "2", "3", "s", "u", "o"});
            switch (s) {
                case "1":
                case "s":
                    DisplayMenu("Materiały", "Aktualny stan magazynu", true, 45);
                    for (Material m : warehouseList) {
                        System.out.println(m);
                    }

                    Functions.PressKeyToContinue();
                    break;
                case "2":
                case "u":
                    //pyta do jakiego typu chce dodać, pyta o ilość i czy chce dodać do wszystkich czy do jednego tylko
                    System.out.println("\n\n");
                    tempText = "Wybierz typ surowca do uzupełnienia:\n";
                    for (int i = 0; i < warehouseList.size(); i++) {
                        tempText += (i + 1) + ") " + warehouseList.get(i).materialType + "\n";
                    }
                    DisplayMenu("Uzupełnianie surowca", tempText + "\nWpisz cyfrę znajdującą się lewej stronie wybranej opcji,\nby wybrać element.", true, 45);
                    type = Functions.GetInputInt("Wybrany typ", new int[]{1, MaterialTypes.values().length});

                    for (Material m : warehouseList) {
                        if (m.materialType == MaterialTypes.values()[type]) {
                            tempMaterial = m;
                            break;
                        }
                    }

                    System.out.println("\n\n");
                    tempText = "Wybierz rozmiar surowca do uzupełnienia:\n0) wszystkie na raz\n";
                    for (int i = 0; i < SizeVariant.values().length; i++) {
                        tempText += (i + 1) + ") " + SizeVariant.values()[i] + "\n";
                    }
                    DisplayMenu("Uzupełnianie surowca", tempText + "\nWpisz cyfrę znajdującą się lewej stronie wybranej opcji,\nby wybrać element.", true, 45);
                    size = Functions.GetInputInt("Wybrany rozmiar", new int[]{0, SizeVariant.values().length});

                    System.out.println("\n\n");
                    tempText = "Jaką ilość surowca dodać:\n";
                    DisplayMenu("Uzupełnianie surowca", tempText + "\nPodaj własną nieujemną wartość liczbową.", false, 45);

                    quantity = Functions.GetInputInt("Wpisz ilość", false);

                    System.out.println("\n\n");
                    if(tempMaterial != null) {
                        if (size == 0)
                            System.out.println(tempMaterial.AddQuantity(quantity) ? "Dodano ilość pomyślnie" : "Podano błędną ilość!"); //zawsze będzie prawidłowa, ponieważ GetInputInt inaczej nie pozwoli
                        else
                            System.out.println(tempMaterial.AddQuantity(quantity, SizeVariant.values()[size - 1]) ? "Dodano ilość pomyślnie" : "Podano błędną ilość!"); //zawsze będzie prawidłowa, ponieważ GetInputInt inaczej nie pozwoli
                    }

                    Functions.PressKeyToContinue();
                    break;
                case "3":
                case "o":
                    //pyta od jakiego typu chce odjąć, pyta o ilość i czy chce odjąć wartość od wszystkich czy jednego rozmiaru i jak wszystkich to czy wszystkich na raz (w sumie $ilość*ilość rozmiarów), czy w sumie $ilość)

                    System.out.println("\n\n");
                    tempText = "Wybierz typ surowca do odjęcia:\n";
                    for (int i = 0; i < warehouseList.size(); i++) {
                        tempText += (i + 1) + ") " + warehouseList.get(i).materialType + "\n";
                    }
                    DisplayMenu("Odejmowanie surowca", tempText + "\nWpisz cyfrę znajdującą się lewej stronie wybranej opcji,\nby wybrać element.", true, 45);
                    type = Functions.GetInputInt("Wybrany typ", new int[]{1, MaterialTypes.values().length});

                    for (Material m : warehouseList) {
                        if (m.materialType == MaterialTypes.values()[type]) {
                            tempMaterial = m;
                            break;
                        }
                    }

                    System.out.println("\n\n");
                    tempText = "Wybierz rozmiar surowca do odjęcia:\n0) wszystkie na raz\n";
                    for (int i = 0; i < SizeVariant.values().length; i++) {
                        tempText += (i + 1) + ") " + SizeVariant.values()[i] + "\n";
                    }
                    DisplayMenu("Odejmowanie surowca", tempText + "\nWpisz cyfrę znajdującą się lewej stronie wybranej opcji,\nby wybrać element.", true, 45);
                    size = Functions.GetInputInt("Wybrany rozmiar", new int[]{0, SizeVariant.values().length});

                    if (size == 0) {
                        System.out.println("\n\n");
                        tempText = "Jak chcesz odjąć ilość surowca:\n1) odejmij ilość od każdego z osobna\n2) odejmij podaną ilość od każdego łącznie\n";
                        DisplayMenu("Odejmowanie surowca", tempText + "\nWpisz cyfrę znajdującą się lewej stronie wybranej opcji,\nby wybrać element.", true, 45);
                        takeFromEach = Functions.GetInputInt("Typ usuwania", new int[]{0, SizeVariant.values().length}) == 1;
                    }

                    System.out.println("\n\n");
                    tempText = "Jaką ilość surowca odjąć:\n";
                    DisplayMenu("Odejmowanie surowca", tempText + "\nPodaj własną nieujemną wartość liczbową.", false, 45);

                    quantity = Functions.GetInputInt("Wpisz ilość", false);

                    System.out.println("\n\n");
                    if(tempMaterial != null) {
                        if (size == 0)
                            System.out.println(tempMaterial.TakeFrom(quantity, takeFromEach) ? "Pobrano ilość pomyślnie" : "Nie wystarczające materiały lub błędna wartość");
                        else
                            System.out.println(tempMaterial.TakeFrom(quantity, SizeVariant.values()[size-1]) ? "Pobrano ilość pomyślnie" : "Nie wystarczające materiały lub błędna wartość");
                    }

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

    private void ShowOrderMenu() {
        String s = "";
        while (true) {
            DisplayMenu("Zamówienia", "1) (s)twórz zamówienie\n2) (z)realizuj zamówienie\n3) (u)suń zamówienie\n0) (p)owrót\n\nWpisz cyfrę znajdującą się lewej stronie lub znak w nawiasach,\nby przejść do wybranej opcji.", true, 45);

            s = Functions.GetInputString("Wybrana opcja", new String[]{"0", "p", "1", "2", "3", "s", "z", "u"});
            switch (s) {
                case "1":
                case "s":
                    System.out.println("Tworzenie zamówienia...");

                    Functions.PressKeyToContinue();
                    break;
                case "2":
                case "z":
                    System.out.println("Realizowanie zamówienia...");

                    Functions.PressKeyToContinue();
                    break;
                case "3":
                case "u":
                    System.out.println("Usuwanie zamówienia...");

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