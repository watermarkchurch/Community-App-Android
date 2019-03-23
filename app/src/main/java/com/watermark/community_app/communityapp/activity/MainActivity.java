package com.watermark.community_app.communityapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.watermark.community_app.communityapp.ContentManager;
import com.watermark.community_app.communityapp.R;
import com.watermark.community_app.communityapp.data.PostData;
import com.watermark.community_app.communityapp.fragments.HomeFragment;
import com.watermark.community_app.communityapp.fragments.PantryFragment;
import com.watermark.community_app.communityapp.fragments.SearchFragment;

/**
 * Created by Blake on 2/16/2019.
 *
 * Main Activity that will setup the bottom navigation buttons and handle switching between fragments
 */
public class MainActivity extends AppCompatActivity {
    private ContentManager contentManager = ContentManager.getInstance();
    private HomeFragment homeFragment = new HomeFragment();
    private PantryFragment pantryFragment = new PantryFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * Launch the main activity layout and switch to the home fragment
         */
        setContentView(R.layout.activity_main);
        switchToHomeFragment();

        /*
         * Setup the bottom navigation bar. It will be responsible for switching between fragments
         */
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottombaritem_home:
                        switchToHomeFragment();
                        return true;
                    case R.id.bottombaritem_pantry:
                        switchToPantryFragment();
                        return true;
                    case R.id.bottombaritem_search:
                        switchToSearchFragment();
                        return true;
                }
                return false;
            }
        });

        contentManager.setCallback(callback);
    }

    public void switchToHomeFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.main, homeFragment).addToBackStack(null).commit();
    }

    public void switchToPantryFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.main, pantryFragment).addToBackStack(null).commit();
    }

    public void switchToSearchFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.main, searchFragment).addToBackStack(null).commit();
    }

    /**
     * The Callback class that allows the content manager to notify the MainActivity when the content has been loaded.
     */
    private ContentManager.ContentLoadedCallback callback = new ContentManager.ContentLoadedCallback() {
        @Override
        public void tableContentLoaded() {
            homeFragment.tableContentLoaded();
        }

        @Override
        public void questionContentLoaded() {
            homeFragment.questionContentLoaded();
        }

        @Override
        public void pantryContentLoaded() {
            pantryFragment.pantryContentLoaded();
        }
    };

    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public static void launchPostActivity(PostData data, Intent videoIntent, Activity parentActivity) {
        String titleLabel = parentActivity.getString(R.string.title);
        String descriptionLabel = parentActivity.getString(R.string.content);
        String urlLabel = parentActivity.getString(R.string.media_url);

        videoIntent.putExtra(titleLabel, data.getTitle());
        videoIntent.putExtra(descriptionLabel, data.getContent());
        videoIntent.putExtra(urlLabel, data.getMediaUrl());
        parentActivity.startActivity(videoIntent);
    }
}
