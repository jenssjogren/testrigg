package com.example.jens.testrigg;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.jens.testrigg.MESSAGE";
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_DEVICE_ADDRESS = 2;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private ConnectThread connectThread;

    Button pairedDevicesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pairedDevicesButton = (Button) findViewById(R.id.button_paired_devices);


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
        connectThread.cancel();
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

                    connectThread = new ConnectThread(mBluetoothAdapter.getRemoteDevice(macAddress));
                    connectThread.run();

                    Toast.makeText(this, "Lyckades: " + macAddress, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Något gick fel", Toast.LENGTH_LONG).show();
                }
                break;

        }
    }
}
