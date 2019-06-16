package pl.GreczkaPiotr.javaZwirownia.order;

import pl.GreczkaPiotr.javaZwirownia.Functions;
import pl.GreczkaPiotr.javaZwirownia.GravelPitManagment;
import pl.GreczkaPiotr.javaZwirownia.material.Material;
import pl.GreczkaPiotr.javaZwirownia.material.MaterialTypes;
import pl.GreczkaPiotr.javaZwirownia.material.SizeVariant;
import pl.GreczkaPiotr.javaZwirownia.menu.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Order {
    public String orderDescription;
    public Material materialTarget;
    public List<SizeVariant> sizeVariant;
    public boolean takeFromEach;
    public int quantity;
    public boolean isRealized;

    public Order(String orderDescription, Material materialTarget, List<SizeVariant> sizeVariant, boolean takeFromEach, int quantity) {
        if (orderDescription.length() == 0)
            orderDescription = "brak opisu zamówienia";

        if (sizeVariant == null)
            sizeVariant = new ArrayList<>();

        this.orderDescription = orderDescription;
        this.materialTarget = materialTarget;
        this.sizeVariant = sizeVariant;
        this.takeFromEach = takeFromEach;
        this.quantity = quantity;

        isRealized = false;
    }

    public Order(String orderDescription, Material materialTarget, SizeVariant sizeVariant, int quantity) {
        this(orderDescription, materialTarget, List.of(sizeVariant), false, quantity);
    }

    public Order(String orderDescription, Material materialTarget, List<SizeVariant> sizeVariant, int quantity) {
        this(orderDescription, materialTarget, sizeVariant, false, quantity);
    }

    public Order(String orderDescription, Material materialTarget, boolean takeFromEach, int quantity) {
        this(orderDescription, materialTarget, null, takeFromEach, quantity);
    }

    public Order(String orderDescription, Material materialTarget, int quantity) {
        this(orderDescription, materialTarget, null, false, quantity);
    }

    public Boolean RealizeOrder() {
        if (!GravelPitManagment.main.silentMode)
            System.out.println("Próba realizacji zamówienia...");

        if (isRealized) {
            System.out.println("Zamówienie jest już zrealizowane!");
            return false;
        }

        Boolean state;
        state = materialTarget.TakeFrom(quantity, sizeVariant, takeFromEach);

        if (state == null) {
            System.out.println("Istnieje błąd w zamówieniu!");
        } else if (state) {
            System.out.println("Zrealizowano zamówienie pomyślnie!");
            isRealized = true;
        } else {
            System.out.println("Niewystarczające materiały lub błędna wartość");
        }

        return state;
    }


    @Override
    public String toString() {
        return "Szczegóły zamówienia:" +
                "\n- opis zamówienia: " + orderDescription +
                "\n- rodzaj materiału: " + materialTarget.materialType +
                (sizeVariant.size() != 0 ?
                        (sizeVariant.size() == 1 ?
                                "\n- rozmiar: " + sizeVariant.get(0) :
                                "\n- rozmiar od: " + sizeVariant.get(0) + " do: " + sizeVariant.get(sizeVariant.size() - 1)) :
                        "\n- rozmiar: każdy") +
                "\n- ilość: " + (takeFromEach ? (sizeVariant.size() == 0 ? "łącznie " + (quantity * SizeVariant.values().length) + " (każdy rozmiar po " + quantity + " sztuk)" : "łącznie " + (quantity * sizeVariant.size()) + " (każdy rozmiar po " + quantity + " sztuk)") : "łącznie " + quantity) +
                "\n- zrealizowano: " + (isRealized ? "tak" : "nie");
    }
}