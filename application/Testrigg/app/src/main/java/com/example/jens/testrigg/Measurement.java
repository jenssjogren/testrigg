package com.example.jens.testrigg;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.Calendar;
import java.util.Date;

public class Measurement {
    private Coordinate gps;
    private Coordinate user;
    private Coordinate wifiGoogle;
    private Coordinate wifiOpencellid;
    private String googleDistance;
    private String opencellidDistance;
    private String gsmRssi;
    private String gsmCid;
    private String gsmLac;
    private AccessPoint[] accessPoints;
    private int nrOfAccessPoints;
    private Date measuredTime;
    private String sigfoxLinkQuality;

    public String getMeasuredTime() {
        return measuredTime.toString();
    }



    public Measurement() {
        measuredTime = Calendar.getInstance().getTime();
        gps = null;
        user = null;
        wifiGoogle = null;
        wifiOpencellid = null;
        gsmRssi = "N/A";
        gsmCid = "N/A";
        gsmLac = "N/A";
        sigfoxLinkQuality = "N/A";
        accessPoints = new AccessPoint[128];
        nrOfAccessPoints = 0;
    }

    public String getSigfoxLinkQuality() {
        return sigfoxLinkQuality;
    }

    public void setSigfoxLinkQuality(String sigfoxLinkQuality) {
        this.sigfoxLinkQuality = sigfoxLinkQuality;
    }

    public Coordinate getWifiGoogle() {
        return wifiGoogle;
    }

    public void setWifiGoogle(Coordinate wifiGoogle) {
        this.wifiGoogle = wifiGoogle;
    }

    public Coordinate getWifiOpencellid() {
        return wifiOpencellid;
    }

    public void setWifiOpencellid(Coordinate wifiOpencellid) {
        this.wifiOpencellid = wifiOpencellid;
    }

    public String getGpsDistance() {
        if (user != null && gps != null) {
            return computeDistance(user, gps);
        }
        else {
            return "N/A";
        }
    }

    public String getGoogleDistance() {
        if (user != null && wifiGoogle != null) {
            return computeDistance(user, wifiGoogle);
        }
        else {
            return "N/A";
        }
    }

    public String getOpencellidDistance() {
        if (user != null && wifiOpencellid != null) {
            return computeDistance(user, wifiOpencellid);
        }
        else {
            return "N/A";
        }
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

    public AccessPoint[] getAccessPoints() {
        AccessPoint[] aps = new AccessPoint[nrOfAccessPoints];
        for (int i = 0; i < nrOfAccessPoints; i++) {
            aps[i] = accessPoints[i];
        }
        return aps;
    }

    public int getNrOfAccessPoints() {
        return nrOfAccessPoints;
    }

    private LatLng coordinateToLatLng(Coordinate coordinate) {
        double lat = Double.valueOf(coordinate.lat);
        double lng = Double.valueOf(coordinate.lng);
        LatLng latLng = new LatLng(lat, lng);
        return latLng;
    }

    private String computeDistance(Coordinate c1, Coordinate c2) {
        LatLng latLng1 = coordinateToLatLng(c1);
        LatLng latLng2 = coordinateToLatLng(c2);
        double dist = SphericalUtil.computeDistanceBetween(latLng1, latLng2);
        return Double.toString(dist);
    }
}
