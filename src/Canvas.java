import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Canvas extends JFrame {
    private JTextArea textArea;
    private JTextField inputField;
    public JButton okButton;
    public boolean isOKPressed = false;
    public Canvas(String title) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        textArea.setEditable(false);
        textArea.setCaretColor(Color.white);

        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(150,25));
        okButton = new JButton("OK");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(inputField);
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isOKPressed = true;
                textArea.append(inputField.getText());
            }
        });

    }

    public void textAppend(String text) {
       textArea.append(text + "\n");
    }

    public JTextField getInputField() {
        return inputField;
    }
}
