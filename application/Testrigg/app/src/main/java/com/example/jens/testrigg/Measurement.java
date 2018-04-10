package com.example.jens.testrigg;

public class Measurement {
    private String gpsLat;
    private String gpsLong;
    private String userLat;
    private String userLong;
    private String gsmRssi;
    private String gsmCid;
    private String gsmLac;
    private String[] wifiRssi;
    private String[] wifiMac;
    private int wifiNrOfAccessPoints;

    private int rssiEnd = 0;
    private int macEnd = 0;

    public Measurement() {
        gpsLat = "N/A";
        gpsLong = "N/A";
        userLat = "N/A";
        userLong = "N/A";
        gsmRssi = "N/A";
        gsmCid = "N/A";
        gsmLac = "N/A";
        wifiRssi = new String[128];
        wifiMac = new String[128];
        wifiNrOfAccessPoints = 0;
    }

    public String getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(String gpsLat) {
        this.gpsLat = gpsLat;
    }

    public String getGpsLong() {
        return gpsLong;
    }

    public void setGpsLong(String gpsLong) {
        this.gpsLong = gpsLong;
    }

    public String getUserLat() {
        return userLat;
    }

    public void setUserLat(String userLat) {
        this.userLat = userLat;
    }

    public String getUserLong() {
        return userLong;
    }

    public void setUserLong(String userLong) {
        this.userLong = userLong;
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

    public String getWifiRssi(int pos) {
        return wifiRssi[pos];
    }

    public void addWifiRssi(String wifiRssi) {
        this.wifiRssi[rssiEnd] = wifiRssi;
        rssiEnd++;
    }

    public String getWifiMac(int pos) {
        return wifiMac[pos];
    }

    public void addWifiMac(String wifiMac) {
        this.wifiMac[macEnd] = wifiMac;
        macEnd++;
    }

    public int getWifiNrOfAccessPoints() {
        return macEnd;
    }

    public void setWifiNrOfAccessPoints(int wifiNrOfAccessPoints) {
        this.wifiNrOfAccessPoints = wifiNrOfAccessPoints;
    }
}
