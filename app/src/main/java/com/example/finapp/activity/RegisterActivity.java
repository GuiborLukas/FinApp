package com.example.finapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.finapp.R;

public class RegisterActivity extends AppCompatActivity {

    RadioGroup radioGroupOperation;
    RadioButton checkedRadioButton;
    Spinner spinnerDescription;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        radioGroupOperation = (RadioGroup) findViewById(R.id.radioGroupOperation);
        checkedRadioButton = (RadioButton)radioGroupOperation.findViewById(radioGroupOperation.getCheckedRadioButtonId());
        spinnerDescription = (Spinner) findViewById(R.id.spinnerDescription);

    radioGroupOperation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup arg0, int id) {
            switch (id) {
                case R.id.radioButtonCredit:
                    adapter = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.credit_descriptions, android.R.layout.simple_spinner_item);
                    spinnerDescription.setAdapter(adapter);
                    break;
                case R.id.radioButtonDebit:
                    adapter = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.debit_descriptions, android.R.layout.simple_spinner_item);
                    spinnerDescription.setAdapter(adapter);
                    break;
            }
        }
    });
    }
}