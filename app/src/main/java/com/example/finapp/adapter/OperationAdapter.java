package com.example.finapp.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finapp.R;
import com.example.finapp.model.Operation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class OperationAdapter extends RecyclerView.Adapter<OperationAdapter.MyViewHolder> {

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private List<Operation> list;

    public OperationAdapter(List<Operation> list) {
        this.list = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewOp;
        TextView textViewDescOp;
        TextView textViewDateOp;
        TextView textViewValueOp;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            textViewOp = itemView.findViewById(R.id.textViewOp);
            textViewDescOp = itemView.findViewById(R.id.textViewDescOp);
            textViewDateOp = itemView.findViewById(R.id.textViewDateOp);
            textViewValueOp = itemView.findViewById(R.id.textViewValueOp);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View operationItem =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.operation_cell, parent, false);
        return new MyViewHolder(operationItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Operation operation = list.get(position);
        holder.textViewOp.setText("Crédito".equalsIgnoreCase(operation.getOperation()) ? "C" : "D");
        holder.textViewDescOp.setText(operation.getDescription());
        holder.textViewDateOp.setText(dateFormat.format(operation.getData()));
        holder.textViewValueOp.setText("R$" + String.valueOf(operation.getValor()));
        if("C".equalsIgnoreCase(holder.textViewOp.getText().toString())){
            holder.textViewOp.setTextColor(Color.parseColor("#0000FF"));
            holder.textViewDescOp.setTextColor(Color.parseColor("#0000FF"));
            holder.textViewDateOp.setTextColor(Color.parseColor("#0000FF"));
            holder.textViewValueOp.setTextColor(Color.parseColor("#0000FF"));
        }else{
            holder.textViewOp.setTextColor(Color.parseColor("#FF0000"));
            holder.textViewDescOp.setTextColor(Color.parseColor("#FF0000"));
            holder.textViewDateOp.setTextColor(Color.parseColor("#FF0000"));
            holder.textViewValueOp.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
}
