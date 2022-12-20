package com.example.finapp.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.finapp.model.Operation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

public class OperationDAO {

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public OperationDAO(Context context){
        DBHelper db = new DBHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    public boolean insertOperation(Operation operation){
        ContentValues values = new ContentValues();
        values.put("valor", operation.getValor());
        values.put("operation", operation.getOperation());
        values.put("description", operation.getDescription());
        values.put("data", dateToStr(operation.getData()));

        try{
            write.insert(DBHelper.TABLE1_NAME, null, values);
            Log.i("INFO", "Tarefa salva com sucesso!");
        }catch (Exception e) {
            Log.e("INFO DB", "Erro ao salvar tarefa: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateOperation(Operation operation){
        ContentValues values = new ContentValues();
        values.put("valor", operation.getValor());
        values.put("operation", operation.getOperation());
        values.put("description", operation.getDescription());
        values.put("data", dateToStr(operation.getData()));
        try{
            String[] args = {operation.getId().toString()};
            write.update(DBHelper.TABLE1_NAME, values, "id=?", args);
            Log.i("INFO", "Tarefa atualizada com sucesso!");
        }catch (Exception e) {
            Log.e("INFO DB", "Erro ao atualizar tarefa: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteOperation(Operation operation){
        try{
            String[] args = {operation.getId().toString()};
            write.delete(DBHelper.TABLE1_NAME, "id=?", args);
            Log.i("INFO", "Tarefa removida com sucesso!");
        }catch (Exception e) {
            Log.e("INFO DB", "Erro ao remover tarefa: " + e.getMessage());
            return false;
        }
        return true;
    }

    public List<Operation> getAllOperations() throws ParseException {
        List<Operation> operationList = new ArrayList<>();
        Cursor cursor = read.query(DBHelper.TABLE1_NAME, new String[]{"id", "valor", "operation", "description", "data"},
                null, null, null,null, null);

//        String sql = "SELECT * FROM " + DBHelper.TABLE1_NAME + ";";
//        Cursor cursor = read.rawQuery(sql, null);

        while(cursor.moveToNext()){
            Operation operationInstance = new Operation();
            @SuppressLint("Range") Long id = cursor.getLong(cursor.getColumnIndex("id"));
            @SuppressLint("Range") double valor = cursor.getDouble(cursor.getColumnIndex("valor"));
            @SuppressLint("Range") String operation = cursor.getString(cursor.getColumnIndex("operation"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") Date data = strToDate(cursor.getString(cursor.getColumnIndex("data")));

            operationInstance.setId(id);
            operationInstance.setValor(valor);
            operationInstance.setOperation(operation);
            operationInstance.setDescription(description);
            operationInstance.setData(data);
            operationList.add(operationInstance);
        }
        return operationList;
    }

    public static String dateToStr(Date date){
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    String strDate = dateFormat.format(date);
        return strDate;
    }

    public static Date strToDate(String data) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(data);
    }
}
