/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usba.pss.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Anwar
 */
public class Utils {
    public static SimpleDateFormat sqlDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static String LOG_API = "api";

    public static Date convertStringToDate(String date, String format) {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            dt = sdf.parse(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return dt;
    }

    public static boolean isNullOrEmpty(String value) {
        if (value == null || value.trim().equals("")) return true;
        else return false;
    }

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    public static boolean isOk(Integer value) {
        return !(value == null || value <= 0);
    }

    public static boolean isDoublePositiveOk(Double value) {
        return !(value == null || value <= 0);
    }

    public static boolean isOk(Long value) {
        return !(value == null || value <= 0);
    }

    public static boolean isOk(String str) {
        return !(str == null || str.trim().isEmpty());
    }

    public static boolean isOk(Enum value) {
        return !(value == null);
    }

    public static Integer getIntegerFromObject(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Integer) {
            return ((Integer) object).intValue();
        } else if (object instanceof Short) {
            return ((Short) object).intValue();
        } else if (object instanceof Long) {
            return ((Long) object).intValue();
        } else if (object instanceof BigDecimal) {
            return ((BigDecimal) object).intValue();
        } else if (object instanceof BigInteger) {
            return ((BigInteger) object).intValue();
        } else if (object instanceof Byte) {
            return new Integer((Byte) object);
        } else if (object instanceof Double) {
            return ((Double) object).intValue();
        } else if (object instanceof String) {
            try {
                return Integer.parseInt((String) object);
            } catch (Throwable t) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static String getDateToString(Date date, String dateFormat) {
        if (date == null) {
            return null;
        }

        return (new SimpleDateFormat(dateFormat)).format(date);
    }

    public static Date addDays(Date date, int day) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }
}
