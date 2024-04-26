package com.example.bankingapplication;

public class userInfo {
    private static userInfo instance;
    static String firstName;
    static String lastName;
    String dob;
    static String username;
    String password;
    static String checking;
    static String savings;
    String id;
    String address;
    String zipCode;

    private userInfo () {}

    public static synchronized userInfo getInstance() {
        if (instance == null) {
            instance = new userInfo();
        }
        return instance;
    }

    public userInfo(String firstName, String lastName, String dob, String username, String password,
                    String checking, String savings, String address, String zipCode, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.username = username;
        this.password = password;
        this.checking = checking;
        this.savings = savings;
        this.address = address;
        this.zipCode = zipCode;
        this.id = id;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String un) {
        username = un;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
