package com.example.jens.testrigg;

import java.util.Calendar;
import java.util.Date;

public class Measurement {
    private Coordinate gps;
    private Coordinate user;
    private Coordinate wifiGoogle;
    private String gsmRssi;
    private String gsmCid;
    private String gsmLac;
    private AccessPoint[] accessPoints;
    private int nrOfAccessPoints;
    private Date measuredTime;

    private int rssiEnd = 0;
    private int macEnd = 0;

    public String getMeasuredTime() {
        return measuredTime.toString();
    }

    public Measurement() {
        measuredTime = Calendar.getInstance().getTime();
        gps = null;
        user = null;
        wifiGoogle = null;
        gsmRssi = "N/A";
        gsmCid = "N/A";
        gsmLac = "N/A";
        accessPoints = new AccessPoint[128];
        nrOfAccessPoints = 0;
    }

    public Coordinate getWifiGoogle() {
        return wifiGoogle;
    }

    public void setWifiGoogle(Coordinate wifiGoogle) {
        this.wifiGoogle = wifiGoogle;
    }

    public Coordinate getGps() {
        return gps;
    }

    public void setGps(Coordinate gps) {
        this.gps = gps;
    }

    public Coordinate getUser() {
        return user;
    }

    public void setUser(Coordinate user) {
        this.user = user;
    }


    public String getGsmRssi() {
        return gsmRssi;
    }

    public void setGsmRssi(String gsmRssi) {
        this.gsmRssi = gsmRssi;
    }

    public String getGsmCid() {
        return gsmCid;
    }

    public void setGsmCid(String gsmCid) {
        this.gsmCid = gsmCid;
    }

    public String getGsmLac() {
        return gsmLac;
    }

    public void setGsmLac(String gsmLac) {
        this.gsmLac = gsmLac;
    }

    public AccessPoint getAccessPoint(int pos) {
        return accessPoints[pos];
    }

    public void addAccessPoint(AccessPoint accessPoint) {
        this.accessPoints[nrOfAccessPoints] = accessPoint;
        nrOfAccessPoints++;
    }

    public int getNrOfAccessPoints() {
        return nrOfAccessPoints;
    }
}
