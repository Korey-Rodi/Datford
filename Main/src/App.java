import javax.swing.*;
public class App {
    private JFrame frame;

    public App() {
        frame = new JFrame("Datford");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);

        JPanel panel = new JPanel();
        JLabel assetTag = new JLabel("Asset Tag:");
        JTextField assetTagEntry = new JTextField(30);
        JLabel serialNumber = new JLabel("Serial Number:");
        JTextField serialNumberEntry = new JTextField(30);
        assetTag.setBounds(10,20,80,25);
        assetTagEntry.setBounds(100,20,165,25);
        panel.add(assetTag);
        panel.add(assetTagEntry);
        panel.add(serialNumber);
        panel.add(serialNumberEntry);

        frame.add(panel);


        frame.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);

        
    }
}
