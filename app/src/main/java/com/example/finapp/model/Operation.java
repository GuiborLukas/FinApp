package com.example.finapp.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Operation implements Serializable {
    private Long id;
    private Double valor;
    private String operation;
    private String description;
    private Date data;

    public Operation() {
    }

    public Operation(Double valor, String operation, String description, Date data) {
        this.valor = valor;
        this.operation = operation;
        this.description = description;
        this.data = data;
    }

    public Operation(Long id, Double valor, String operation, String description, Date data) {
        this.id = id;
        this.valor = valor;
        this.operation = operation;
        this.description = description;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) { this.data = data; }

}
