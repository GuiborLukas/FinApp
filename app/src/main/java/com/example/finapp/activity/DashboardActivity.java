package com.example.finapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.finapp.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void registerActivity(View view){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    public void statementActivity(View view){
        Intent intent = new Intent(getApplicationContext(), StatementActivity.class);
        startActivity(intent);
    }

    public void searchActivity(View view){
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(intent);
    }

    public void filteredActivity(View view){
        Intent intent = new Intent(getApplicationContext(), FilteredActivity.class);
        startActivity(intent);
    }

    public void terminateApp(View view){
        finish();
    }
}