package com.smartapps.super_pos.Items;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DateItem extends Date implements Serializable {

    private static final ThreadLocal<DateFormat> DF = new ThreadLocal<DateFormat>() {
        @Override
        public DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.US);
        }
    };

    public DateItem(String date) {
        super();
        if (date != null && !date.isEmpty()) {
            try {
                if (Objects.requireNonNull(DF.get()).parse(date) != null) {
                    setTime(Objects.requireNonNull(Objects.requireNonNull(DF.get()).parse(date)).getTime());
                }

            } catch (ParseException ignored) {
            }
        }
    }

    public DateItem() {
        super();

    }

    @Override
    public String toString() {
        return Objects.requireNonNull(DF.get()).format(this);
    }
}
