public enum CostType {

    DIRECT("Direct Costs", "Direct Cost",
            "<p>Direct costs are expenses tied directly to making a specific product or service. "
            + "They usually go up and down with how much you produce.</p>"
            + "<p>Examples:</p><ul>"
            + "<li>Raw materials (wood for furniture, fabric for clothes)</li>"
            + "<li>Manufacturing supplies (nails, glue, packaging)</li>"
            + "<li>Commissions tied to specific sales</li></ul>"),

    INDIRECT("Indirect Costs", "Indirect Cost",
            "<p>Indirect costs are overhead. They keep the business running but aren't tied to one "
            + "specific product, and they usually stay the same no matter how much you produce.</p>"
            + "<p>Examples:</p><ul>"
            + "<li>Rent or mortgage</li>"
            + "<li>Utilities (electricity, water, internet)</li>"
            + "<li>Admin staff salaries</li>"
            + "<li>Marketing and advertising</li>"
            + "<li>Insurance</li>"
            + "<li>Equipment depreciation</li></ul>");

    private final String title;
    private final String itemName;
    private final String info;

    CostType(String title, String itemName, String info) {
        this.title = title;
        this.itemName = itemName;
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public String getItemName() {
        return itemName;
    }

    public String getInfo() {
        return info;
    }
}
