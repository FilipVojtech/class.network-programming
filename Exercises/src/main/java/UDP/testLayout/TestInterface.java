package UDP.testLayout;

import javax.swing.*;
import java.awt.*;

/*
Text Field         Submit Btn

|---------------------------|
|        Text Area          |
|---------------------------|

                    Clear Btn
*/
// When Submitted, appends to the textarea
public class TestInterface {
    private JFrame mainFrame;

    private JPanel mainPanel;
    private JTextField inputField;
    private JButton submitButton;
    private JTextArea textArea;
    private JButton clearButton;

    public TestInterface() {
        configureMainWindow();

        configureLayout();
    }

    private void configureMainWindow() {
        mainFrame = new JFrame("Text Appender OS");
        mainFrame.setSize(300, 400);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLayout(new CardLayout());
        mainFrame.setVisible(true);
    }

    private void configureLayout() {
        // Components
        mainPanel = new JPanel(new GridBagLayout());

        inputField = new JTextField(15);
        submitButton = new JButton("Append");
        submitButton.addActionListener(_ -> {
            appendText();
        });

        textArea = new JTextArea(15, 7);
        textArea.setEditable(false);

        clearButton = new JButton("Clear All");
        clearButton.addActionListener(_ -> textArea.setText(""));

        // Configuring panel
        mainPanel.add(inputField, getGridBagConstraints(0, 0, 3));
        mainPanel.add(submitButton, getGridBagConstraints(4, 0, 1));
        mainPanel.add(textArea, getGridBagConstraints(0, 1, 5));
        mainPanel.add(clearButton, getGridBagConstraints(0, 2, 5));
    }

    private static GridBagConstraints getGridBagConstraints(int col, int row, int width) {
        // Create a constraints object to manage component placement within a frame/panel
        GridBagConstraints gbc = new GridBagConstraints();
        // Set it to fill horizontally (component will expand to fill width)
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // Add padding around the component (Pad by 5 on all sides)
        gbc.insets = new Insets(5, 5, 5, 5);

        // Set the row position to the supplied value
        gbc.gridx = col;
        // Set the column position to the supplied value
        gbc.gridy = row;
        // Set the component's width to the supplied value (in columns)
        gbc.gridwidth = width;
        return gbc;
    }

    public void appendText() {
        String text = inputField.getText();

        if (text.isEmpty()) return;

        textArea.append(text + "\n");
        inputField.setText("");
    }

    public void start() {
        this.showMainPanel();
    }

    private void showMainPanel() {
        mainFrame.add(mainPanel);
        mainPanel.setVisible(true);
    }

    public static void main(String[] args) {
        TestInterface gui = new TestInterface();

        gui.start();
    }
}
