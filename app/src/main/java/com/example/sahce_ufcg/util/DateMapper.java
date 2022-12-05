package com.example.sahce_ufcg.util;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateMapper {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String fromDayOfWeekToString(DayOfWeek day){
        switch (day){
            case MONDAY:
                return "Segunda";
            case TUESDAY:
                return "Terça";
            case WEDNESDAY:
                return "Quarta";
            case THURSDAY:
                return "Quinta";
            case FRIDAY:
                return "Sexta";
            case SATURDAY:
                return "Sábado";
            case SUNDAY:
                return "Domingo";
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static DayOfWeek fromStringToDayOfWeek(String strDay){
        switch (strDay.toLowerCase()){
            case "segunda":
                return MONDAY;
            case "terça":
                return TUESDAY;
            case "quarta":
                return WEDNESDAY;
            case "quinta":
                return THURSDAY;
            case "sexta":
                return FRIDAY;
            case "sábado":
                return SATURDAY;
            case "domingo":
                return SUNDAY;
        }
        return null;
    }

    public static String fromLocalDateMonthToNumericMoth(String month){
        switch (month.toLowerCase()){
            case "jan":
                return "01";
            case "feb":
                return "02";
            case "mar":
                return "03";
            case "apr":
                return "04";
            case "may":
                return "05";
            case "jun":
                return "06";
            case "jul":
                return "07";
            case "aug":
                return "08";
            case "sep":
                return "09";
            case "oct":
                return "10";
            case "nov":
                return "11";
            case "dec":
                return "12";
        }
        return null;
    }

    public static String formatAmericanDateToBrazilianFormat(LocalDate inputDate){
        String date = inputDate.toString();
        String[] arrayDate = date.split("-");
        String month = arrayDate[1];
        String day = arrayDate[2];
        day = day.length() == 1 ? "0" + day : day;
        String year = arrayDate[0];
        return day + "/" + month + "/" + year;
    }
}
