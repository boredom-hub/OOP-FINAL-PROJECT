import java.util.ArrayList;

// keeps all the items for one kind of cost (direct or indirect)
public class CostList {

    private final CostType type;
    private final ArrayList<CostItem> items = new ArrayList<>();

    public CostList(CostType type) {
        this.type = type;
    }

    public CostType getType() {
        return type;
    }

    public int size() {
        return items.size();
    }

    public CostItem get(int index) {
        return items.get(index);
    }

    public void add(CostItem item) {
        items.add(item);
    }

    public void replace(int index, CostItem item) {
        items.set(index, item);
    }

    public void remove(int index) {
        items.remove(index);
    }

    // puts a copy right under the original
    public void duplicate(int index) {
        items.add(index + 1, items.get(index).copy());
    }

    public double getTotal() {
        double total = 0;
        for (CostItem item : items) {
            total += item.getTotal();
        }
        return total;
    }
}
