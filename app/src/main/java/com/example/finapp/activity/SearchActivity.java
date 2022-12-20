package com.example.finapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.finapp.R;
import com.example.finapp.adapter.OperationAdapter;
import com.example.finapp.helper.OperationDAO;
import com.example.finapp.model.DateOperationComparator;
import com.example.finapp.model.Operation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText editTextDateInitial;
    private EditText editTextDateFinal;
    private CheckBox checkBoxCredit;
    private CheckBox checkBoxDebit;
    private RecyclerView recyclerView;
    private OperationAdapter operationAdapter;
    private List<Operation> operationList = new ArrayList<>();
    private OperationDAO operationDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editTextDateInitial = findViewById(R.id.editTextDateInitial);
        editTextDateFinal = findViewById(R.id.editTextDateFinal);
        checkBoxCredit = findViewById(R.id.checkBoxCredit);
        checkBoxDebit = findViewById(R.id.checkBoxDebit);
        recyclerView = findViewById(R.id.recyclerViewSearchResult);
        operationDAO = new OperationDAO(getApplicationContext());


        editTextDateInitial.addTextChangedListener(new TextWatcher() {
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

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    editTextDateInitial.setText(current);
                    editTextDateInitial.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextDateFinal.addTextChangedListener(new TextWatcher() {
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

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    editTextDateFinal               .setText(current);
                    editTextDateFinal          .setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.itemAction){
            try {
                operationList = operationDAO.getAllOperations();
            } catch (ParseException e) {
                Toast.makeText(this, "Erro ao converter dados", Toast.LENGTH_SHORT).show();
                return false;
            }
            String strDataInicial = editTextDateInitial.getText().toString();
            if(!strDataInicial.isEmpty()){
                try {
                    Date dataInicial = OperationDAO.strToDate(strDataInicial);
                    operationList = filterDatesGreaterThan(dataInicial);
                } catch (ParseException e) {
                    Toast.makeText(this, "Data Inicial Inválida", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            String strDataFinal = editTextDateFinal.getText().toString();
            if(!strDataFinal.isEmpty()){
                try {
                    Date dataFinal = OperationDAO.strToDate(strDataFinal);
                    operationList = filterDatesSmallerThan(dataFinal);
                } catch (ParseException e) {
                    Toast.makeText(this, "Data Final Inválida", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            if(!checkBoxCredit.isChecked()){
                operationList = filterByOperation(checkBoxCredit.getText().toString());
            }
            if(!checkBoxDebit.isChecked()){
                operationList = filterByOperation(checkBoxDebit.getText().toString());
            }
            try {
                updateRecyclerSearchOperation();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateRecyclerSearchOperation() throws ParseException {
        Collections.sort(operationList, new DateOperationComparator());
        List<Operation> lastOperationList = operationList.subList(0, operationList.size()>15 ? 14 : operationList.size());
        operationAdapter = new OperationAdapter(lastOperationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(operationAdapter);
    }

    private List<Operation> filterDatesGreaterThan(Date date){
        List<Operation> newList = new ArrayList<>();
        for(Operation o : operationList){
            if(o.getData().compareTo(date) >= 0){
                newList.add(o);
            }
        }
        return newList;
    }

    private List<Operation> filterDatesSmallerThan(Date date){
        List<Operation> newList = new ArrayList<>();
        for(Operation o : operationList){
            if(o.getData().compareTo(date) <= 0){
                newList.add(o);
            }
        }
        return newList;
    }

    private List<Operation> filterByOperation(String operation){
        List<Operation> newList = new ArrayList<>();
        for(Operation o : operationList){
            if(!operation.equalsIgnoreCase(o.getOperation())){
                newList.add(o);
            }
        }
        return newList;
    }
}