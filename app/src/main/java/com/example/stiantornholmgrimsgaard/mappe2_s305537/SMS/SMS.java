package com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS;

/**
 * Created by stiantornholmgrimsgaard on 02.10.2017.
 */

public class SMS {

    private Long _id;
    private Long date;
    private String message;
    private boolean isSent;
    private boolean isWeekly;

    public SMS() {

    }

    public SMS(Long date, String message, boolean isSent, boolean isWeekly) {
        this.date = date;
        this.message = message;
        this.isSent = isSent;
        this.isWeekly = isWeekly;
    }

    public SMS(Long _id, Long date, String message, boolean isSent, boolean isWeekly) {
        this._id = _id;
        this.date = date;
        this.message = message;
        this.isSent = isSent;
        this.isWeekly = isWeekly;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        this.isSent = sent;
    }

    public boolean isWeekly() {
        return isWeekly;
    }

    public void setWeekly(boolean isWeekly) {
        this.isWeekly = isWeekly;
    }
}
