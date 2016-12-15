package com.cargomatrix.widgets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cargomatrix.camera.activities.AnnotationPhotoActivity;
import com.cargomatrix.camera.activities.PhotoActivity;
import com.cargomatrix.camera.models.AnnotationType;
import com.cargomatrix.camera.models.Photo;
import com.cargomatrix.components.utils.GATrackerUtils;
import com.cargomatrix.widgets.R;
import com.cargomatrix.widgets.adapters.CategoriesAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements
        CategoriesAdapter.OnItemClickListener {

    private static final int PHOTO_WIDGET = 0;
    private static final int ANNOTATION_PHOTO_WIDGET = 1;
    private static final int BARCODE_SCANNER = 2;
    private static final int CAMERA_SCANNER = 3;
    private static final int CUSTOM_LIST = 4;
    private static final int NUMBER_SELECTOR = 5;
    private static final int DATE_SELECTOR = 6;
    private static final int STEPPER = 7;
    private static final int SIGNATURE_VIEW = 8;
    private static final int BARCODE_EDITTEXT = 9;

    public static final int TAKE_PHOTO_REQUEST = 1;  // The request code

    /* For test only */
    private Map<String, String> propertiesTest = new HashMap<>();

    {
        propertiesTest.put("Tracking Number", "Z18762534198");
        propertiesTest.put("Account", "IAI");
        propertiesTest.put("Carrier", "UPS");
        propertiesTest.put("Pickup Date", "03/21/2016");
        propertiesTest.put("Receipt Date", "03/21/2016");
        propertiesTest.put("Pieces", "10");
        propertiesTest.put("SLAC", "20");
        propertiesTest.put("Weight", "85kg");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView() {
        String[] categories = getResources().getStringArray(R.array.categories);
        CategoriesAdapter adapter = new CategoriesAdapter(categories, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.grid_view);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int position) {
        switch (position) {
            case PHOTO_WIDGET:
                PhotoActivity.startActivityForResult(this,
                        TAKE_PHOTO_REQUEST, new Random().nextInt(),
                        Environment.getExternalStorageDirectory().toString(), propertiesTest);
                GATrackerUtils.trackScreenName(this, PhotoActivity.class.getSimpleName());
                break;
            case ANNOTATION_PHOTO_WIDGET:
                String[] types = getResources().getStringArray(R.array.types);
                ArrayList<String> typesTest = new ArrayList<>(Arrays.asList(types));
                // TODO: Added conversion
                ArrayList<AnnotationType> annotationTypes = new ArrayList<>();
                for (String type : typesTest) annotationTypes.add(new AnnotationType(type));
                AnnotationPhotoActivity.startActivityForResult(this,
                        TAKE_PHOTO_REQUEST, new Random().nextInt(),
                        Environment.getExternalStorageDirectory().toString(),
                        propertiesTest, annotationTypes);
                GATrackerUtils.trackScreenName(this, AnnotationPhotoActivity.class.getSimpleName());
                break;
            case BARCODE_SCANNER:
                startActivity(new Intent(this, BarcodeReaderTestActivity.class));
                GATrackerUtils.trackScreenName(this, BarcodeReaderTestActivity.class.getSimpleName());
                break;
            case CAMERA_SCANNER:
                //startActivity(new Intent(this, BarcodeCameraTestActivity.class));
                //GATrackerUtils.trackScreenName(this, BarcodeCameraTestActivity.class.getSimpleName());
                startActivity(new Intent(this, BarcodeScannerActivity.class));
                GATrackerUtils.trackScreenName(this, BarcodeScannerActivity.class.getSimpleName());
                break;
            case NUMBER_SELECTOR:
                startActivity(new Intent(this, NumSelectorTestActivity.class));
                GATrackerUtils.trackScreenName(this, NumSelectorTestActivity.class.getSimpleName());
                break;
            case CUSTOM_LIST:
                startActivity(new Intent(this, ListComponentTestActivity.class));
                GATrackerUtils.trackScreenName(this,
                        ListComponentTestActivity.class.getSimpleName());
                break;
            case STEPPER:
                startActivity(new Intent(this, StepperTestActivity.class));
                GATrackerUtils.trackScreenName(this,
                        StepperTestActivity.class.getSimpleName());
                break;
            case SIGNATURE_VIEW:
                startActivity(new Intent(this, SignatureTestActivity.class));
                GATrackerUtils.trackScreenName(this,
                        SignatureTestActivity.class.getSimpleName());
                break;
            case BARCODE_EDITTEXT:
                startActivity(new Intent(this, BarcodeEditTextTestActivity.class));
                GATrackerUtils.trackScreenName(this,
                        BarcodeEditTextTestActivity.class.getSimpleName());
                break;
            default:
                Toast.makeText(this, "Component locked", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == TAKE_PHOTO_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user captured a photo.
                Photo photo = data.getParcelableExtra(PhotoActivity.PHOTO);
                if (photo != null) {
                    Toast.makeText(this, "PHOTO CAPTURED!\n"
                            + "Notes: " + photo.getNotes().size() + '\n'
                            + "Longitude: " + photo.getLocation().longitude() + '\n'
                            + "Latitude: " + photo.getLocation().latitude() + '\n'
                            + "Path: " + photo.image_path(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
