package skinapp.luca.com.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bhargavms.podslider.OnPodClickListener;
import com.bhargavms.podslider.PodSlider;

import java.util.Timer;
import java.util.TimerTask;

import skinapp.luca.com.R;
import skinapp.luca.com.fragment.LogoFragment;

public class LogoActivity extends AppCompatActivity {

    private int NUM_PAGES = 4;
    private int currentPage = 0;

    private  PodSlider podSlider;
    private ViewPager pager;

    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogoActivity.this, MainActivity.class);

                startActivity(intent);
                finish();
            }
        });

        podSlider = (PodSlider) findViewById(R.id.pod_slider);
        podSlider.setPodClickListener(new OnPodClickListener() {
            @Override
            public void onPodClick(int position) {
                Log.d("PodPosition", "position = " + position);

                currentPage = position;
            }
        });

        pager = (ViewPager) findViewById(R.id.pager);
        PodPagerAdapter adapter = new PodPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        podSlider.setUpWithViewPager(pager);

        podSlider.setCurrentlySelectedPod(currentPage);
        podSlider.setCurrentlySelectedPodAndAnimate(currentPage);

        podSlider.setMediumCircleInterpolator(null);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES - 1) {
                    currentPage = 0;
                } else {
                    currentPage++;
                }
                podSlider.setCurrentlySelectedPod(currentPage);
                podSlider.setCurrentlySelectedPodAndAnimate(currentPage);
                pager.setCurrentItem(currentPage);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }

    private class PodPagerAdapter extends FragmentStatePagerAdapter {
        public PodPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            LogoFragment fragment = new LogoFragment().newInstance(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
