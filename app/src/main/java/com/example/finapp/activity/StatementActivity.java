package com.example.finapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.finapp.R;
import com.example.finapp.adapter.OperationAdapter;
import com.example.finapp.helper.OperationDAO;
import com.example.finapp.model.Operation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class StatementActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OperationAdapter operationAdapter;
    private List<Operation> operationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);

        recyclerView = findViewById(R.id.reciclerViewOperationList);
    }

    @Override
    protected void onStart(){
        super.onStart();
        try {
            updateRecyclerOperation();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void updateRecyclerOperation() throws ParseException {
        OperationDAO operationDAO = new OperationDAO(getApplicationContext());
        operationList = operationDAO.getAllOperations();
        operationAdapter = new OperationAdapter(operationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(operationAdapter);
    }
}