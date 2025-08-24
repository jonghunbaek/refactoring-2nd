package com.example.refactoring.chapter11.exceptionWithPrecheck13;

import java.util.Deque;
import java.util.List;

public class Refactoring {

    private Deque<Resource> available;
    private List<Resource> allocated;

    public Resource get() {
        Resource result = available.isEmpty() ? Resource.create() : available.pop();
        allocated.add(result);

        return result;
    }
}
