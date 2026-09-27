package org.example;


public class Main {
    public static void main(String[] args) {
        try {
            Call caller = new Call();
            caller.invokeAnnotatedMethods();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}