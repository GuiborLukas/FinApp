package com.example.finapp.model;

import java.util.Comparator;

public class DateOperationComparator implements Comparator<Operation> {

    @Override
    public int compare(Operation operation, Operation operation2) {
        return operation.getData().compareTo(operation2.getData());
    }
}
