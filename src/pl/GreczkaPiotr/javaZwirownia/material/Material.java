package pl.GreczkaPiotr.javaZwirownia.material;

import pl.GreczkaPiotr.javaZwirownia.GravelPitManagment;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

public class Material {
    public MaterialTypes materialType;
    public Dictionary<SizeVariant, Integer> quantities;

    public Material(MaterialTypes materialType) {
        this(materialType, 0);
    }

    public Material(MaterialTypes materialType, int startQuantityForEach) {
        this.materialType = materialType;
        quantities = new Hashtable<>();

        for (SizeVariant sizeVariant : SizeVariant.values()) {
            quantities.put(sizeVariant, startQuantityForEach);
        }
    }

    //SOME SPECIAL

    public int ReturnQuantity(SizeVariant sizeVariant) {
        return quantities.get(sizeVariant);
    }


    //ADD

    public Boolean AddQuantity(int quantity, SizeVariant addToSize) {
        if (!GravelPitManagment.main.silentMode)
            System.out.println("Próba dołożenia do materiału " + materialType + " sztuk w ilości " + quantity + " jednostek (aktualnie: " + quantities.get(addToSize) + "), wariant: rozmiar " + addToSize);

        if (quantity == 0)
            return true;
        if (quantity < 0)
            return null;

        quantities.put(addToSize, ReturnQuantity(addToSize) + quantity);
        return true;
    }

    public Boolean AddQuantity(int quantity) {
        if (!GravelPitManagment.main.silentMode)
            System.out.println("Próba dołożenia do materiału " + materialType + " sztuk w ilości " + quantity + " jednostek, wariant: do każdego rozmiaru");

        if (quantity == 0)
            return true;
        if (quantity < 0)
            return null;

        for (SizeVariant sizeVariant : SizeVariant.values()) {
            quantities.put(sizeVariant, ReturnQuantity(sizeVariant) + quantity);
        }
        return true;
    }


    //TAKE

    public Boolean TakeFrom(int quantity, SizeVariant sizeToTakeFrom) {
        if (!GravelPitManagment.main.silentMode)
            System.out.println("Próba pobrania z magazynu materiału " + materialType + " w ilości " + quantity + " jednostek (dostępne: " + quantities.get(sizeToTakeFrom) + "), wariant: rozmiar " + sizeToTakeFrom);

        if (quantity == 0)
            return true;
        if (quantity < 0)
            return null;

        if (ReturnQuantity(sizeToTakeFrom) >= quantity) {
            quantities.put(sizeToTakeFrom, ReturnQuantity(sizeToTakeFrom) - quantity);
            return true;
        }
        return false;
    }

    public Boolean TakeFrom(int quantity, boolean takeQuantityFromEach) {
        if (!GravelPitManagment.main.silentMode)
            System.out.println("Próba pobrania z magazynu materiału " + materialType + " w ilości " + quantity + " jednostek, wariant: " + (takeQuantityFromEach ? "pobierz ilość z każdej hałdy" : "pobierz materiał w miarę równomiernie"));

        if (quantity == 0)
            return true;
        if (quantity < 0)
            return null;

        if (takeQuantityFromEach) { //z każdego bierzemy ilość jaką podaliśmy
            //for (int i = 0; i < quantities.size(); i++) { //sprawdzamy czy każdy ma wystarczającą ilość, by pobrać z magazynu
            //    if (ReturnQuantity(SizeVariant.values()[i]) < quantity)
            //        return false;
            //}
            for (SizeVariant sizeVariant : SizeVariant.values()) { //sprawdzamy czy każdy ma wystarczającą ilość, by pobrać z magazynu
                if (ReturnQuantity(sizeVariant) < quantity)
                    return false;
            }
            for (SizeVariant sizeVariant : SizeVariant.values()) {
                quantities.put(sizeVariant, ReturnQuantity(sizeVariant) - quantity);
            }
            return true;
        }
        //bez else, ponieważ każda ścieżka wyżej zwraca wartość

        int totalQuantity = 0;
        Iterator<Integer> integerIterator = quantities.elements().asIterator();
        while (integerIterator.hasNext()) {
            totalQuantity += integerIterator.next(); //sumujemy ilości
        }

        if (totalQuantity < quantity) //jeśli więcej chcemy zabrać, niż jest ogólnie
            return false;

        do {
            if (quantity > quantities.size()) {
                for (SizeVariant sizeVariant : SizeVariant.values()) {
                    if (quantity <= 0)
                        break;

                    if (ReturnQuantity(sizeVariant) > 0) {
                        quantities.put(sizeVariant, ReturnQuantity(sizeVariant) - 1);
                        quantity--;
                    }
                }
            } else {
                while (true) {
                    if (quantity <= 0)
                        break;

                    SizeVariant maxValueIndex = SizeVariant.values()[0];
                    for (SizeVariant sizeVariant : SizeVariant.values()) {
                        if (ReturnQuantity(sizeVariant) > ReturnQuantity(maxValueIndex))
                            maxValueIndex = sizeVariant;
                    }

                    quantities.put(maxValueIndex, ReturnQuantity(maxValueIndex) - 1); //zawsze będzie prawidłowe (większe od zera), ponieważ pobierana ilość była mniejsza od sumy

                    quantity--;
                }
            }
        } while (quantity > 0);

        return true; //gotowe, udało się
    }

