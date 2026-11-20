package com.example.voloshenko;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Нижняя панель на три экрана.
 * Волошенко Кирилл Андреевич, Т-433901-ИСТ.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        Fragment feedbackFragment = new FeedbackFragment();
        Fragment testFragment = new TestFragment();
        Fragment notifyFragment = new NotifyFragment();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.feedback) {
                setCurrentFragment(feedbackFragment);
            } else if (id == R.id.test) {
                setCurrentFragment(testFragment);
            } else if (id == R.id.notify) {
                setCurrentFragment(notifyFragment);
            }
            return true;
        });

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.feedback);
        }
    }

    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, fragment)
                .commit();
    }
}
