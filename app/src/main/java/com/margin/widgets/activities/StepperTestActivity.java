package com.cargomatrix.widgets.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cargomatrix.components.fragments.StepperFragment;
import com.cargomatrix.components.interfaces.INavigable;
import com.cargomatrix.widgets.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CargoMatrix, Inc. on Mar 31, 2016.
 *
 * @author Denis Shakinov {dshakinov@xbsoftware.com}
 */
public class StepperTestActivity extends AppCompatActivity {

    private static final int FRAGMENTS_COUNT = 10;
    private static final int ADDING_TEST_DELAY = 1200;
    private static final String NUMBER = "number";
    private List<String> mTitles = new ArrayList<>();
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new StepperTestFragment()).commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacksAndMessages(null);
    }

    @SuppressLint("ValidFragment")
    private class StepperTestFragment extends StepperFragment {

        private StepperFragment.StepperPagerAdapter mPagerAdapter;

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            for (int i = 0; i < FRAGMENTS_COUNT; i++) {
                final int number = i + 1;
                //dynamically adding test
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //pass fragment with arguments
                        TestEmptyFragment fragment = new TestEmptyFragment();
                        Bundle arguments = new Bundle();
                        arguments.putInt(NUMBER, number);
                        fragment.setArguments(arguments);
                        mPagerAdapter.addFragment(fragment);
                        mTitles.add("Title #" + number);
                    }
                }, i * ADDING_TEST_DELAY);
            }
        }

        @Override
        protected StepperPagerAdapter getPagerAdapter() {
            if (mPagerAdapter == null) {
                mPagerAdapter = new StepperPagerAdapter(getChildFragmentManager());
            }
            return mPagerAdapter;
        }

        @Override
        protected void onPageChanged(int position) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(mTitles.get(position));
            }
        }

        @Override
        protected void onDonePressed() {
            Toast.makeText(getContext(), "Done pressed!", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ValidFragment")
    private class TestEmptyFragment extends Fragment implements INavigable {

        private int number;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                number = getArguments().getInt(NUMBER);
            }
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            textView.setGravity(Gravity.CENTER);
            return textView;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ((TextView) view).setText("Fragment #" + number);
        }

        @Override
        public boolean canMoveNext() {
            return true;
        }

        @Override
        public boolean canMovePrev() {
            return true;
        }
    }
}
