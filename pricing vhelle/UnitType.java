// all the units you can pick when adding a cost item
public enum UnitType {
    KG("kg", "Weight - Kilograms (kg)"),
    G("g", "Weight - Grams (g)"),
    MG("mg", "Weight - Milligrams (mg)"),
    LB("lb", "Weight - Pounds (lb)"),
    OZ("oz", "Weight - Ounces (oz)"),

    L("l", "Volume - Liters (L)"),
    ML("ml", "Volume - Milliliters (mL)"),
    FL_OZ("fl_oz", "Volume - Fluid Ounces (fl oz)"),
    CUP("cup", "Volume - Cups"),
    TBSP("tbsp", "Volume - Tablespoons"),
    TSP("tsp", "Volume - Teaspoons"),

    PCS("pcs", "Count - Pieces"),
    DOZEN("dozen", "Count - Dozen"),

    PACK("pack", "Packaging - Pack"),
    BOX("box", "Packaging - Box"),
    BAG("bag", "Packaging - Bag"),
    CAN("can", "Packaging - Can"),
    BOTTLE("bottle", "Packaging - Bottle"),
    JAR("jar", "Packaging - Jar");

    private final String code;
    private final String label;

    UnitType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    // the combo box shows whatever toString gives back
    @Override
    public String toString() {
        return label;
    }
}
