import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame implements ActionListener{
    private JTextField displayField;
    private JButton[] buttons;
    private String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "Square", "%", "C", "Backspace"
    };
    private String currentInput = "";
    private double result = 0;
    private char lastOperator;

    public CalculatorGUI() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 350);

        displayField = new JTextField(20);
        displayField.setEditable(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4));

        buttons = new JButton[buttonLabels.length];

        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(displayField, BorderLayout.NORTH);
        contentPanel.add(buttonPanel, BorderLayout.CENTER);

        setContentPane(contentPanel);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "+":
            case "-":
            case "*":
            case "/":
                performOperation(command.charAt(0));
                break;
            case "=":
                calculateResult();
                break;
            case "Square":
                square();
                break;
            case "%":
                calculatePercentage();
                if (!currentInput.isEmpty()) {
                    double num = Double.parseDouble(currentInput);
                    if (currentInput.contains("%")) {
                        // Remove the percentage sign and then multiply by 100
                        num = num * 100;
                        displayField.setText(String.valueOf(num));
                    } else {
                        // Standard percentage calculation
                        result = num * 100;
                        displayField.setText(String.valueOf(result));
                    }
                    currentInput = "";
                }


                break;
            case "C":
                clear();
                break;
            case "Backspace":
                if (!currentInput.isEmpty()) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                    displayField.setText(currentInput);
                }
                break;
            default:
                currentInput += command;
                displayField.setText(currentInput);
        }
    }

    private void performOperation(char operator) {
        if (!currentInput.isEmpty()) {
            result = Double.parseDouble(currentInput);
            currentInput = "";
            lastOperator = operator;
        }
    }

    private void calculateResult() {
        if (!currentInput.isEmpty()) {
            double secondOperand = Double.parseDouble(currentInput);
            switch (lastOperator) {
                case '+':
                    result += secondOperand;
                    break;
                case '-':
                    result -= secondOperand;
                    break;
                case '*':
                    result *= secondOperand;
                    break;
                case '/':
                    if (secondOperand != 0)
                        result /= secondOperand;
                    else
                        displayField.setText("Error: Division by zero");
                    break;
            }
            displayField.setText(String.valueOf(result));
            currentInput = "";
        }
    }
    private void square() {
        if (!currentInput.isEmpty()) {
            double num = Double.parseDouble(currentInput);
            result = num * num;
            displayField.setText(String.valueOf(result));
            currentInput = "";
        }
    }

    private void calculatePercentage() {
        if (!currentInput.isEmpty()) {
            double num = Double.parseDouble(currentInput);
            result = num * 100;
            displayField.setText(String.valueOf(result));
            currentInput = "";
        }
    }

    private void clear() {
        currentInput = "";
        result = 0;
        lastOperator = '\0';
        displayField.setText("");
    }

    public static void main(String[] args) {
        new CalculatorGUI();
    }
}
