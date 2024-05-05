package com.example.bankingapplication;

public class userInfoDisplay {
    private String firstName;
    private String lastName;
    private String dob;
    private String username;
    private String password;
    private String checking;
    private String savings;
    private String address;
    private String zipCode;
    private String cardNum;
    private String cardExp;
    private String cardCVV;
    private String email;
    private String number;
    private String id;


    public userInfoDisplay(String firstName, String lastName, String dob, String username, String password, String checking,
                           String savings, String address, String zipCode, String cardNum, String cardExp, String cardCVV,
                           String email, String number, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.username = username;
        this.password = password;
        this.checking = checking;
        this.savings = savings;
        this.address = address;
        this.zipCode = zipCode;
        this.cardNum = cardNum;
        this.cardExp = cardExp;
        this.cardCVV = cardCVV;
        this.email = email;
        this.number = number;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChecking() {
        return checking;
    }

    public void setChecking(String checking) {
        this.checking = checking;
    }

    public String getSavings() {
        return savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardExp() {
        return cardExp;
    }

    public void setCardExp(String cardExp) {
        this.cardExp = cardExp;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
