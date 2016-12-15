package com.cargomatrix.widgets.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cargomatrix.barcode.listeners.OnBarcodeClearListener;
import com.cargomatrix.barcode.listeners.OnBarcodeReaderError;
import com.cargomatrix.barcode.listeners.OnBarcodeReceivedListener;
import com.cargomatrix.barcode.views.BarcodeEditText;
import com.cargomatrix.widgets.R;

/**
 * Created by CargoMatrix, Inc. on Apr 12, 2016.
 *
 * @author Denis Shakinov {dshakinov@xbsoftware.com}
 */
public class BarcodeEditTextTestActivity extends AppCompatActivity
        implements OnBarcodeReceivedListener, OnBarcodeReaderError, OnBarcodeClearListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_edittext);
        BarcodeEditText barcodeEditText = (BarcodeEditText) findViewById(R.id.barcode_edit_text);
        if (barcodeEditText != null) {
            barcodeEditText.setOnBarcodeNumberListener(this);
            barcodeEditText.setOnBarcodeErrorListener(this);
            barcodeEditText.setOnBarcodeClearListener(this);
        }
    }

    @Override
    public void onBarcodeReceived(String barcode) {
        Toast.makeText(this, "Barcode received: " + barcode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBarcodeCleared(String erasedText) {
        Toast.makeText(this, erasedText, Toast.LENGTH_SHORT).show();
    }
}
