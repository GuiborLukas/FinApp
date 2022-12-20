package com.example.finapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finapp.R;
import com.example.finapp.adapter.OperationAdapter;
import com.example.finapp.helper.OperationDAO;
import com.example.finapp.model.Operation;
import com.example.finapp.model.DateOperationComparator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatementActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OperationAdapter operationAdapter;
    private List<Operation> operationList = new ArrayList<>();
    private TextView textViewValueSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);

        recyclerView = findViewById(R.id.reciclerViewOperationList);
        textViewValueSaldo = findViewById(R.id.textViewValueSaldo);
    }

    @Override
    protected void onStart(){
        super.onStart();
        try {
            updateRecyclerOperation();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        textViewValueSaldo.setText(String.valueOf(calculaTotal(operationList)));
            if(Double.parseDouble(textViewValueSaldo.getText().toString()) >= 0 ){
                textViewValueSaldo.setTextColor(Color.parseColor("#0000FF"));
            }else{
                textViewValueSaldo.setTextColor(Color.parseColor("#FF0000"));
            }
    }

    public void updateRecyclerOperation() throws ParseException {
        OperationDAO operationDAO = new OperationDAO(getApplicationContext());
        operationList = operationDAO.getAllOperations();
        Collections.sort(operationList, new DateOperationComparator());
        operationAdapter = new OperationAdapter(operationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(operationAdapter);
    }

    public double calculaTotal(List<Operation> list){
        double total = 0;
        for(Operation o: list){
            if("Crédito".equalsIgnoreCase(o.getOperation())){
                total += o.getValor();
            }else{
                total -= o.getValor();
            }
        }
        return total;
    }
}