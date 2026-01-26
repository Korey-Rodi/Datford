import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.swing.*;
import java.util.Scanner;
import java.io.File;

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
        panel.setBackground(Color.decode("#a5c2f0"));
        panel.setLayout(null);
        JLabel assetTag = new JLabel("Asset Tag:");
        JTextField assetTagEntry = new JTextField();
        JLabel serialNumber = new JLabel("Serial Number:");
        JTextField serialNumberEntry = new JTextField();
        JLabel condition = new JLabel("Condition:");
        JTextField conditionEntry = new JTextField();
        JButton clearButton = new JButton("Clear");
        JButton submitButton = new JButton("Submit");
        JButton viewButton = new JButton("View File");
        JButton chooseFileButton = new JButton("Choose File");

        // Set bounds
        assetTag.setBounds(210,20,165,40);
        assetTagEntry.setBounds(100,45,300,40);
        serialNumber.setBounds(200,70,100,40);
        serialNumberEntry.setBounds(100,95,300,40);
        condition.setBounds(210,120,100,40);
        conditionEntry.setBounds(100,145,300,40);
        clearButton.setBounds(100,250,300,40);
        submitButton.setBounds(100,300,300,40);
        viewButton.setBounds(100,350,300,40);
        chooseFileButton.setBounds(100,400,300,40);

        // add components to panel
        panel.add(assetTag);
        panel.add(assetTagEntry);
        panel.add(serialNumber);
        panel.add(serialNumberEntry);
        panel.add(condition);
        panel.add(conditionEntry);
        panel.add(clearButton);
        panel.add(submitButton);
        panel.add(viewButton);
        panel.add(chooseFileButton);

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
