package pl.greczkapiotr.javazwirownia.material;

public enum MaterialTypes {
    Sand("Piasek"), Gravel("Żwir"), Rock("Kamień"), Chippings("Grys"), GravelMix("Pospółka"), ExampleType("Przykładowy typ materiału");

    private String value;
    MaterialTypes(String str) { this.value = str; }
    @Override public String toString() { return value; }
}
