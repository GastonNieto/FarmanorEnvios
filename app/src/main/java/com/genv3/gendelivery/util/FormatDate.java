package com.genv3.gendelivery.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {
    public static String formateador(String fecha){
        String fechastring = fecha;
        String fechaFormato = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date a = sdf.parse(fechastring);
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            fechaFormato = fmtOut.format(a);

        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        return fechaFormato;


    }
    public static String formateadorDate(String fecha){
        String fechastring = fecha;
        String fechaFormato = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date a = sdf.parse(fechastring);
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy ");
            fechaFormato = fmtOut.format(a);

        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        return fechaFormato;


    }
}


