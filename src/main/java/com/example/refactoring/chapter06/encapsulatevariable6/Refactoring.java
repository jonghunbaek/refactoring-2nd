package com.example.refactoring.chapter06.encapsulatevariable6;

public class Refactoring {

    public static void main(String[] args) {
        Spaceship spaceship = new Spaceship(new Owner(new DataRefactoring().getDefaultOwner()));
    }
}
