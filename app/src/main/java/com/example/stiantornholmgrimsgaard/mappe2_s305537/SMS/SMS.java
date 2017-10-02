package com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS;

import java.util.Date;

/**
 * Created by stiantornholmgrimsgaard on 02.10.2017.
 */

public class SMS {

    private Long _id;
    private Long date;
    private String message;

    public SMS() {

    }

    public SMS(Long date, String message) {
        this.date = date;
        this.message = message;
    }

    public SMS(Long _id, Long date, String message) {
        this._id = _id;
        this.date = date;
        this.message = message;
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
}