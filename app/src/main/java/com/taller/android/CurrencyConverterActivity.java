package com.taller.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyConverterActivity extends Activity {
    private final String[] currencies = {"COP - Peso colombiano", "USD - Dólar", "EUR - Euro", "MXN - Peso mexicano"};
    private final double[] ratesToUsd = {0.00025, 1.0, 1.087, 0.054};
    private EditText amountInput;
    private Spinner fromCurrency;
    private Spinner toCurrency;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        amountInput = findViewById(R.id.amountInput);
        fromCurrency = findViewById(R.id.fromCurrency);
        toCurrency = findViewById(R.id.toCurrency);
        resultText = findViewById(R.id.resultText);
        Button convertButton = findViewById(R.id.convertButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromCurrency.setAdapter(adapter);
        toCurrency.setAdapter(adapter);
        toCurrency.setSelection(1);
        convertButton.setOnClickListener(this::convertCurrency);
    }

    public void convertCurrency(View view) {
        String rawAmount = amountInput.getText().toString().trim().replace(',', '.');
        if (rawAmount.isEmpty()) {
            amountInput.setError("Ingresa una cantidad");
            return;
        }

        try {
            double amount = Double.parseDouble(rawAmount);
            if (amount < 0) throw new NumberFormatException();
            int from = fromCurrency.getSelectedItemPosition();
            int to = toCurrency.getSelectedItemPosition();
            double converted = amount * ratesToUsd[from] / ratesToUsd[to];
            NumberFormat format = NumberFormat.getNumberInstance(new Locale("es", "CO"));
            format.setMinimumFractionDigits(2);
            format.setMaximumFractionDigits(2);
            resultText.setText(format.format(amount) + " " + code(from)
                    + " = " + format.format(converted) + " " + code(to));
        } catch (NumberFormatException exception) {
            Toast.makeText(this, "Ingresa un valor numérico válido", Toast.LENGTH_SHORT).show();
        }
    }

    private String code(int position) {
        return currencies[position].substring(0, 3);
    }
}
