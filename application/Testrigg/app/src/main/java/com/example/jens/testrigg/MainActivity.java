package com.example.jens.testrigg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pairedDevicesButton = (Button) findViewById(R.id.button_paired_devices);
        sendButton = (Button) findViewById(R.id.button_send);


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

                Toast.makeText(MainActivity.this, "Mottaget: " + message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

