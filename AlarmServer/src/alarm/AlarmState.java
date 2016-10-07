/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarm;

import java.util.Date;


/**
 *
 * @author alex
 */
public class AlarmState {
    
    private String imei;
    private Date date;
    private String fix;
    private Float latitude;
    private Float longitude;
    private String speed;       //Km/h
    private String direction;   //Deg
    private String altitude;    //m
    private String battery;     //mV
    
    private String geoAlarmStatus;
    private String alarmStatus;
    private String systemStatus;

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the latitude
     */
    public Float getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public Float getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the speed
     */
    public String getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(String speed) {
        this.speed = speed;
    }

    /**
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * @return the altitude
     */
    public String getAltitude() {
        return altitude;
    }

    /**
     * @param altitude the altitude to set
     */
    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    /**
     * @return the battery
     */
    public String getBattery() {
        return battery;
    }

    /**
     * @param battery the battery to set
     */
    public void setBattery(String battery) {
        this.battery = battery;
    }

    /**
     * @return the geoAlarmStatus
     */
    public String getGeoAlarmStatus() {
        return geoAlarmStatus;
    }

    /**
     * @param geoAlarmStatus the geoAlarmStatus to set
     */
    public void setGeoAlarmStatus(String geoAlarmStatus) {
        this.geoAlarmStatus = geoAlarmStatus;
    }

    /**
     * @return the alarmStatus
     */
    public String getAlarmStatus() {
        return alarmStatus;
    }

    /**
     * @param alarmStatus the alarmStatus to set
     */
    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    /**
     * @return the systemStatus
     */
    public String getSystemStatus() {
        return systemStatus;
    }

    /**
     * @param systemStatus the systemStatus to set
     */
    public void setSystemStatus(String systemStatus) {
        this.systemStatus = systemStatus;
    }

    /**
     * @return the positionFix
     */
    public String getFix() {
        return fix;
    }

    /**
     * @param positionFix the positionFix to set
     */
    public void setFix(String fix) {
        this.fix = fix;
    }

    /**
     * @return the imei
     */
    public String getImei() {
        return imei;
    }

    /**
     * @param imei the imei to set
     */
    public void setImei(String imei) {
        this.imei = imei;
    }
    
}
