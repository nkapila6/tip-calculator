package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private EditText baseAmount;
    private SeekBar seekBarTip;
    private TextView tipAmount;
    private TextView tipPercent;
    private TextView totalAmount;
    private static final String TAG = "MainActivity";
    private static int INITIAL_TIP_PERCENT = 15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        baseAmount = (EditText) findViewById(R.id.etBaseAmount);
        seekBarTip = (SeekBar) findViewById(R.id.seekBarTip);
        tipAmount = (TextView) findViewById(R.id.tvTipAmount);
        tipPercent = (TextView) findViewById(R.id.tvTipPercentLabel);
        totalAmount = (TextView) findViewById(R.id.tvTotalAmount);
//        baseAmount = findViewById(R.id.etBaseAmount);
//        tipAmount = findViewById(R.id.tvTipAmount);
//        tipPercent = findViewById(R.id.tvTipPercentLabel);
//        totalAmount = findViewById(R.id.tvTotalAmount);
        seekBarTip.setProgress(INITIAL_TIP_PERCENT);
        tipPercent.setText(String.valueOf(INITIAL_TIP_PERCENT)+"%");
        seekBarTip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override

        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            Log.i(TAG, "Seekbar progress change " + i);
            tipPercent.setText(String.valueOf(i) + "%");
            computeTipTotal();
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        baseAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG, "Base amount changed " + editable);
                computeTipTotal();
            }
        });
    }

    private void computeTipTotal() {
        if (baseAmount.getText().toString().isEmpty()){
            tipAmount.setText("");
            totalAmount.setText("");
            return;
        }
        // Get value of base and tip
        float base = Float.parseFloat(baseAmount.getText().toString());
        Log.i(TAG, "Base amount fetched " + String.valueOf(base));
        float seek = (float) seekBarTip.getProgress();
        Log.i(TAG, "Seek bar tip fetched " + String.valueOf(seek));
        // Compute tip and total
        float tip = base * seek / 100;
        float tot = base + tip;
        String tipString = String.format("%.2f", tip);
        String totString = String.format("%.2f", tot);
        // update the UI
        tipAmount.setText(tipString);
        totalAmount.setText(totString);
    }
}