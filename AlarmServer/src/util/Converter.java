/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Administrador
 */
public class Converter {
    static boolean debug = true;

    public static String[] month = {"Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"};
    public static String[] month_l = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};

    public static Date dateTime(String date, String time){
        // Separar Fecha
        Date result = null;
        try{
            String[] s = new String[3];
            s[0] = date.substring(0, 2);
            s[1] = date.substring(2,4);
            s[2] = date.substring(4);
            boolean flag = true;
            // Asignar año-mes-dia
            int y = Integer.valueOf(s[0]) + 2000;
            int m = Integer.valueOf(s[1]);
            int d = Integer.valueOf(s[2]);
            String[] tt = new String[3];

            tt[0] = time.substring(0,2);
            tt[1] = time.substring(2,4);
            tt[2] = time.substring(4);
            
            int hour = Integer.valueOf(tt[0]);
            int min = Integer.valueOf(tt[1]);
            int sec = Integer.valueOf(tt[2]);
            // Rangos para cada Valor
            if(d < 1 || d > 31 || m < 1 || m > 12 || y < 1800)
                return null;
            // Año Bisiesto Febrero - 29
            if(y % 4 == 0){
                if(m == 2)
                    if(d > 29)
                        flag = false;
            }
            // Año no Bisiesto Febrero - 28
            if(y % 4 != 0){
                if(m==2)
                    if(d > 28)
                        flag = false;
            }
            // Meses de 30 dias
            if(d > 30 && (m == 4 || m == 6 || m == 9 || m == 11))
                flag = false;
            if(flag){
                GregorianCalendar gc = new GregorianCalendar();
                gc.set(y, m - 1, d, hour, min, sec);

                result = gc.getTime();
            }
        }catch(Exception e){
            if(debug)
                e.printStackTrace();
            result = null;
        }
        return result;
    }
    
    public static String dateTimeFormat(Date dt){
        String result = month[dt.getMonth()] + "/"+ (dt.getDate() < 10 ? "0" : "") + dt.getDate() +"/"+(1900+dt.getYear())
                 + " " + (dt.getHours() < 10 ? "0" : "") + dt.getHours() +":"+ (dt.getMinutes() < 10 ? "0" : "") + dt.getMinutes() + ":"
                 + (dt.getSeconds() < 10 ? "0" : "") + dt.getSeconds();

        return result;
    }

    public static Integer integer(String in){
        Integer result;
        try{
            if(in.contains("."))
                in = in.substring(0, in.indexOf("."));
            if(in.contains(","))
                in = in.substring(0, in.indexOf(","));
            
            result = Integer.valueOf(in.trim());
        }catch(Exception e){
            result = null;
        }
        return result;
    }
}
