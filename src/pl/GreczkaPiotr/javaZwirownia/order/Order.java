package pl.GreczkaPiotr.javaZwirownia.order;

import pl.GreczkaPiotr.javaZwirownia.material.MaterialTypes;
import pl.GreczkaPiotr.javaZwirownia.material.SizeVariant;

public class Order {
    String orderDescription;
    MaterialTypes materialType;
    SizeVariant sizeVariant;
    boolean takeFromEach;
    int quantity;
    boolean isRealized;

    public Order(String orderDescription, MaterialTypes materialType, SizeVariant sizeVariant, boolean takeFromEach, int quantity) {
        if(orderDescription.length() == 0)
            orderDescription = "brak opisu zamówienia";

        this.orderDescription = orderDescription;
        this.materialType = materialType;
        this.sizeVariant = sizeVariant;
        this.takeFromEach = takeFromEach;
        this.quantity = quantity;

        isRealized = false;
    }

    public Order(String orderDescription, MaterialTypes materialType, SizeVariant sizeVariant, int quantity) {
        this(orderDescription, materialType, sizeVariant, false, quantity);
    }

    public Order(String orderDescription, MaterialTypes materialType, boolean takeFromEach, int quantity) {
        this(orderDescription, materialType, null, takeFromEach, quantity);
    }

    public Order(String orderDescription, MaterialTypes materialType, int quantity) {
        this(orderDescription, materialType, null, false, quantity);
    }


    public boolean hasSizeVariant() {
        return sizeVariant == null;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderDescription='" + orderDescription + '\'' +
                ", materialType=" + materialType +
                ", sizeVariant=" + sizeVariant +
                ", takeFromEach=" + takeFromEach +
                ", quantity=" + quantity +
                ", isRealized=" + isRealized +
                '}';
    }
}