package pl.greczkapiotr.javazwirownia.material;

public enum SizeVariant {
    VerySmall("bardzo drobne"), Small("drobne"), Medium("średnie"), Big("duże"), VeryBig("bardzo duże");

    private String value;
    SizeVariant(String str) { this.value = str; }
    @Override public String toString() { return value; }
}
