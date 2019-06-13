package pl.GreczkaPiotr.javaZwirownia;

import pl.GreczkaPiotr.javaZwirownia.material.MaterialTypes;
import pl.GreczkaPiotr.javaZwirownia.material.SizeVariant;

public interface IGravelPitSystem {
    void Initialize();

    void production(MaterialTypes materialType, int quantity);
    void production(MaterialTypes materialType, SizeVariant materialSize, int quantity);

    boolean order(MaterialTypes materialType, int quantity);
    boolean order(MaterialTypes materialType, SizeVariant materialSize, int quantity);
    boolean order(MaterialTypes materialType, SizeVariant minMaterialSize, SizeVariant maxMaterialSize, int quantity);
}
