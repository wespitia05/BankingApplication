package com.example.bankingapplication;

public class Card {

    private String cardnumber;
    private String expirDate;
    private String cvv;

    public Card(String cardnumber, String expirDate, String cvv) {
        this.cardnumber = cardnumber;
        this.expirDate = expirDate;
        this.cvv = cvv;
    }

    public void addToDB() {

    }

    public String getCardnumber() { return cardnumber; }
    public String getExpirDate() { return expirDate; }
    public String getCvv() { return cvv; }

    public void setCardnumber(String cardnumber) { this.cardnumber = cardnumber; }
    public void setExpirDate(String expirDate) { this.expirDate = expirDate; }
    public void setCvv(String cvv) { this.cvv = cvv; }
}

