package com.smartapps.super_pos.Items;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class PathDate {

    private static final ThreadLocal<DateFormat> DF = new ThreadLocal<DateFormat>() {
        @Override
        public DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.US);
        }
    };
    private Date date;

    public PathDate(Date date) {
        this.date = date;
    }

    public PathDate(String date) {
        if (date != null && !date.isEmpty()) {
            try {
                this.date = Objects.requireNonNull(DF.get()).parse(date);
            } catch (ParseException e) {
                this.date = null;
            }
        } else {
            this.date = null;
        }
    }

    public PathDate() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        if (date != null) {
            return DF.get().format(date);
        } else {
            return "";
        }
    }
}
