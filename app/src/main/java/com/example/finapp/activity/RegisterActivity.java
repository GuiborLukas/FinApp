package com.example.finapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.finapp.R;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    RadioGroup radioGroupOperation;
    RadioButton checkedRadioButton;
    Spinner spinnerDescription;
    ArrayAdapter adapter;
    EditText editTextDate;

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

        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextDate.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMAAAA";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    editTextDate.setText(current);
                    editTextDate.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        }





}