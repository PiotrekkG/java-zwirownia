package pl.greczkapiotr.javazwirownia.order;

import pl.greczkapiotr.javazwirownia.material.Material;
import pl.greczkapiotr.javazwirownia.GravelPitManagement;
import pl.greczkapiotr.javazwirownia.material.SizeVariant;

import java.util.ArrayList;
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
        if (!GravelPitManagement.main.silentMode)
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
            System.out.println("Zamówienia zostało zrealizowane pomyślnie!");
            isRealized = true;
        } else {
            System.out.println("Brak wystarczających materiałów, aby zrealizować zamówienie!");
        }

        return state;
    }


    public String shortUnitDescription() {
        int size = sizeVariant.size();
        String description;

        if (takeFromEach) {
            description =
                    "łącznie " +
                            (size == 0 ?
                                    (quantity * SizeVariant.values().length)
                                    :
                                    (quantity * size)
                            )
                            + " (każdy rozmiar po " + quantity + ")";
        } else {
            if (size == 0) {
                description = "łącznie " + quantity + " (pobieranie ze wszystkich dostępnych rozmiarów w miarę równomiernie)";
            } else if (size == 1) {
                description = quantity + " (pobieranie z wybranego rozmiaru)";
            } else {
                description = "łącznie " + quantity + " (pobieranie z wybranych dostępnych rozmiarów w miarę równomiernie)";
            }
        }

        return description;
    }

    @Override
    public String toString() {
        return "Szczegóły zamówienia:" +
                "\n- opis zamówienia: " + orderDescription +
                "\n- rodzaj materiału: " + materialTarget.materialType +
                (sizeVariant.size() != 0 ?
                        (sizeVariant.size() == 1 ?
                                "\n- rozmiar: " + sizeVariant.get(0) :
                                "\n- rozmiary od: " + sizeVariant.get(0) + ", do: " + sizeVariant.get(sizeVariant.size() - 1)) :
                        "\n- rozmiar: każdy") +
                "\n- ilość: " + shortUnitDescription() +
                "\n- zrealizowano: " + (isRealized ? "tak" : "nie") +
                "\n\nAktualny stan powyższego surowca w magazynie: " + materialTarget.toString();
    }
}