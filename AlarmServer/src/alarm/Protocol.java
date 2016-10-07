/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarm;

import util.Converter;

/**
 *
 * @author alex
 */
public class Protocol {
    
    public static final int QUERY_PARAMS = 1;
    public static final int QUERY_STATE_GPS = 2;
    public static final int QUERY_STATE_CELL = 3;
    public static final int ARM = 4;
    public static final int DISARM = 5;
    public static final int ENABLE = 6;
    public static final int DISABLE = 7;
    public static final int LOGIN_REPLY = 8;
    public static final int HANDS_CHECK_REPLY = 9;
    public static final int ALARM_REPLY = 10;
    
    public static AlarmState decodeMessage(String message){
        
        AlarmState state = new AlarmState();
        
        String[] mess = message.split("\\$");
        
        if(mess.length == 4){
            message = mess[2];
        }
        else{
            System.out.println("Invalid: "+ message);
            return null;
        }
        
        String[] params = message.split("#");
        
        if(params.length >= 13){
            String code = params[0];
            state.setDate(Converter.dateTime(params[1], params[2]));
            state.setFix((params[3].equals("1") ? "Valid" : "Invalid"));
            state.setLatitude(Float.valueOf(params[4]));
            state.setLongitude(Float.valueOf(params[5]));
            state.setSpeed(params[6] +" Km/h");
            state.setDirection(params[7] +"º");
            state.setAltitude(params[8] +" m");
            state.setBattery(params[9] +" mV");

            state.setGeoAlarmStatus(params[10].equals("0") ? "No" : (params[10].equals("1") ? "Leave" : "Enter"));
            switch(params[11]){
                case "1":
                    state.setAlarmStatus("MOVING");
                    break;
                case "2":
                    state.setAlarmStatus("SHOCK");
                    break;
                case "3":
                    state.setAlarmStatus("Main Power Disconnected !!");
                    break;
                case "4":
                    state.setAlarmStatus("SPEED OVER");
                    break;
                case "5":
                    state.setAlarmStatus("PANIC");
                    break;
                case "6":
                    state.setAlarmStatus("DOOR OPEN");
                    break;
                case "7":
                    state.setAlarmStatus("IGNITION ON");
                    break;
                case "8":
                    state.setAlarmStatus("Ignition ON");
                    break;
                case "9":
                    state.setAlarmStatus("Ignition OFF");
                    break;
                case "A":
                    state.setAlarmStatus("Low Battery");
                    break;
                case "B":
                    state.setAlarmStatus("CRASH");
                    break;
            }
            state.setSystemStatus((params[12].charAt(0) == '0' ? "Disarm" : "Arm") +" / "+
                    //(params[12].charAt(1) == '0' ? "Close" : "Open") +" / "+
                    (params[12].charAt(1) == '0' ? "Off" : "On"));
        }
        
        return state;
        
    }

    public static String getMessage(String imei, int code){
        StringBuilder message = new StringBuilder();
        
        switch(code){
            case QUERY_PARAMS:
                message.append("PA$8$10$2$AP");
                break;
            case QUERY_STATE_GPS:
                message.append("PA$1$01$G$AP");
                break;
            case QUERY_STATE_CELL:
                message.append("PA$1$01$L$AP");
                break;
            case ARM:
                message.append("PA$17$24$AP");
                break;
            case DISARM:
                message.append("PA$17$25$AP");
                break;
            case ENABLE:

                break;
            case DISABLE:

                break;
            case LOGIN_REPLY:

                break;
            case HANDS_CHECK_REPLY:

                break;
            case ALARM_REPLY:

                break;
        }
        
        return message.toString();
    }
    
    
}
