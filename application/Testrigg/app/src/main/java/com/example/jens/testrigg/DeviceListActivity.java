package com.example.jens.testrigg;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeviceListActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ArrayList list = intent.getStringArrayListExtra(MainActivity.EXTRA_MESSAGE);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Get the device MAC address, the last 17 chars in the View
        String deviceInfo = ((TextView) v).getText().toString();
        String macAddress = deviceInfo.substring(deviceInfo.length() - 17);

        Intent data = new Intent();
        data.setData(Uri.parse(macAddress));
        setResult(RESULT_OK, data);
        finish();
    }
}