    public Boolean TakeFrom(int quantity, SizeVariant sizesToTakeFrom[], boolean takeQuantityFromEach) {
        if (!GravelPitManagment.main.silentMode)
            System.out.println("Próba pobrania z magazynu materiału " + materialType + " w ilości " + quantity + " jednostek, wariant: " + ("minimalny rozmiar: " + sizesToTakeFrom[0] + ", maksymalny rozmiar: " + sizesToTakeFrom[sizesToTakeFrom.length - 1]) + ", " + (takeQuantityFromEach ? "pobierz ilość z każdej hałdy" : "pobierz materiał w miarę równomiernie"));

        if (quantity == 0)
            return true;
        if (quantity < 0)
            return null;

        if (takeQuantityFromEach) { //z każdego bierzemy ilość jaką podaliśmy
            //for (int i = 0; i < quantities.size(); i++) { //sprawdzamy czy każdy ma wystarczającą ilość, by pobrać z magazynu
            //    if (ReturnQuantity(SizeVariant.values()[i]) < quantity)
            //        return false;
            //}
            for (SizeVariant sizeVariant : sizesToTakeFrom) { //sprawdzamy czy każdy ma wystarczającą ilość, by pobrać z magazynu
                if (ReturnQuantity(sizeVariant) < quantity)
                    return false;
            }
            for (SizeVariant sizeVariant : sizesToTakeFrom) {
                quantities.put(sizeVariant, ReturnQuantity(sizeVariant) - quantity);
            }
            return true;
        }
        //bez else, ponieważ każda ścieżka wyżej zwraca wartość

        int totalQuantity = 0;
        for (SizeVariant sizeVariant : sizesToTakeFrom) {
            totalQuantity += quantities.get(sizeVariant);
        }

        if (totalQuantity < quantity) //jeśli więcej chcemy zabrać, niż jest ogólnie
            return false;

        do {
            if (quantity > quantities.size()) {
                for (SizeVariant sizeVariant : sizesToTakeFrom) {
                    if (quantity <= 0)
                        break;

                    if (ReturnQuantity(sizeVariant) > 0) {
                        quantities.put(sizeVariant, ReturnQuantity(sizeVariant) - 1);
                        quantity--;
                    }
                }
            } else {
                while (true) {
                    if (quantity <= 0)
                        break;

                    SizeVariant maxValueIndex = sizesToTakeFrom[0];
                    for (SizeVariant sizeVariant : sizesToTakeFrom) {
                        if (ReturnQuantity(sizeVariant) > ReturnQuantity(maxValueIndex))
                            maxValueIndex = sizeVariant;
                    }

                    quantities.put(maxValueIndex, ReturnQuantity(maxValueIndex) - 1); //zawsze będzie prawidłowe (większe od zera), ponieważ pobierana ilość była mniejsza od sumy

                    quantity--;
                }
            }
        } while (quantity > 0);

        return true; //gotowe, udało się
    }


    // OTHERS

    @Override
    public String toString() { //własny .toString()
        String quantities = "";
        //for (int i = 0; i < this.quantities.size(); i++) {
        for (SizeVariant sizeVariant : SizeVariant.values()) {
            if (quantities != "")
                quantities += "; ";

            quantities += sizeVariant + " - " + ReturnQuantity(sizeVariant);
        }

        return materialType + "\n\t• ilości w magazynie: [" + quantities + "]";
    }
}