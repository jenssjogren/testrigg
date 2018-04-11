package com.example.jens.testrigg;

public class AccessPoint {
    String rssi;
    String mac;
    public AccessPoint(String rssi, String mac) {
        this.rssi = rssi;
        this.mac = mac;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
