package com.cargomatrix.widgets.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.cargomatrix.camera.models.Property;
import com.cargomatrix.components.fragments.FilterableListFragment;
import com.cargomatrix.components.fragments.ListFragment.OnItemClickListener;
import com.cargomatrix.components.models.IListItem;
import com.cargomatrix.widgets.R;

import java.util.ArrayList;
import java.util.List;

/**
 * For test only
 * <p/>
 * Created by CargoMatrix, Inc. on Feb 29, 2016.
 *
 * @author Denis Shakinov {dshakinov@xbsoftware.com}
 */
public class ListComponentTestActivity extends AppCompatActivity implements
        OnItemClickListener<IListItem> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_component);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        List<IListItem> items = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            items.add(new Property("Title " + i, "Subtext " + i));
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                FilterableListFragment.newInstance(items)).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onItemClick(IListItem item) {
        Toast.makeText(this, "Item clicked!\nTitle: " + item.getTitle() + "\nSubtext: " + item
                .getSubtext(), Toast.LENGTH_SHORT).show();
    }
}
