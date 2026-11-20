package com.taller.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class CreditCalculatorActivity extends Activity {
    private EditText loanAmountInput;
    private EditText monthsInput;
    private EditText rateInput;
    private TextView resultText;
    private final NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        loanAmountInput = findViewById(R.id.loanAmountInput);
        monthsInput = findViewById(R.id.monthsInput);
        rateInput = findViewById(R.id.rateInput);
        resultText = findViewById(R.id.creditResultText);
        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(this::calculateCredit);
    }

    public void calculateCredit(View view) {
        try {
            double principal = parse(loanAmountInput);
            int months = Integer.parseInt(monthsInput.getText().toString().trim());
            double monthlyRate = parse(rateInput) / 100.0;
            if (principal <= 0 || months <= 0 || monthlyRate < 0) throw new NumberFormatException();

            double payment;
            if (monthlyRate == 0) {
                payment = principal / months;
            } else {
                payment = principal * monthlyRate
                        / (1 - Math.pow(1 + monthlyRate, -months));
            }
            double total = payment * months;
            double interest = total - principal;
            resultText.setText("Cuota mensual: " + currency.format(payment)
                    + "\nTotal a pagar: " + currency.format(total)
                    + "\nGanancia por intereses: " + currency.format(interest));
        } catch (NumberFormatException exception) {
            Toast.makeText(this, "Completa los datos con valores válidos", Toast.LENGTH_SHORT).show();
        }
    }

    private double parse(EditText input) {
        return Double.parseDouble(input.getText().toString().trim().replace(',', '.'));
    }
}
