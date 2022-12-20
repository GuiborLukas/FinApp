package com.example.finapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finapp.R;
import com.example.finapp.helper.OperationDAO;
import com.example.finapp.model.Operation;

import java.text.ParseException;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextDate;
    private EditText editTextValue;
    private Operation currentOperation;

    RadioGroup radioGroupOperation;
    RadioButton checkedRadioButton;
    Spinner spinnerDescription;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        radioGroupOperation = findViewById(R.id.radioGroupOperation);
        checkedRadioButton = radioGroupOperation.findViewById(radioGroupOperation.getCheckedRadioButtonId());
        spinnerDescription = findViewById(R.id.spinnerDescription);
        editTextValue = findViewById(R.id.editTextValue);
        editTextDate = findViewById(R.id.editTextDate);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.itemAction){
            OperationDAO operationDAO = new OperationDAO(getApplicationContext());
            if(currentOperation != null) {
                String strNewValue = editTextValue.getText().toString();
                String strNewDate = editTextDate.getText().toString();
                RadioButton rb = (RadioButton) findViewById(
                        radioGroupOperation.getCheckedRadioButtonId());
                String strNewOperation = rb.getText().toString();
                String strNewDescription = spinnerDescription.getSelectedItem().toString();

                if (strNewValue.isEmpty()
                        || strNewDate.isEmpty()
                        || strNewOperation.isEmpty()
                        || strNewDescription.isEmpty()
                ){
                    Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        Operation operation = new Operation();
                        operation.setId(currentOperation.getId());
                        operation.setValor(Double.parseDouble(strNewValue));
                        operation.setOperation(strNewOperation);
                        operation.setDescription(strNewDescription);
                        operation.setData(operationDAO.strToDate(strNewDate));
                        if (operationDAO.updateOperation(operation)) {
                            finish();
                            Toast.makeText(this, "Operação salva", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Erro ao salvar operação", Toast.LENGTH_SHORT).show();
                        }
                    }catch(NumberFormatException e){
                        Toast.makeText(this, "Valor deve ser numérico!", Toast.LENGTH_SHORT).show();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


            }else{
                String strNewValue = editTextValue.getText().toString();
                String strNewDate = editTextDate.getText().toString();
                RadioButton rb = (RadioButton) findViewById(
                        radioGroupOperation.getCheckedRadioButtonId());
                String strNewOperation = rb.getText().toString();
                String strNewDescription = spinnerDescription.getSelectedItem().toString();

                if (strNewValue.isEmpty()
                    || strNewDate.isEmpty()
                    || strNewOperation.isEmpty()
                    || strNewDescription.isEmpty()
                ){
                    Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        Operation operation = new Operation();
                        operation.setValor(Double.parseDouble(strNewValue));
                        operation.setOperation(strNewOperation);
                        operation.setDescription(strNewDescription);
                        operation.setData(operationDAO.strToDate(strNewDate));

                        if (operationDAO.insertOperation(operation)) {
                            finish();
                            Toast.makeText(this, "Operação salva", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Erro ao salvar operação", Toast.LENGTH_SHORT).show();
                        }
                    }catch(NumberFormatException e){
                        Toast.makeText(this, "Valor deve ser numérico!", Toast.LENGTH_SHORT).show();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return super.onOptionsItemSelected(item);
    }



}