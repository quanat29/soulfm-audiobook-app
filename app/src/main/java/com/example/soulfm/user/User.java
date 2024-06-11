package com.example.soulfm.user;

public class User {
    private String Tendangnhap;
    private String Sdt;

    public User(String tendangnhap, String sdt) {
        Tendangnhap = tendangnhap;
        Sdt = sdt;
    }

    public String getTendangnhap() {
        return Tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        Tendangnhap = tendangnhap;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }
}
