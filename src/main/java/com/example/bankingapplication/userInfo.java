package com.example.bankingapplication;

public class userInfo {
    private static userInfo instance;
    static String firstName;
    static String lastName;
    static String dob;
    static String username;
    static String password;
    static String checking;
    static String savings;
    static String id;
    static String address;
    static String zipCode;
    static String cardNum;
    static String cardExp;
    static String cardCVV;

    private userInfo () {}

    public static synchronized userInfo getInstance() {
        if (instance == null) {
            instance = new userInfo();
        }
        return instance;
    }

    public userInfo(String firstName, String lastName, String dob, String username, String password,
                    String checking, String savings, String address, String zipCode, String cardNum,
                    String cardExp, String cardCVV, String id) {
        userInfo.firstName = firstName;
        userInfo.lastName = lastName;
        userInfo.dob = dob;
        userInfo.username = username;
        userInfo.password = password;
        userInfo.checking = checking;
        userInfo.savings = savings;
        userInfo.address = address;
        userInfo.zipCode = zipCode;
        userInfo.cardNum = cardNum;
        userInfo.cardExp = cardExp;
        userInfo.cardCVV = cardCVV;
        userInfo.id = id;
    }
    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String fn) {
        firstName = fn;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String ln) {
        lastName = ln;
    }

    public static String getDob() {
        return dob;
    }

    public static void setDob(String db) {
        dob = db;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String un) {
        username = un;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String pwd) {
        password = pwd;
    }

    public static String getChecking() {
        return checking;
    }

    public static void setChecking(String c) {
        checking = c;
    }

    public static String getSavings() {
        return savings;
    }

    public static void setSavings(String s) {
        savings = s;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String ID) { id = ID;}

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String addy) {
        address = addy;
    }

    public static String getZipCode() {
        return zipCode;
    }

    public static void setZipCode(String zip) {
        zipCode = zip;
    }

    public static String getCardNum() {
        return cardNum;
    }

    public static void setCardNum(String cardNum) {
        userInfo.cardNum = cardNum;
    }

    public static String getCardExp() {
        return cardExp;
    }

    public static void setCardExp(String cardExp) {
        userInfo.cardExp = cardExp;
    }

    public static String getCardCVV() {
        return cardCVV;
    }

    public static void setCardCVV(String cardCVV) {
        userInfo.cardCVV = cardCVV;
    }
}
