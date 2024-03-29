package pl.greczkapiotr.javazwirownia.material;

import pl.greczkapiotr.javazwirownia.Functions;
import pl.greczkapiotr.javazwirownia.menu.*;

import java.util.List;


public class MaterialsMenu {
    public static void ShowMaterialsMenu(MenuSettings menuSettings, List<Material> warehouseList) {
        String s;

        String tempText;
        int type, size, quantity;
        Boolean takeFromEach = null;
        Material tempMaterial;

        while (true) {
            Menu.DisplayMenu("Magazyn", "1) (s)tan magazynu\n2) (u)zupełnij surowiec\n3) (o)dejmij surowiec\n0) (p)owrót\n\nWpisz cyfrę znajdującą się po lewej stronie lub znak w nawiasach,\nby przejść do wybranej opcji.", menuSettings);

            s = Functions.GetInputString("Wybrana opcja", List.of("0", "p", "1", "2", "3", "s", "u", "o"));
            switch (s) {

                case "1"://stan magazynu
                case "s":
                    Menu.DisplayMenu("Materiały", "Aktualny stan magazynu", false, menuSettings);
                    for (Material m : warehouseList) {
                        System.out.println(m);
                    }

                    Functions.PressKeyToContinue();
                    break;

                case "2"://uzupełnij surowiec
                case "u":
                    //pyta do jakiego typu chce dodać, pyta o ilość i czy chce dodać do wszystkich czy do jednego tylko
                    System.out.println("\n\n");
                    tempText = "Wybierz typ surowca do uzupełnienia:\n";
                    for (int i = 0; i < warehouseList.size(); i++) {
                        tempText += (i + 1) + ") " + warehouseList.get(i).materialType + "\n";
                    }
                    Menu.DisplayMenu("Uzupełnianie surowca", tempText + "\nWpisz cyfrę znajdującą się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                    type = Functions.GetInputInt("Wybrany typ", new int[]{1, warehouseList.size()});

                    tempMaterial = warehouseList.get(type-1);

                    System.out.println("\n\n");
                    tempText = "Wybierz rozmiar surowca do uzupełnienia:\n0) wszystkie na raz\n";
                    for (int i = 0; i < SizeVariant.values().length; i++) {
                        tempText += (i + 1) + ") " + SizeVariant.values()[i] + "\n";
                    }
                    Menu.DisplayMenu("Uzupełnianie surowca", tempText + "\nWpisz cyfrę znajdującą się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                    size = Functions.GetInputInt("Wybrany rozmiar", new int[]{0, SizeVariant.values().length});

                    System.out.println("\n\n");
                    tempText = "Jaką ilość surowca dodać:\n";
                    Menu.DisplayMenu("Uzupełnianie surowca", tempText + "\nPodaj własną nieujemną wartość liczbową.", false, menuSettings);

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

                case "3"://odejmij surowiec
                case "o":
                    //pyta od jakiego typu chce odjąć, pyta o ilość i czy chce odjąć wartość od wszystkich czy jednego rozmiaru i jak wszystkich to czy wszystkich na raz (w sumie $ilość*ilość rozmiarów), czy w sumie $ilość)

                    System.out.println("\n\n");
                    tempText = "Wybierz typ surowca do odjęcia:\n";
                    for (int i = 0; i < warehouseList.size(); i++) {
                        tempText += (i + 1) + ") " + warehouseList.get(i).materialType + "\n";
                    }
                    Menu.DisplayMenu("Odejmowanie surowca", tempText + "\nWpisz cyfrę znajdującą się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                    type = Functions.GetInputInt("Wybrany typ", new int[]{1, warehouseList.size()});

                    tempMaterial = warehouseList.get(type-1);

                    System.out.println("\n\n");
                    tempText = "Wybierz rozmiar surowca do odjęcia:\n0) wszystkie na raz\n";
                    for (int i = 0; i < SizeVariant.values().length; i++) {
                        tempText += (i + 1) + ") " + SizeVariant.values()[i] + "\n";
                    }
                    Menu.DisplayMenu("Odejmowanie surowca", tempText + "\nWpisz cyfrę znajdującą się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                    size = Functions.GetInputInt("Wybrany rozmiar", new int[]{0, SizeVariant.values().length});

                    if (size == 0) {
                        System.out.println("\n\n");
                        tempText = "Jak chcesz odjąć ilość surowca:\n1) odejmij łącznie ilość w miarę równomiernie\n2) odejmij ilość od każdego z osobna\n";
                        Menu.DisplayMenu("Odejmowanie surowca", tempText + "\nWpisz cyfrę znajdującą się po lewej stronie wybranej opcji,\nby wybrać element.", menuSettings);
                        takeFromEach = Functions.GetInputInt("Typ usuwania", List.of(1, 2)) == 2;
                    }

                    System.out.println("\n\n");
                    tempText = "Jaką ilość surowca odjąć:\n";
                    Menu.DisplayMenu("Odejmowanie surowca", tempText + "\nPodaj własną nieujemną wartość liczbową.", false, menuSettings);

                    quantity = Functions.GetInputInt("Wpisz ilość", false);

                    System.out.println("\n\n");
                    if(tempMaterial != null) {
                        if (size == 0)
                            System.out.println(tempMaterial.TakeFrom(quantity, takeFromEach) ? "Pobrano ilość pomyślnie" : "Niewystarczające materiały lub błędna wartość");
                        else
                            System.out.println(tempMaterial.TakeFrom(quantity, SizeVariant.values()[size-1]) ? "Pobrano ilość pomyślnie" : "Niewystarczające materiały lub błędna wartość");
                    }

                    Functions.PressKeyToContinue();
                    break;

                case "0"://powrót
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
