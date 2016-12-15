package com.cargomatrix.widgets.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cargomatrix.components.listeners.OnSignatureChangedListener;
import com.cargomatrix.components.views.SignatureView;

/**
 * Created by CargoMatrix, Inc. on Apr 06, 2016.
 *
 * @author Denis Shakinov {dshakinov@xbsoftware.com}
 */
public class SignatureTestActivity extends AppCompatActivity
        implements OnSignatureChangedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SignatureView signatureView = new SignatureView(this, this);
        signatureView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(signatureView);
    }

    @Override
    public void onSignatureChanged(Bitmap signature) {
        Toast.makeText(this, "Signature captured!\n" +
                signature.toString(), Toast.LENGTH_SHORT).show();
        if (!signature.isRecycled()) {
            signature.recycle();
        }
    }
}
