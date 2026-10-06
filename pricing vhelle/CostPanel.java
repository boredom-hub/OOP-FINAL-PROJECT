import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// one section of the window: title, table, buttons and the total
public class CostPanel extends JPanel {

    private CostList costs;
    private DefaultTableModel tableModel;
    private JTable table;

    private JButton addButton = new JButton("+ Add");
    private JButton editButton = new JButton("Edit");
    private JButton duplicateButton = new JButton("Duplicate");
    private JButton deleteButton = new JButton("Delete");
    private JLabel totalLabel = new JLabel();

    public CostPanel(CostList costs) {
        this.costs = costs;

        setLayout(new BorderLayout(0, 8));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));

        add(makeHeader(), BorderLayout.NORTH);
        add(makeTable(), BorderLayout.CENTER);
        add(makeFooter(), BorderLayout.SOUTH);

        addButton.addActionListener(e -> addItem());
        editButton.addActionListener(e -> editSelected());
        duplicateButton.addActionListener(e -> duplicateSelected());
        deleteButton.addActionListener(e -> deleteSelected());

        refresh(-1);
    }

    private JPanel makeHeader() {
        JLabel title = new JLabel(costs.getType().getTitle());
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));

        JButton infoButton = new JButton("?");
        infoButton.setMargin(new Insets(0, 6, 0, 6));
        infoButton.setToolTipText("What are " + costs.getType().getTitle().toLowerCase() + "?");
        infoButton.addActionListener(e -> showInfo());

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        left.add(title);
        left.add(infoButton);

        JPanel header = new JPanel(new BorderLayout());
        header.add(left, BorderLayout.WEST);
        header.add(addButton, BorderLayout.EAST);
        return header;
    }

    private JScrollPane makeTable() {
        String[] columns = {"Item", "Qty", "Unit", "Cost / unit", "Total"};

        // the table is read only, you edit through the dialog
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> updateButtons());

        // numbers look better on the right
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(1).setCellRenderer(right);
        table.getColumnModel().getColumn(3).setCellRenderer(right);
        table.getColumnModel().getColumn(4).setCellRenderer(right);

        // double click to edit
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editSelected();
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(400, 130));
        return scroll;
    }

    private JPanel makeFooter() {
        totalLabel.setFont(totalLabel.getFont().deriveFont(Font.BOLD, 14f));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        buttons.add(editButton);
        buttons.add(duplicateButton);
        buttons.add(deleteButton);

        JPanel footer = new JPanel(new BorderLayout());
        footer.add(buttons, BorderLayout.WEST);
        footer.add(totalLabel, BorderLayout.EAST);
        return footer;
    }

    // ---- button actions ----

    private void addItem() {
        CostItem item = CostDialog.ask(this, costs.getType(), null);
        if (item != null) {
            costs.add(item);
            refresh(costs.size() - 1);
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            return;
        }
        CostItem item = CostDialog.ask(this, costs.getType(), costs.get(row));
        if (item != null) {
            costs.replace(row, item);
            refresh(row);
        }
    }

    private void duplicateSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            return;
        }
        costs.duplicate(row);
        refresh(row + 1);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            return;
        }
        costs.remove(row);
        refresh(-1);
    }

    private void showInfo() {
        JLabel text = new JLabel("<html><body style='width: 340px'>" + costs.getType().getInfo() + "</body></html>");
        JOptionPane.showMessageDialog(this, text, costs.getType().getTitle(), JOptionPane.INFORMATION_MESSAGE);
    }

    // ---- keeping the screen in sync with the data ----

    // redraws the table, pass the row to select afterwards (or -1 for none)
    private void refresh(int rowToSelect) {
        tableModel.setRowCount(0);
        for (int i = 0; i < costs.size(); i++) {
            CostItem item = costs.get(i);
            tableModel.addRow(new Object[]{
                    item.getName(),
                    Util.num(item.getQuantity()),
                    item.getUnit().getCode(),
                    Util.peso(item.getUnitCost()),
                    Util.peso(item.getTotal())
            });
        }

        totalLabel.setText("Total: " + Util.peso(costs.getTotal()));

        if (rowToSelect >= 0 && rowToSelect < tableModel.getRowCount()) {
            table.setRowSelectionInterval(rowToSelect, rowToSelect);
        }
        updateButtons();
    }

    private void updateButtons() {
        boolean somethingSelected = table.getSelectedRow() != -1;
        editButton.setEnabled(somethingSelected);
        duplicateButton.setEnabled(somethingSelected);
        deleteButton.setEnabled(somethingSelected);
    }
}
