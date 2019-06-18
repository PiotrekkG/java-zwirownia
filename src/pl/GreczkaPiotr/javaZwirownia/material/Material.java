package pl.greczkapiotr.javazwirownia.material;

import pl.greczkapiotr.javazwirownia.GravelPitManagement;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

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
        if (!GravelPitManagement.main.silentMode)
            System.out.println("Próba dołożenia do materiału " + materialType + " sztuk w ilości " + quantity + " jednostek (aktualnie: " + quantities.get(addToSize) + "), wariant: rozmiar " + addToSize);

        if (quantity == 0)
            return true;
        if (quantity < 0)
            return null;

        quantities.put(addToSize, ReturnQuantity(addToSize) + quantity);
        return true;
    }

    public Boolean AddQuantity(int quantity) {
        if (!GravelPitManagement.main.silentMode)
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
        if (!GravelPitManagement.main.silentMode)
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

    public Boolean TakeFrom(int quantity, Boolean takeQuantityFromEach) {
        if (!GravelPitManagement.main.silentMode)
            System.out.println("Próba pobrania z magazynu materiału " + materialType + " w ilości " + quantity + " jednostek, wariant: " + (takeQuantityFromEach ? "pobierz ilość z każdej hałdy" : "pobierz materiał w miarę równomiernie"));

        if (quantity == 0)
            return true;
        if (quantity < 0)
            return null;

        if (takeQuantityFromEach) { //z każdego bierzemy ilość jaką podaliśmy
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
                    if (quantity <= 0) //pętla nieskończona - ponieważ gdy jest w warunku, to IDE krzyczy, że punkt jest nieosiągalny ze względu na warunek na dole pętli
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

    public Boolean TakeFrom(int quantity, List<SizeVariant> sizesToTakeFrom, boolean takeQuantityFromEach) {
        if(sizesToTakeFrom.size() == 0)
            return TakeFrom(quantity, takeQuantityFromEach);
        if (sizesToTakeFrom.size() == 1)
            return TakeFrom(quantity, sizesToTakeFrom.get(0));

        if (!GravelPitManagement.main.silentMode)
            System.out.println("Próba pobrania z magazynu materiału " + materialType + " w ilości " + quantity + " jednostek, wariant: minimalny rozmiar: " + (sizesToTakeFrom.size()!=0?sizesToTakeFrom.get(0):null) + ", maksymalny rozmiar: " + (sizesToTakeFrom.size()!=0?sizesToTakeFrom.get(sizesToTakeFrom.size()-1):null) + ", " + (takeQuantityFromEach ? "pobierz ilość z każdej hałdy" : "pobierz materiał w miarę równomiernie"));

        if (quantity == 0)
            return true;
        if (quantity < 0)
            return null;

        if (takeQuantityFromEach) { //z każdego bierzemy ilość jaką podaliśmy
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
                    if (quantity <= 0) //pętla nieskończona - ponieważ gdy jest w warunku, to IDE krzyczy, że punkt jest nieosiągalny ze względu na warunek na dole pętli
                        break;

                    SizeVariant maxValueIndex = sizesToTakeFrom.get(0);
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
    public String toString() { //nadpisana metoda .toString() dla tego obiektu
        StringBuilder quantities = new StringBuilder();
        for (SizeVariant sizeVariant : SizeVariant.values()) {
            if (!quantities.toString().isBlank())
                quantities.append("; ");

            quantities.append(sizeVariant + " - " + ReturnQuantity(sizeVariant));
        }

        return materialType + "\n\t> ilości w magazynie: [" + quantities + "]";
    }
}