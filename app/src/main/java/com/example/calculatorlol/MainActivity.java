package com.example.calculatorlol;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView one, two, three, four, five, six, seven, eight, nine, zero, AC, add, minus, tvdivide, multiply, period, plusOrMinus, equal, percent, operation, result;

    private String currentNumber = "";
    private String firstNumber = "";
    private String operator = "";
    private boolean isDecimal = false;  // To keep track of whether a decimal point has been added

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TextViews
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);
        AC = findViewById(R.id.AC);
        add = findViewById(R.id.add);
        minus = findViewById(R.id.minus);
        tvdivide = findViewById(R.id.tvdivide);
        multiply = findViewById(R.id.multiply);
        period = findViewById(R.id.period);
        plusOrMinus = findViewById(R.id.plusOrMinus);
        equal = findViewById(R.id.equal);
        percent = findViewById(R.id.percent);
        operation = findViewById(R.id.operation);
        result = findViewById(R.id.result);

        // Set OnClickListeners for number buttons
        setNumberClickListener(one, "1");
        setNumberClickListener(two, "2");
        setNumberClickListener(three, "3");
        setNumberClickListener(four, "4");
        setNumberClickListener(five, "5");
        setNumberClickListener(six, "6");
        setNumberClickListener(seven, "7");
        setNumberClickListener(eight, "8");
        setNumberClickListener(nine, "9");
        setNumberClickListener(zero, "0");

        // Set OnClickListeners for operator buttons
        add.setOnClickListener(v -> setOperator("+"));
        minus.setOnClickListener(v -> setOperator("-"));
        tvdivide.setOnClickListener(v -> setOperator("/"));
        multiply.setOnClickListener(v -> setOperator("*"));

        //   for equal button
        equal.setOnClickListener(v -> performCalculation());

        // Set  for clear button
        AC.setOnClickListener(v -> clear());

        //   for decimal point
        period.setOnClickListener(v -> addDecimalPoint());

        //   for plus/minus
        plusOrMinus.setOnClickListener(v -> toggleSign());

        //   for percent
        percent.setOnClickListener(v -> applyPercentage());
    }

    private void setNumberClickListener(TextView numberView, String number) {
        numberView.setOnClickListener(v -> {
            currentNumber += number;
            result.setText(currentNumber);
            Log.d("Calculator", "Current Number: " + currentNumber);  // Debug statement
        });
    }

    private void setOperator(String op) {
        if (!currentNumber.isEmpty()) {
            firstNumber = currentNumber;
            currentNumber = "";
            operator = op;
            isDecimal = false;  // Reset decimal  when an operator is set
            operation.setText(firstNumber + " " + operator);  // Update operation
            Log.d("Calculator", "Operator Set: " + operator);  // message
        } else if (!firstNumber.isEmpty() && !operator.isEmpty()) {
            // Allow changing of the operator
            operator = op;
            operation.setText(firstNumber + " " + operator);  // Update operation display
            Log.d("Calculator", "Operator Changed: " + operator);  // message
        }
    }

    private void performCalculation() {
        if (!firstNumber.isEmpty() && !currentNumber.isEmpty() && !operator.isEmpty()) {
            try {
                double num1 = Double.parseDouble(firstNumber);
                double num2 = Double.parseDouble(currentNumber);
                double resultValue = 0;

                switch (operator) {
                    case "+":
                        resultValue = num1 + num2;
                        break;
                    case "-":
                        resultValue = num1 - num2;
                        break;
                    case "*":
                        resultValue = num1 * num2;
                        break;
                    case "/":
                        if (num2 != 0) {
                            resultValue = num1 / num2;
                        } else {
                            result.setText("Error");
                            return;
                        }
                        break;
                }
                result.setText(formatResult(resultValue));
                operation.setText("");  // Clear operation display
                Log.d("Calculator", "Result: " + resultValue);  // message lol
                currentNumber = String.valueOf(resultValue);
                firstNumber = "";
                operator = "";
            } catch (NumberFormatException e) {
                result.setText("Error");
                Log.e("Calculator", "NumberFormatException: " + e.getMessage());  // message
            }
        }
    }

    private String formatResult(double value) {
        return String.format("%,.2f", value);
    }

    private void clear() {
        currentNumber = "";
        firstNumber = "";
        operator = "";
        isDecimal = false;
        result.setText("");
        operation.setText("");  // Clear
        Log.d("Calculator", "Cleared");
    }

    private void addDecimalPoint() {
        if (!isDecimal) {
            if (currentNumber.isEmpty()) {
                currentNumber = "0.";  // Start with 0 if no number is present
            } else {
                currentNumber += ".";
            }
            isDecimal = true;  // Set to true once decimal is added
            result.setText(currentNumber);
            Log.d("Calculator", "Current Number with Decimal: " + currentNumber);
        }
    }

    private void toggleSign() {
        if (!currentNumber.isEmpty()) {
            double number = Double.parseDouble(currentNumber);
            number = -number;  // idk
            currentNumber = String.valueOf(number);
            result.setText(currentNumber);
            Log.d("Calculator", "Toggled Sign: " + currentNumber);
        }
    }

    private void applyPercentage() {
        if (!currentNumber.isEmpty()) {
            double number = Double.parseDouble(currentNumber);
            number = number / 100;  // Convert to percentage
            currentNumber = String.valueOf(number);
            result.setText(currentNumber);
            Log.d("Calculator", "Percentage Applied: " + currentNumber);  // Debug statement
        }
    }
}
