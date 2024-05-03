package com.example.bankingapplication;

public class transactionsInfo {
    private static transactionsInfo instance;
    static String name;
    static String category;
    static String amount;
    static String date;

    private transactionsInfo () {}

    public static synchronized transactionsInfo getInstance() {
        if (instance == null) {
            instance = new transactionsInfo();
        }
        return instance;
    }

    public transactionsInfo (String name, String category, String amount, String date) {
        transactionsInfo.name = name;
        transactionsInfo.category = category;
        transactionsInfo.amount = amount;
        transactionsInfo.date = date;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        transactionsInfo.name = name;
    }

    public static String getCategory() {
        return category;
    }

    public static void setCategory(String category) {
        transactionsInfo.category = category;
    }

    public static String getAmount() {
        return amount;
    }

    public static void setAmount(String amount) {
        transactionsInfo.amount = amount;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        transactionsInfo.date = date;
    }
}
