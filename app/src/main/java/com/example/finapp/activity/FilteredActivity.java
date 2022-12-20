package com.example.finapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finapp.R;
import com.example.finapp.adapter.OperationAdapter;
import com.example.finapp.helper.OperationDAO;
import com.example.finapp.model.DateOperationComparator;
import com.example.finapp.model.Operation;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilteredActivity extends AppCompatActivity {

    private DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private TextView textViewTotalDebitos;
    private TextView textViewTotalCreditos;

    private TextView textViewTotalMoradia;
    private TextView textViewTotalSaude;
    private TextView textViewTotalOutrosD;

    private TextView textViewTotalSalario;
    private TextView textViewTotalOutrosC;

    private RecyclerView recyclerViewMoradia;
    private RecyclerView recyclerViewSaude;
    private RecyclerView recyclerViewOutrosD;

    private RecyclerView recyclerViewSalario;
    private RecyclerView recyclerViewOutrosC;

    private OperationDAO operationDAO;

    private OperationAdapter operationAdapter;

    private List<Operation> operationListFull = new ArrayList<>();
    private List<Operation> operationListCredit = new ArrayList<>();
    private List<Operation> operationListDebit = new ArrayList<>();
    private List<Operation> operationListMoradia = new ArrayList<>();
    private List<Operation> operationListSaude = new ArrayList<>();
    private List<Operation> operationListOutrosD = new ArrayList<>();
    private List<Operation> operationListSalario = new ArrayList<>();
    private List<Operation> operationListOutrosC = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered);

        operationDAO = new OperationDAO(getApplicationContext());
        textViewTotalDebitos = findViewById(R.id.textViewTotalDebitos);
        textViewTotalCreditos = findViewById(R.id.textViewTotalCreditos);
        textViewTotalMoradia = findViewById(R.id.textViewTotalMoradia);
        recyclerViewMoradia = findViewById(R.id.recyclerViewMoradia);
        textViewTotalSaude = findViewById(R.id.textViewTotalSaude);
        recyclerViewSaude = findViewById(R.id.recyclerViewSaude);
        textViewTotalOutrosD = findViewById(R.id.textViewTotalOutrosD);
        recyclerViewOutrosD = findViewById(R.id.recyclerViewOutrosD);
        textViewTotalSalario = findViewById(R.id.textViewTotalSalario);
        recyclerViewSalario = findViewById(R.id.recyclerViewSalario);
        textViewTotalOutrosC = findViewById(R.id.textViewTotalOutrosC);
        recyclerViewOutrosC = findViewById(R.id.recyclerViewOutrosC);

    }

    @Override
    protected void onStart(){
        super.onStart();
        try {
            distributeIntoArrays();
            textViewTotalCreditos.setText("R$ " + String.valueOf(decimalFormat.format(calculaTotal(operationListCredit))));
            textViewTotalDebitos.setText("R$ " + String.valueOf(decimalFormat.format(calculaTotal(operationListDebit))));
            updateRecyclerOperation(textViewTotalMoradia, operationListMoradia, recyclerViewMoradia);
            updateRecyclerOperation(textViewTotalSaude, operationListSaude, recyclerViewSaude);
            updateRecyclerOperation(textViewTotalOutrosD, operationListOutrosD, recyclerViewOutrosD);
            updateRecyclerOperation(textViewTotalSalario, operationListSalario, recyclerViewSalario);
            updateRecyclerOperation(textViewTotalOutrosC, operationListOutrosC, recyclerViewOutrosC);
        } catch (ParseException e) {
            Toast.makeText(this,"Erro na consulta aos dados", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void updateRecyclerOperation(TextView textView, List<Operation> operationList, RecyclerView recyclerView) throws ParseException {
        textView.setText("R$ " + String.valueOf(decimalFormat.format(calculaTotal(operationList))));
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
                total += o.getValor();
        }
        return total;
    }

    public void distributeIntoArrays() throws ParseException {
        operationListFull = operationDAO.getAllOperations();
        for(Operation o: operationListFull){
            if("Crédito".equalsIgnoreCase(o.getOperation())){
                operationListCredit.add(o);
                switch (o.getDescription()){
                    case "Salário":{operationListSalario.add(o);
                        break;}
                    case "Outros":{operationListOutrosC.add(o);
                        break;}
                }
            }else{
                operationListDebit.add(o);
                switch (o.getDescription()){
                    case "Moradia":{operationListMoradia.add(o);
                        break;}
                    case "Saúde":{operationListSaude.add(o);
                        break;}
                    case "Outros":{operationListOutrosD.add(o);
                        break;}
                }
            }
        }
    }



}