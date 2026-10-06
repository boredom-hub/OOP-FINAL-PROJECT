import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CostList directCosts = new CostList(CostType.DIRECT);
    private CostList indirectCosts = new CostList(CostType.INDIRECT);

    public MainFrame() {
        super("Direct & Indirect Costs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        content.add(new CostPanel(directCosts));
        content.add(Box.createVerticalStrut(12));
        content.add(new CostPanel(indirectCosts));

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        setSize(640, 760);
        setLocationRelativeTo(null);
    }
}
