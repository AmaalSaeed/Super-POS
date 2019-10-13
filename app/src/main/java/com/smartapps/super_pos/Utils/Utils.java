package com.smartapps.super_pos.Utils;

import android.content.Context;

public class Utils {
    public static String getErrorMesage( int error) {
        switch (error) {
            case 404:
                return "هذه الصفحة غير متوفرة يرجى تحديث التطبيق";
            case 500:
                return "حدث خطأ في السرفر";
            default:
                return "حدث خطأ ما..سيتم العمل على إصلاحه قريبا";

        }
    }
}
