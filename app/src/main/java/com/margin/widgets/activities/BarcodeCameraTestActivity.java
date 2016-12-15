package com.cargomatrix.widgets.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cargomatrix.barcode.fragments.CameraBarcodeFragment;
import com.cargomatrix.barcode.listeners.OnBarcodeReceivedListener;

/**
 * Created by CargoMatrix, Inc. on Apr 06, 2016.
 *
 * @author Steve Tsourounis {steve.tsourounis@cargomatrix.com}
 */
public class BarcodeCameraTestActivity extends AppCompatActivity implements
        OnBarcodeReceivedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment barcodeFragment = new CameraBarcodeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, barcodeFragment).commit();
    }

    @Override
    public void onBarcodeReceived(String barcode) {
        Toast.makeText(this, "Barcode received: " + barcode, Toast.LENGTH_SHORT).show();
    }
}
