package com.example.jens.testrigg;

public class AccessPoint {
    String rssi;
    String mac;
    private String json = "";

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

    public String getJson() {
        // "{\"macAddress\": \"00:25:9c:cf:1c:ad\",\"signalStrength\": -55}";
        json = "{\"macAddress\": \"" + mac + "\",\"signalStrength\": " + rssi + "}";
        return json;
    }
}
