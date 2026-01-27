import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.swing.*;
import java.util.Scanner;
import java.io.File;
import java.awt.Insets;

public class mainScreen {
    private JFrame frame;
    public static String fileChoice;
    public static int assetCount = 0;

    public mainScreen() {
        frame = new JFrame("Datford");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(500,500);

        // Create panel and components
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5,5,5,5);
        panel.setBackground(Color.decode("#a6a2a2"));
        JLabel assetTag = new JLabel("Asset Tag:");
        assetTag.setFont(new Font("Roboto", Font.BOLD, 14));
        JTextField assetTagEntry = new JTextField(30);
        JLabel serialNumber = new JLabel("Serial Number:");
        serialNumber.setFont(new Font("Roboto", Font.BOLD, 14));
        JTextField serialNumberEntry = new JTextField(30);
        JLabel condition = new JLabel("Condition:");
        condition.setFont(new Font("Roboto", Font.BOLD, 14));
        JTextField conditionEntry = new JTextField(30);
        JButton clearButton = new JButton("Clear");
        JButton submitButton = new JButton("Submit");
        JButton viewButton = new JButton("View File");
        JButton chooseFileButton = new JButton("Choose File");
        chooseFileButton.setToolTipText(fileChoice);


        // add components to panel
        panel.add(chooseFileButton, gbc);
        gbc.gridy++;
        panel.add(viewButton, gbc);
        gbc.gridy++;
        panel.add(assetTag, gbc);
        gbc.gridy++;
        panel.add(assetTagEntry, gbc);
        gbc.gridy++;
        panel.add(serialNumber, gbc);
        gbc.gridy++;
        panel.add(serialNumberEntry, gbc);
        gbc.gridy++;
        panel.add(condition, gbc);
        gbc.gridy++;
        panel.add(conditionEntry, gbc);
        gbc.gridy++;
        panel.add(clearButton, gbc);
        gbc.gridy++;
        panel.add(submitButton, gbc);

        // Action listeners
        clearButton.addActionListener(e -> {
            assetTagEntry.setText("");
            serialNumberEntry.setText("");
            conditionEntry.setText("");
        });
        submitButton.addActionListener (e -> {
            try {
                PrintStream fileOutput = new PrintStream(new FileOutputStream(fileChoice, true));
                String assetTagText = assetTagEntry.getText();
                String serialNumberText = serialNumberEntry.getText();
                String conditionText = conditionEntry.getText();
                assetCount = getAssetCount() + 1;
                fileOutput.println(assetCount + " , " + assetTagText + " , " + serialNumberText + " , " + conditionText);
                fileOutput.close();

            }
            catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        viewButton.addActionListener(e -> {
            JFrame viewFrame = new JFrame(fileChoice + " - File view");
            viewFrame.setSize(500,500);
            int x = frame.getX();
            int y = frame.getY();
            viewFrame.setLocation(x + 500, y);
            viewFrame.setResizable(false);
            viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JPanel viewPanel = new JPanel();
            viewPanel.setLayout(null);
            JTextArea fileContent = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(fileContent);
            JButton openFileButton = new JButton("Open File in Editor");
            openFileButton.setBounds(100,410,300,40);
            scrollPane.setBounds(0,0,500,400);
            fileContent.setEditable(false);
            fileContent.setBounds(0,0,500,400);
            viewPanel.add(scrollPane);
            viewPanel.add(openFileButton);
            viewFrame.add(viewPanel);
            viewFrame.setVisible(true);
            // Action listeners
            openFileButton.addActionListener(ev -> {
                try {
                    java.awt.Desktop.getDesktop().open(new File(fileChoice));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            try{
                Scanner file = new Scanner(new File(fileChoice));
                while(file.hasNextLine()) {
                    fileContent.append(file.nextLine() + "\n");
            } 
            file.close();
        }
            catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        chooseFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                fileChoice = selectedFile.getAbsolutePath();
                chooseFileButton.setToolTipText(fileChoice);
            }
        });


        frame.add(panel);
        frame.setVisible(true);
    }

    public static int getAssetCount() throws FileNotFoundException {
        int assetCount = 0;
        Scanner file = new Scanner(new File(fileChoice));
        while (file.hasNextLine()) {
            file.nextLine();
            assetCount++;
        }
        file.close();

        return assetCount;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(mainScreen::new);

        
    }
}
