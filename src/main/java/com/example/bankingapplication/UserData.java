package com.example.bankingapplication;

public class UserData {
    private static String checkingBalance;
    private static String savingsBalance;

    public static String getCheckingBalance() {
        return checkingBalance;
    }

    public static void setCheckingBalance(String checkingBalance) {
        UserData.checkingBalance = checkingBalance;
    }

    public static String getSavingsBalance() {
        return savingsBalance;
    }

    public static void setSavingsBalance(String savingsBalance) {
        UserData.savingsBalance = savingsBalance;
    }


}
