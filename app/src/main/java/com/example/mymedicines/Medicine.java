package com.example.mymedicines;

import java.io.Serializable;

public class Medicine {
    private int led_id;
    private String medicine_name;
    private String exp_date;
    private int piece;
    private boolean state;
    private String days;
    private boolean date_state;

    public Medicine(int led_id, String medicine_name, String exp_date, int piece, boolean state, String days) {
        this.led_id = led_id;
        this.medicine_name = medicine_name;
        this.exp_date = exp_date;
        this.piece = piece;
        this.state = state;
        this.days = days;
        date_state = true;
    }

    public int getLed_id() {
        return led_id;
    }

    public void setLed_id(int led_id) {
        this.led_id = led_id;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public boolean isDate_state() {
        return date_state;
    }

    public void setDate_state(boolean date_state) {
        this.date_state = date_state;
    }
}
