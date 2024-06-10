package com.example.soulfm.payment;

public class Payment {

    private String timePayment;
    private String description;
    private String money;

    public Payment(String timePayment, String description, String money) {
        this.timePayment = timePayment;
        this.description = description;
        this.money = money;
    }

    public String getTimePayment() {
        return timePayment;
    }

    public void setTimePayment(String timePayment) {
        this.timePayment = timePayment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
