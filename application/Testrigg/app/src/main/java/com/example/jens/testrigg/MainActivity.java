package com.example.jens.testrigg;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.jens.testrigg.MESSAGE";
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_DEVICE_ADDRESS = 2;
    private static final int REQUEST_USER_INPUT_LOCATION = 3;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 4;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private ConnectBluetooth connectBluetooth;
    private boolean isWaitingMessage = false;
    private String currentMeasurementSession = "";
    private boolean inMeasurementSession = false;
    private boolean postHttpConnectionDone = false;
    private static final int COLOR_GREEN = Color.rgb(111, 198, 85);
    private static final int COLOR_RED = Color.rgb(209, 54, 54);
    private Coordinate userCoordinate = null;
    private int measureCounter = 0;
    private int lastSigfoxSequenceNumber = -1;
    private Measurement lastMeasurement = null;

    Button pairedDevicesButton, makeMeasurementButton, newSessionButton, getButton, viewMeasurementOnmapButton;
    TextView measuredTime, gpsCoordinates, userCoordinates, wifiCoordinatesGoogle, nrOfAps, gsmRssi,
            gsmLac, gsmCid, sigfoxLink, wifiCoordinatesOpencellid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pairedDevicesButton = (Button) findViewById(R.id.button_paired_devices);
        makeMeasurementButton = (Button) findViewById(R.id.button_send);
        newSessionButton = (Button) findViewById(R.id.button_new_measurement);
        newSessionButton.setTextColor(COLOR_GREEN);
        viewMeasurementOnmapButton = (Button) findViewById(R.id.button_view_measurement_on_map);

        measuredTime = (TextView) findViewById(R.id.textView_time_measured_data);
        gpsCoordinates = (TextView) findViewById(R.id.textView_gps_coordinates_data);
        userCoordinates = (TextView) findViewById(R.id.textView_user_coordinates_data);
        wifiCoordinatesGoogle = (TextView) findViewById(R.id.textView_wifi_coordinates_google_data);
        wifiCoordinatesOpencellid = (TextView) findViewById(R.id.textView_wifi_coordinates_opencellid_data);
        nrOfAps = (TextView) findViewById(R.id.textView_nr_reached_aps_data);
        gsmRssi = (TextView) findViewById(R.id.textView_gsm_rssi_data);
        gsmLac = (TextView) findViewById(R.id.textView_gsm_lac_data);
        gsmCid = (TextView) findViewById(R.id.textView_gsm_cid_data);
        sigfoxLink = (TextView) findViewById(R.id.textView_sigfox_link_quality_data);

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

        makeMeasurementButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (lastSigfoxSequenceNumber == -1) {
                    //Get the last sequence number of sigfox messages
                    GetSigfoxStatus getSigfoxStatus = new GetSigfoxStatus();
                    getSigfoxStatus.execute("https://backend.sigfox.com/api/devices/38D054/messages?limit=1");
                }

                // Use the Builder class for convenient dialog construction
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Ange koordinater från?")
                        .setPositiveButton("GPS", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent mapsIntent = new Intent(MainActivity.this, MapsActivity.class);
                                startActivityForResult(mapsIntent, REQUEST_USER_INPUT_LOCATION);
                            }
                        })
                        .setNegativeButton("Textinmatning", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                builder.setMessage("Mata in koordinater");
                                LinearLayout lila1 = new LinearLayout(builder.getContext());
                                lila1.setOrientation(LinearLayout.VERTICAL);
                                final EditText lat = new EditText(MainActivity.this);
                                lat.setHint("Latitude");
                                lat.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                                lila1.addView(lat);
                                final EditText lng = new EditText(MainActivity.this);
                                lng.setHint("Longitude");
                                lng.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                                lila1.addView(lng);

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        userCoordinate = new Coordinate(lat.getText().toString(), lng.getText().toString());
                                        startMeasurementSession();
                                    }
                                });

                                builder.setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                                builder.setView(lila1);
                                builder.show();
                            }
                        });
                builder.show();
            }
        });

        newSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inMeasurementSession) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Namnge loggfil för aktuell mätning");

                    // Set up the input
                    final EditText input = new EditText(MainActivity.this);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            currentMeasurementSession = input.getText().toString() + ".csv";
                            inMeasurementSession = true;
                            newSessionButton.setText("Cancel session");
                            newSessionButton.setTextColor(COLOR_RED);

                            String heading = "Time;User defined coordinates;" +
                                    "GPS coordinates;WiFi coordinates (Google);" +
                                    "Opencellid coordinates;GPS distance;Google distance;" +
                                    " Opencellid distance;Google accuracy;Opencellid accuracy;" +
                                    "Access points reached;GSM RSSI;GSM LAC;" +
                                    "GSM CID;Sigfox link quality\r\n";

                            File dir = getApplicationDir("Testrigg");
                            File file = createFile(dir, currentMeasurementSession);
                            if (file.length() == 0) {
                                //Write only heading if file is empty
                                writeToFile(file, heading);
                            }

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Are you sure to cancel measurements to " + currentMeasurementSession + "?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // End session
                                    inMeasurementSession = false;
                                    newSessionButton.setText("New session");
                                    newSessionButton.setTextColor(COLOR_GREEN);
                                    currentMeasurementSession = "";
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            }
        });


        viewMeasurementOnmapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapsIntent = new Intent(MainActivity.this, ViewMeasurementOnMapActivity.class);
                mapsIntent.putExtra("gps", lastMeasurement.getGps());
                mapsIntent.putExtra("user", lastMeasurement.getUser());
                mapsIntent.putExtra("google", lastMeasurement.getWifiGoogle());
                mapsIntent.putExtra("opencellid", lastMeasurement.getWifiOpencellid());
                startActivity(mapsIntent);
            }
        });
    }

    private String generateSigfoxMessage(String msg) {
        String str = "SEND_SIGFOX:";
        str = str + String.format("%012x", new BigInteger(1, msg.getBytes(/*YOUR_CHARSET?*/)));
        str = str + "\r\n";
        Log.d(TAG, "Sigfox message: " + str);
        return str;
    }

    private void startMeasurementSession() {
        if (!isWaitingMessage && connectBluetooth != null) {
            //Get other peripherals info
            connectBluetooth.write("GET_ALL_SEND_SIGFOX\r\n".getBytes());
            connectBluetooth.read();
        } else {
            Toast.makeText(this, "Kunde inte starta mätning", Toast.LENGTH_LONG).show();
        }
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

            if (gpsNmea.length() > 36) {
                if (gpsNmea.charAt(35) == 'E' || gpsNmea.charAt(35) == 'W') {
                    // We have connection. Get lat/long

                    gpsNmea = gpsNmea.substring(10, 10 + 26); //Skip 10 time info chars, coordinates 26 chars
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
        if (message.contains("GSM_START\r\n")) {
            //RSSI
            int indexCsq = message.indexOf("+CSQ: ");
            int length = "+CSQ: ".length();
            String rssi = message.substring(indexCsq + length, indexCsq + length + 2);

            rssi = rssi.replace(',', ' ');
            rssi = rssi.trim();

            //Convert value to dBm
            if (rssi.equals("99")) {
                //Not known or not detectable signal
                rssi = "N/A";
            } else {
                try {
                    //First messages after hardware restart will throw exception...
                    int rssiInt = Integer.parseInt(rssi);
                    int dbm = -113 + 2 * rssiInt;
                    rssi = "" + dbm;
                    Log.d(TAG, "GSM RSSI in dBm: " + rssi);
                }catch (NumberFormatException e) {
                    rssi = "N/A";
                }

            }

            //LAC and CID
            int indexLac = message.indexOf("+CREG: ");
            indexLac = indexLac + "+CREG: ".length() + 5;
            String lac = message.substring(indexLac, indexLac + 4);

            Log.d(TAG, "GSM LAC: " + lac);

            int indexCid = indexLac + 7;
            String cid = message.substring(indexCid, indexCid + 4);
            Log.d(TAG, "GSM CID: " + cid);

            measurement.setGsmCid(cid);
            measurement.setGsmLac(lac);
            measurement.setGsmRssi(rssi);
        }

        //WIFI
        if (message.contains("WIFI_START\r\n")) {
            if (message.contains("+CWLAP:")) {
                //We found accesspoints
                int index = message.indexOf("WIFI_START\r\n");
                int end = message.indexOf("WIFI_END\r\n");
                int length = "WIFI_START\r\n".length();
                String rawData = message.substring(index + length, end);
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
                for (String ap : aps) {
                    commaPos = ap.indexOf(',');
                    rssi = ap.substring(1, commaPos);
                    mac = ap.substring(commaPos + 2, commaPos + 19);
                    AccessPoint accessPoint = new AccessPoint(rssi, mac);
                    measurement.addAccessPoint(accessPoint);
                }
                Log.d(TAG, "First AP rssi: " + measurement.getAccessPoint(0).rssi);
                Log.d(TAG, "First AP mac: " + measurement.getAccessPoint(0).mac);
                Log.d(TAG, "Last AP rssi: " + measurement.getAccessPoint(aps.length - 1).rssi);
                Log.d(TAG, "Last AP mac: " + measurement.getAccessPoint(aps.length - 1).mac);
            }
        }
        return measurement;
    }

    private File getApplicationDir(String directoryName) {
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            //Media not mounted
            Log.e(TAG, "Media not mounted");
            return null;
        } else {
            //Media mounted, keep on going!
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // No permission; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                Toast.makeText(this, "Kunde inte skapa fil", Toast.LENGTH_LONG).show();
                return null;
            } else {
                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                        directoryName);
                if (!dir.exists()) {
                    //Folder does not exist, try to create new
                    if (!dir.mkdirs()) {
                        Log.e(TAG, "Directory not created");
                        return null;
                    } else {
                        return dir;
                    }
                }
                //Folder already exists, return it
                return dir;
            }
        }
    }

    private File createFile(File folder, String fileName) {
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            //Media not mounted
            Log.e(TAG, "Media not mounted");
            return null;
        } else {
            //Media mounted, keep on going!
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // No permission; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                Toast.makeText(this, "Inga rättigheter att skapa fil", Toast.LENGTH_LONG).show();
                return null;
            } else {
                File file = new File(folder, fileName);
                if (file.exists()) {
                    Toast.makeText(this, "Fil finns redan, skapar ingen ny", Toast.LENGTH_LONG).show();
                    return file;
                } else {
                    try {
                        file.createNewFile();
                        return file;
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Kunde inte skapa fil", Toast.LENGTH_LONG).show();
                        return null;
                    }
                }
            }
        }
    }

    private void writeToFile(File file, String fileContents) {
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(file, true);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveMeasurementToFile(Measurement measurement, File file) {
        Coordinate gps = measurement.getGps();
        Coordinate user = measurement.getUser();
        Coordinate wifiGoogle = measurement.getWifiGoogle();
        Coordinate wifiOpencellid = measurement.getWifiOpencellid();
        String rowData = "";

        rowData = rowData + measurement.getMeasuredTime() + ";";

        if (user != null) {
            rowData = rowData + user.lat + " " + user.lng + ";";
        } else {
            rowData = rowData + "N/A" + ";";
        }
        if (gps != null) {
            rowData = rowData + gps.lat + " " + gps.lng + ";";
        } else {
            rowData = rowData + "N/A" + ";";
        }
        if (wifiGoogle != null) {
            rowData = rowData + wifiGoogle.lat + " " + wifiGoogle.lng + ";";
        } else {
            rowData = rowData + "N/A" + ";";
        }
        if (wifiOpencellid != null) {
            rowData = rowData + wifiOpencellid.lat + " " + wifiOpencellid.lng + ";";
        } else {
            rowData = rowData + "N/A" + ";";
        }

        rowData = rowData + measurement.getGpsDistance() + ";";
        rowData = rowData + measurement.getGoogleDistance() + ";";
        rowData = rowData + measurement.getOpencellidDistance() + ";";
        if (wifiGoogle != null) {
            rowData = rowData + wifiGoogle.acc + ";";
        } else {
            rowData = rowData + "N/A" + ";";
        }
        if (wifiOpencellid != null) {
            rowData = rowData + wifiOpencellid.acc + ";";
        } else {
            rowData = rowData + "N/A" + ";";
        }
        rowData = rowData + "" + measurement.getNrOfAccessPoints() + ";";
        rowData = rowData + measurement.getGsmRssi() + ";";
        rowData = rowData + measurement.getGsmLac() + ";";
        rowData = rowData + measurement.getGsmCid() + ";";
        rowData = rowData + measurement.getSigfoxLinkQuality() + ";";

        rowData = rowData + "\r\n";

        writeToFile(file, rowData);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (connectBluetooth != null) {
            connectBluetooth.cancel();
        }

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

                    makeMeasurementButton.setVisibility(View.VISIBLE);
                    newSessionButton.setVisibility(View.VISIBLE);

                } else {
                    Toast.makeText(this, "Något gick fel", Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_USER_INPUT_LOCATION:
                Log.d(TAG, "Intent handled");
                if (resultCode == Activity.RESULT_OK) {
                    Coordinate userPosition = (Coordinate) data.getSerializableExtra("Coordinate");
                    Log.d(TAG, "User coordinates: " + userPosition.lat + ", " + userPosition.lng);
                    userCoordinate = userPosition;
                    startMeasurementSession();
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
            private ProgressDialog progress;
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
                isWaitingMessage = true;
                progress = ProgressDialog.show(MainActivity.this, "", "Hämtar data från testrigg!");  //show a progress dialog
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                isWaitingMessage = false;
                progress.cancel();
                if (message != "") {
                    Toast.makeText(MainActivity.this, "Measurement data received!", Toast.LENGTH_SHORT).show();
                    UpdateAndSaveMeasurement updateSave = new UpdateAndSaveMeasurement();
                    updateSave.execute(parseMeasureValues(message));
                }
            }
        }
    }

    private class GetSigfoxStatus extends AsyncTask<String, Void, Void> {
        URL url = null;
        int seqNumber = -1;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            lastSigfoxSequenceNumber = seqNumber;
        }

        @Override
        protected Void doInBackground(String... urls) {
            try {
                url = new URL(urls[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            int status = 1000;


            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                // Add authorization header
                String authorization = "5ad5fb0d3c8789433422e3b9:15b8835e36a3431ba1b555f2090e30f3";
                byte[] encodedBytes;
                encodedBytes = Base64.encode(authorization.getBytes(), 0);
                authorization = "Basic " + new String(encodedBytes);
                urlConnection.setRequestProperty("Authorization", authorization);

                status = urlConnection.getResponseCode();
                String responseMessage = urlConnection.getResponseMessage();
                Log.d(TAG, "Response: code " + status + ", message " + responseMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (status < 400) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String data = readStream(in);
                    Log.d(TAG, "InputStream: " + data);

                    if (data.contains("seqNumber\":")) {
                        int index = data.indexOf("seqNumber\":") + "seqNumber\":".length();
                        int end = data.indexOf(',', index);
                        String number = data.substring(index, end);
                        seqNumber = Integer.valueOf(number);
                        Log.d(TAG, "seqNumber: " + seqNumber);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return null;
        }

        private String readStream(InputStream is) throws IOException {
            StringBuilder sb = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(is), 1000);
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
            }
            is.close();
            return sb.toString();
        }
    }

    private class PostHttpConnection extends AsyncTask<String, Void, Void> {
        private URL url = null;
        private String output;
        private String response = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            postHttpConnectionDone = false;
            Log.d(TAG, "http pre " + postHttpConnectionDone);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            postHttpConnectionDone = true;
            Log.d(TAG, "http post " + postHttpConnectionDone);
        }


        @Override
        protected Void doInBackground(String... urls) {
            try {
                url = new URL(urls[0]);
                output = urls[1];
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;


            try {
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setChunkedStreamingMode(0);
                //urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "*/*");
                //urlConnection.setFixedLengthStreamingMode(output.getBytes().length);

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                writeStream(out, output);
                int status = urlConnection.getResponseCode();
                String responseMessage = urlConnection.getResponseMessage();
                Log.d(TAG, "Response: code " + status + ", message " + responseMessage);

                if (status < 400) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    response = readStream(in);
                    Log.d(TAG, "InputStream: " + response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return null;
        }

        private void writeStream(OutputStream out, String message) throws IOException {
            out.write(message.getBytes());
            out.flush();
        }

        private String readStream(InputStream is) throws IOException {
            StringBuilder sb = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(is), 1000);


            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
            }
            is.close();
            return sb.toString();
        }
    }

    private class UpdateAndSaveMeasurement extends AsyncTask<Measurement, Void, Void> {
        private Measurement measurement;
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(MainActivity.this, "", "Ger Sigfox lite tid...");  //show a progress dialog
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            viewMeasurementOnmapButton.setVisibility(View.VISIBLE);
            progress.cancel();
        }

        @Override
        protected Void doInBackground(Measurement... measurements) {
            measurement = measurements[0];
            measurement.setUser(userCoordinate);
            userCoordinate = null;
            Coordinate gps = measurement.getGps();
            Coordinate user = measurement.getUser();
            Coordinate wifiGoogle;
            Coordinate wifiOpencellid;
            AccessPoint[] accessPoints = measurement.getAccessPoints();
            int nrOfAccessPoints = measurement.getNrOfAccessPoints();

            Log.d(TAG, "AccessPoint[] size = " + accessPoints.length);
            Log.d(TAG, "nrOfAccessPoints = " + nrOfAccessPoints);


            if (nrOfAccessPoints >= 2) {
                //Get location from google based on accesspoints
                String[] params = new String[2];
                params[0] = "https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyCfoPMvLd1nctIkv7shLHWzZs-Qb97I2og";
                params[1] = "{\"considerIp\": \"false\",\"wifiAccessPoints\": [";

                for (AccessPoint ap : accessPoints) {
                    if (measurement.getAccessPoint(nrOfAccessPoints - 1).equals(ap)) {
                        params[1] = params[1] + ap.getJsonForGoogle();
                    } else {
                        params[1] = params[1] + ap.getJsonForGoogle() + ",";
                    }
                }
                params[1] = params[1] + "]}";

                String jsonloc = getGoogleLocationJson(params);
                updateGoogleLocation(jsonloc);

                //Get information from Opencellid
                params[0] = "https://eu1.unwiredlabs.com/v2/process.php";
                params[1] = "{\"token\": \"97b8eafda6008a\",\"wifi\": [";

                for (AccessPoint ap : accessPoints) {
                    if (measurement.getAccessPoint(nrOfAccessPoints - 1).equals(ap)) {
                        params[1] = params[1] + ap.getJsonForOpencellid();
                    } else {
                        params[1] = params[1] + ap.getJsonForOpencellid() + ",";
                    }
                }
                params[1] = params[1] + "]}";

                jsonloc = getOpencellidLocationJson(params);
                updateOpencellidLocation(jsonloc);


            }

            wifiGoogle = measurement.getWifiGoogle();
            wifiOpencellid = measurement.getWifiOpencellid();

            //Check if new sigfox message is registered in the sigfox cloud
            updateSigfoxStatus();

            //Update information and save
            lastMeasurement = measurement;
            if (!currentMeasurementSession.equals("")) {
                File folder = getApplicationDir("Testrigg");
                File file = new File(folder, currentMeasurementSession);
                saveMeasurementToFile(measurement, file);
            }

            if (gps != null) {
                gpsCoordinates.setText(gps.lat + " " + gps.lng);
            } else {
                gpsCoordinates.setText("N/A");
            }
            if (user != null) {
                userCoordinates.setText(user.lat + " " + user.lng);
            } else {
                userCoordinates.setText("N/A");
            }
            if (wifiGoogle != null) {
                wifiCoordinatesGoogle.setText(wifiGoogle.lat + " " + wifiGoogle.lng);
            } else {
                wifiCoordinatesGoogle.setText("N/A");
            }
            if (wifiOpencellid != null) {
                wifiCoordinatesOpencellid.setText(wifiOpencellid.lat + " " + wifiOpencellid.lng);
            } else {
                wifiCoordinatesOpencellid.setText("N/A");
            }

            measuredTime.setText(measurement.getMeasuredTime());
            nrOfAps.setText("" + measurement.getNrOfAccessPoints());
            gsmRssi.setText(measurement.getGsmRssi());
            gsmLac.setText(measurement.getGsmLac());
            gsmCid.setText(measurement.getGsmCid());
            sigfoxLink.setText(measurement.getSigfoxLinkQuality());


            return null;
        }


        private String getGoogleLocationJson(String[] urls) {
            URL url = null;
            String output = urls[1];
            String response = null;


            try {
                url = new URL(urls[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;


            try {
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setChunkedStreamingMode(0);
                //urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "*/*");
                //urlConnection.setFixedLengthStreamingMode(output.getBytes().length);

            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                writeStream(out, output);
                int status = urlConnection.getResponseCode();
                String responseMessage = urlConnection.getResponseMessage();
                Log.d(TAG, "Response: code " + status + ", message " + responseMessage);

                if (status < 400) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    response = readStream(in);
                    Log.d(TAG, "InputStream: " + response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return response;
        }

        private void writeStream(OutputStream out, String message) throws IOException {
            out.write(message.getBytes());
            out.flush();
        }

        private String readStream(InputStream is) throws IOException {
            StringBuilder sb = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(is), 1000);


            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
            }
            is.close();
            return sb.toString();
        }

        private void updateGoogleLocation(String json) {
            String lat = "";
            String lng = "";
            String acc = "";

            if(json != null) {
                int index = json.indexOf("\"lat\": ") + "\"lat\": ".length();

                while (json.charAt(index) != ',') {
                    lat = lat + json.charAt(index);
                    index++;
                }

                index = json.indexOf("\"lng\": ") + "\"lng\": ".length();
                while (json.charAt(index) != ' ') {
                    lng = lng + json.charAt(index);
                    index++;
                }

                index = json.indexOf("\"accuracy\": ") + "\"accuracy\": ".length();
                while (json.charAt(index) != '}') {
                    acc = acc + json.charAt(index);
                    index++;
                }

                Coordinate coordinate = new Coordinate(lat, lng, acc);
                measurement.setWifiGoogle(coordinate);

                Log.d(TAG, "lat: " + lat + "lng: " + lng + "acc: " + acc);
            }

        }

        private String getOpencellidLocationJson(String[] urls) {
            URL url = null;
            String output = urls[1];
            String response = null;


            try {
                url = new URL(urls[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;


            try {
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setChunkedStreamingMode(0);
                //urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "*/*");
                //urlConnection.setFixedLengthStreamingMode(output.getBytes().length);

            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                writeStream(out, output);
                int status = urlConnection.getResponseCode();
                String responseMessage = urlConnection.getResponseMessage();
                Log.d(TAG, "Response: code " + status + ", message " + responseMessage);

                if (status < 400) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    response = readStream(in);
                    Log.d(TAG, "InputStream: " + response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return response;
        }

        private void updateOpencellidLocation(String json) {
            String lat = "";
            String lng = "";
            String acc = "";
            int index = json.indexOf("\"lat\":") + "\"lat\":".length();

            while (json.charAt(index) != ',') {
                lat = lat + json.charAt(index);
                index++;
            }

            index = json.indexOf("\"lon\":") + "\"lon\":".length();
            while (json.charAt(index) != ',') {
                lng = lng + json.charAt(index);
                index++;
            }

            index = json.indexOf("\"accuracy\":") + "\"accuracy\":".length();
            while (json.charAt(index) != '}') {
                acc = acc + json.charAt(index);
                index++;
            }
            try {
                Double.valueOf(lat);
                Coordinate coordinate = new Coordinate(lat, lng, acc);
                measurement.setWifiOpencellid(coordinate);
            }catch (NumberFormatException e) {

            }



            Log.d(TAG, "lat: " + lat + "lng: " + lng + "acc: " + acc);
        }

        private void updateSigfoxStatus() {

            URL url = null;
            HttpURLConnection urlConnection = null;
            int status = 1000;
            int seqNumber = -1;
            String linkQuality = null;

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                url = new URL("https://backend.sigfox.com/api/devices/38D054/messages?limit=1");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                // Add authorization header
                String authorization = "5ad5fb0d3c8789433422e3b9:15b8835e36a3431ba1b555f2090e30f3";
                byte[] encodedBytes;
                encodedBytes = Base64.encode(authorization.getBytes(), 0);
                authorization = "Basic " + new String(encodedBytes);
                urlConnection.setRequestProperty("Authorization", authorization);

                status = urlConnection.getResponseCode();
                String responseMessage = urlConnection.getResponseMessage();
                Log.d(TAG, "Response: code " + status + ", message " + responseMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (status < 400) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String data = readStream(in);
                    Log.d(TAG, "InputStream: " + data);

                    if (data.contains("seqNumber\":")) {
                        int index = data.indexOf("seqNumber\":") + "seqNumber\":".length();
                        int end = data.indexOf(',', index);
                        String number = data.substring(index, end);
                        seqNumber = Integer.valueOf(number);
                        Log.d(TAG, "seqNumber: " + seqNumber);
                        if (seqNumber > lastSigfoxSequenceNumber) {
                            //new message received since last check
                            lastSigfoxSequenceNumber = seqNumber;
                            if (data.contains("linkQuality\":\"")) {
                                index = data.indexOf("linkQuality\":\"") + "linkQuality\":\"".length();
                                end = data.indexOf('"', index);
                                linkQuality = data.substring(index, end);
                                Log.d(TAG, "linkQuality: " + linkQuality);
                                measurement.setSigfoxLinkQuality(linkQuality);
                            }
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        }
    }
}

