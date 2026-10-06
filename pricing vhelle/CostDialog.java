import javax.swing.*;
import java.awt.*;

// the popup for adding or editing a single cost item
public class CostDialog extends JDialog {

    private JTextField nameField = new JTextField(22);
    private JTextField quantityField = new JTextField(10);
    private JLabel quantityHint = new JLabel(" ");
    private JComboBox<UnitType> unitBox = new JComboBox<>(UnitType.values());
    private JLabel costLabel = new JLabel();
    private JTextField costField = new JTextField(10);
    private JLabel errorLabel = new JLabel(" ");

    private CostItem result = null;

    private CostDialog(Window owner, CostType type, CostItem existing) {
        super(owner, (existing == null ? "Add " : "Edit ") + type.getItemName(), ModalityType.APPLICATION_MODAL);

        if (existing == null) {
            quantityField.setText("0");
            costField.setText("0");
        } else {
            nameField.setText(existing.getName());
            quantityField.setText(Util.num(existing.getQuantity()));
            unitBox.setSelectedItem(existing.getUnit());
            costField.setText(Util.num(existing.getUnitCost()));
        }

        quantityHint.setFont(quantityHint.getFont().deriveFont(11f));
        errorLabel.setForeground(Color.RED);

        // update the little hints while the user types
        quantityField.getDocument().addDocumentListener(new SimpleDocListener(this::updateHints));
        unitBox.addActionListener(e -> updateHints());
        updateHints();

        JPanel form = new JPanel(new GridLayout(0, 1, 0, 4));
        form.setBorder(BorderFactory.createEmptyBorder(14, 16, 8, 16));
        form.add(new JLabel(type.getItemName()));
        form.add(nameField);
        form.add(new JLabel("Quantity"));
        form.add(quantityField);
        form.add(quantityHint);
        form.add(new JLabel("Unit type"));
        form.add(unitBox);
        form.add(costLabel);
        form.add(costField);
        form.add(errorLabel);

        JButton okButton = new JButton(existing == null ? "Add" : "Save");
        JButton cancelButton = new JButton("Cancel");
        okButton.addActionListener(e -> save());
        cancelButton.addActionListener(e -> dispose());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(cancelButton);
        buttons.add(okButton);

        getRootPane().setDefaultButton(okButton);
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(owner);
    }

    // shows the dialog and returns the new item, or null if the user cancelled
    public static CostItem ask(Component parent, CostType type, CostItem existing) {
        Window owner = SwingUtilities.getWindowAncestor(parent);
        CostDialog dialog = new CostDialog(owner, type, existing);
        dialog.setVisible(true);
        return dialog.result;
    }

    private void updateHints() {
        double q = ExpressionParser.evaluate(quantityField.getText());
        if (Double.isNaN(q)) {
            quantityHint.setText("Not a valid number (you can type things like 1/2 or 3*4)");
        } else {
            quantityHint.setText("= " + Util.num(q));
        }
        UnitType unit = (UnitType) unitBox.getSelectedItem();
        costLabel.setText("Cost per " + unit.getCode());
    }

    private void save() {
        String name = nameField.getText().trim();
        double quantity = ExpressionParser.evaluate(quantityField.getText());
        double cost;
        try {
            cost = Double.parseDouble(costField.getText().trim());
        } catch (NumberFormatException e) {
            cost = Double.NaN;
        }

        if (name.isEmpty()) {
            errorLabel.setText("Please enter a name");
            return;
        }
        if (Double.isNaN(quantity) || quantity < 0) {
            errorLabel.setText("Quantity must be a number that's 0 or more");
            return;
        }
        if (Double.isNaN(cost) || cost < 0) {
            errorLabel.setText("Cost must be a number that's 0 or more");
            return;
        }

        result = new CostItem(name, quantity, (UnitType) unitBox.getSelectedItem(), cost);
        dispose();
    }
}
