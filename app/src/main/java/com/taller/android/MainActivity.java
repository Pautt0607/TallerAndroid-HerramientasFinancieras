package com.taller.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnConverter).setOnClickListener(view ->
                startActivity(new Intent(this, CurrencyConverterActivity.class)));
        findViewById(R.id.btnCredit).setOnClickListener(view ->
                startActivity(new Intent(this, CreditCalculatorActivity.class)));
    }
}
