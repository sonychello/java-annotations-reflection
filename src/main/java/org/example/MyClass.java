package org.example;

public class MyClass {
    private void printMassage(String obj) {
        System.out.println(obj);
    }

    @Sun(2)
    private void printMenu(int nGuest, String[] objs) {
        System.out.println("For our " + nGuest + " we have next menu:");
        for (String position : objs) {
            System.out.println(position);
        }
    }

     public void printHello() {
        System.out.println("Hello");
    }

    @Sun(1)
    public void printBuka(boolean flag) {
        if (flag) { System.out.println("Buka"); }
        else { System.out.println("Ne Buka"); }
    }

    @Sun(1)
    protected void printCheque(double cheque, double balance) {
        if (cheque > balance) {
             System.out.println((double) (cheque - balance) +" rubles are missing");
        } else {
            System.out.println("Your change " + (double)(balance - cheque));
        }
    }

    @Sun(2)
    protected void complexMethod(MyClass self, int number, String text, boolean flag) {
        System.out.println("Complex: number=" + number + ", text='" + text + "', flag=" + flag + ", self=" + self);
    }

}
