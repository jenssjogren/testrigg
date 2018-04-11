package com.example.jens.testrigg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.jens.testrigg.MESSAGE";
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_DEVICE_ADDRESS = 2;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private ConnectBluetooth connectBluetooth;

    Button pairedDevicesButton, sendButton;
    TextView measuredTime, gpsCoordinates, userCoordinates, wifiCoordinatesGoogle, nrOfAps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pairedDevicesButton = (Button) findViewById(R.id.button_paired_devices);
        sendButton = (Button) findViewById(R.id.button_send);

        measuredTime = (TextView) findViewById(R.id.textView_time_measured_data);
        gpsCoordinates = (TextView) findViewById(R.id.textView_gps_coordinates_data);
        userCoordinates = (TextView) findViewById(R.id.textView_user_coordinates_data);
        wifiCoordinatesGoogle = (TextView) findViewById(R.id.textView_wifi_coordinates_google_data);
        nrOfAps = (TextView) findViewById(R.id.textView_nr_reached_aps_data);


        if (mBluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }

        if (!mBluetoothAdapter.isEnabled()) {

            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }


        pairedDevicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList list = listPairedDevices();
                if (list != null) {
                    Intent deviceIntent = new Intent(MainActivity.this, DeviceListActivity.class);
                    deviceIntent.putExtra(EXTRA_MESSAGE, list);
                    startActivityForResult(deviceIntent, REQUEST_DEVICE_ADDRESS);
                } else {
                    Toast.makeText(MainActivity.this, "No paired devices", Toast.LENGTH_LONG).show();
                }
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectBluetooth.write("GET_ALL\r\n".getBytes());
                connectBluetooth.read();
            }
        });


    }

    private ArrayList listPairedDevices() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                list.add(deviceName + "\n" + deviceHardwareAddress);
            }
            return list;
        }
        return null;
    }

    private Measurement parseMeasureValues(String message) {
        Measurement measurement = new Measurement();

        //GPS
        if (message.contains("GPS_START\r\n")) {
            int indexGps = message.indexOf("GPS_START\r\n");
            int length = "GPS_START\r\n".length();
            int endGps = message.indexOf("GPS_END\r\n");

            String gpsNmea = message.substring(indexGps + length, endGps);
            Log.d(TAG, "NMEA captured length: " + gpsNmea.length());

            if(gpsNmea.length() > 36) {
                if (gpsNmea.charAt(35) == 'E' || gpsNmea.charAt(35) == 'W') {
                    // We have connection. Get lat/long

                    gpsNmea = gpsNmea.substring(10, 10+26); //Skip 10 time info chars, coordinates 26 chars
                    Log.d(TAG, "NMEA coordinates: " + gpsNmea);
                    if (gpsNmea.length() != 26) {
                    } else {
                        //dd + mm.mmmmm/60 for latitude
                        int latdd = Integer.parseInt(gpsNmea.substring(0, 2));
                        double latmm = Double.parseDouble(gpsNmea.substring(2, 10));
                        latmm = latmm / 60;

                        double lat = latdd + latmm;

                        //ddd + mm.mmmmm/60 for longitude
                        int longdd = Integer.parseInt(gpsNmea.substring(13, 16));
                        double longmm = Double.parseDouble(gpsNmea.substring(16, 24));
                        longmm = longmm / 60;

                        double lng = longdd + longmm;

                        Coordinate gps = new Coordinate("" + lat, "" + lng);
                        measurement.setGps(gps);
                        Log.d(TAG, "Formatted coordinates: " + gps.lat + " " + gps.lng);
                    }
                }
            }


        }

        //GSM
        if(message.contains("GSM_START\r\n")) {
            //RSSI
            int indexCsq = message.indexOf("+CSQ: ");
            int length = "+CSQ: ".length();
            String rssi = message.substring(indexCsq+length, indexCsq+length+2);

            rssi = rssi.replace(',', ' ');
            rssi = rssi.trim();

            //Convert value to dBm
            int rssiInt = Integer.parseInt(rssi);
            int dbm = -113 + 2*rssiInt;
            rssi = "" + dbm;
            Log.d(TAG, "GSM RSSI in dBm: " + rssi);

            //LAC and CID
            int indexLac = message.indexOf("+CREG: ");
            indexLac = indexLac + "+CREG: ".length() + 5;
            String lac = message.substring(indexLac, indexLac+4);

            Log.d(TAG, "GSM LAC: " + lac);

            int indexCid = indexLac+7;
            String cid = message.substring(indexCid, indexCid+4);
            Log.d(TAG, "GSM CID: " + cid);

            measurement.setGsmCid(cid);
            measurement.setGsmLac(lac);
            measurement.setGsmRssi(rssi);
        }

        //WIFI
        if(message.contains("WIFI_START\r\n")) {
            if(message.contains("+CWLAP:")) {
                //We found accesspoints
                int index = message.indexOf("WIFI_START\r\n");
                int end = message.indexOf("WIFI_END\r\n");
                int length = "WIFI_START\r\n".length();
                String rawData = message.substring(index+length, end);
                rawData = rawData.replace("+CWLAP:", "");
                rawData = rawData.replace("OK", "");
                rawData = rawData.trim();

                index = rawData.indexOf('(');
                end = rawData.lastIndexOf(')');

                rawData = rawData.substring(index, end);

                String[] aps = rawData.split("\r\n");

                Log.d(TAG, "Nr of APs " + aps.length);

                int commaPos;
                String rssi, mac;
                for (String ap:aps) {
                    commaPos = ap.indexOf(',');
                    rssi = ap.substring(1, commaPos);
                    mac = ap.substring(commaPos+2, commaPos+19 );
                    AccessPoint accessPoint = new AccessPoint(rssi, mac);
                    measurement.addAccessPoint(accessPoint);
                }
                Log.d(TAG, "First AP rssi: " + measurement.getAccessPoint(0).rssi);
                Log.d(TAG, "First AP mac: " + measurement.getAccessPoint(0).mac);
                Log.d(TAG, "Last AP rssi: " + measurement.getAccessPoint(aps.length-1).rssi);
                Log.d(TAG, "Last AP mac: " + measurement.getAccessPoint(aps.length-1).mac);
            }
        }
        return measurement;
    }

    private void updateLastMeasurementInfo(Measurement measurement) {
        Coordinate gps = measurement.getGps();
        Coordinate user = measurement.getUser();
        Coordinate wifiGoogle = measurement.getWifiGoogle();

        if (gps != null) {
            gpsCoordinates.setText(gps.lat + " " + gps.lng);
        }
        else {
            gpsCoordinates.setText("N/A");
        }
        if (user != null) {
            userCoordinates.setText(user.lat + " " + user.lng);
        }
        else {
            userCoordinates.setText("N/A");
        }
        if (wifiGoogle != null) {
            wifiCoordinatesGoogle.setText(wifiGoogle.lat + " " + wifiGoogle.lng);
        }
        else {
            wifiCoordinatesGoogle.setText("N/A");
        }

        measuredTime.setText(measurement.getMeasuredTime());
        nrOfAps.setText("" + measurement.getNrOfAccessPoints());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        connectBluetooth.cancel();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session

                } else {
                    // User did not enable Bluetooth or an error occurred
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_LONG).show();
                    this.finish();
                }
                break;
            case REQUEST_DEVICE_ADDRESS:
                if (resultCode == Activity.RESULT_OK) {
                    String macAddress = data.getData().toString();

                    //Connect to selected device
                    connectBluetooth = new ConnectBluetooth();
                    connectBluetooth.execute(mBluetoothAdapter.getRemoteDevice(macAddress));

                } else {
                    Toast.makeText(this, "Något gick fel", Toast.LENGTH_LONG).show();
                }
                break;

        }
    }

    private class ConnectBluetooth extends AsyncTask<BluetoothDevice, Void, BluetoothSocket> {
        private BluetoothSocket mmSocket;
        private BluetoothDevice mmDevice;
        private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        private ProgressDialog progress;

        private OutputStream mmOutStream;
        private byte[] mmBuffer; // mmBuffer store for the stream
        private boolean connected = true;


        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }

        @Override
        protected BluetoothSocket doInBackground(BluetoothDevice... devices) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = devices[0];

            OutputStream tmpOut = null;


            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = devices[0].createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "Socket's create() method failed", e);
                connected = false;
            }
            mmSocket = tmp;

            // Cancel discovery because it otherwise slows down the connection.
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                Log.e(TAG, "Unable to connect, closing the client socket", connectException);
                connected = false;
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            //manageMyConnectedSocket(mmSocket);

            // Get the input and output streams; using temp objects because
            // member streams are final.

            try {
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating output stream", e);
            }

            mmOutStream = tmpOut;

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(MainActivity.this, "Ansluter...", "Chilla lite!");  //show a progress dialog

        }

        @Override
        protected void onPostExecute(BluetoothSocket bluetoothSocket) {
            super.onPostExecute(bluetoothSocket);
            progress.cancel();
            if (connected) {
                Toast.makeText(MainActivity.this, "Ansluten", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Kunde inte ansluta", Toast.LENGTH_SHORT).show();
            }
        }

        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);
            }
        }

        public void read() {
            IncomingMessage incomingMessage;
            incomingMessage = new IncomingMessage();
            incomingMessage.execute(mmSocket);
        }

        private class IncomingMessage extends AsyncTask<BluetoothSocket, Void, String> {
            InputStream tmpIn = null;
            private InputStream mmInStream;
            private byte[] mmBuffer; // mmBuffer store for the stream
            private String message;

            @Override
            protected String doInBackground(BluetoothSocket... bluetoothSockets) {
                try {
                    tmpIn = bluetoothSockets[0].getInputStream();
                } catch (IOException e) {
                    Log.e(TAG, "Error occurred when creating input stream", e);
                    return null;
                }

                mmInStream = tmpIn;
                String s;
                message = "";
                while (!message.endsWith("MESSAGE_END\r\n")) {
                    try {
                        mmBuffer = new byte[1024];
                        int length = mmInStream.read(mmBuffer);
                        s = new String(mmBuffer, 0, length);
                        message = message + s;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(TAG, "Message: " + message);
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MainActivity.this, "Measurement data received!", Toast.LENGTH_SHORT).show();
                updateLastMeasurementInfo(parseMeasureValues(message));
            }
        }
    }
}

