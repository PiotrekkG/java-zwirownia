package pl.GreczkaPiotr.javaZwirownia.material;

public enum MaterialTypes {
    Unknown("Nieznany typ"), Sand("Piasek"), Gravel("Żwir"), Rock("Kamień"), Chippings("Grys"), GravelMix("Pospółka");

    private String value;
    MaterialTypes(String str) { this.value = str; }
    @Override public String toString() { return value; }
}
