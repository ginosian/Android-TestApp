package com.cargomatrix.widgets.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cargomatrix.barcode.fragments.BarcodeReaderFragment;
import com.cargomatrix.barcode.listeners.OnBarcodeReceivedListener;

/**
 * Created by CargoMatrix, Inc. on Mar 21, 2016.
 *
 * @author Denis Shakinov {dshakinov@xbsoftware.com}
 */
public class BarcodeReaderTestActivity extends AppCompatActivity implements
        OnBarcodeReceivedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment barcodeFragment = new BarcodeReaderFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, barcodeFragment).commit();
    }

    @Override
    public void onBarcodeReceived(String barcode) {
        Toast.makeText(this, "Barcode received: " + barcode, Toast.LENGTH_SHORT).show();
    }
}
