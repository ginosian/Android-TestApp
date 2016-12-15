package com.cargomatrix.widgets.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cargomatrix.components.fragments.NumSelectorFragment;
import com.cargomatrix.components.listeners.OnNumSelectedListener;
import com.cargomatrix.widgets.R;

/**
 * Created by CargoMatrix, Inc. on Feb 23, 2016.
 *
 * @author Denis Shakinov {dshakinov@xbsoftware.com}
 */
public class NumSelectorTestActivity extends AppCompatActivity implements OnNumSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_selector);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                NumSelectorFragment.newInstance(0, 100, 0,
                        getString(R.string.info_message_pieces))).commit();
    }

    @Override
    public void onNumSelected(int num) {
        Toast.makeText(this, "The selected value is " + num, Toast.LENGTH_SHORT).show();
        finish();
    }
}
