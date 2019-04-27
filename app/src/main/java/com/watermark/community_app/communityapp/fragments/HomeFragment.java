package com.watermark.community_app.communityapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.watermark.community_app.communityapp.ContentManager;
import com.watermark.community_app.communityapp.R;
import com.watermark.community_app.communityapp.activity.MainActivity;
import com.watermark.community_app.communityapp.activity.PostActivity;
import com.watermark.community_app.communityapp.adapters.HomeCommunityQuestionsAdapter;
import com.watermark.community_app.communityapp.adapters.HomeWeeklyAdapter;
import com.watermark.community_app.communityapp.data.CommunityQuestionsData;
import com.watermark.community_app.communityapp.data.PostData;
import com.watermark.community_app.communityapp.viewmodel.HomeFragmentViewModel;
import com.watermark.community_app.communityapp.viewmodel.HomeFragmentViewModelFactory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by Blake on 2/16/2019.
 * <p>
 * The "Home" page.
 */
public class HomeFragment extends Fragment {
    private ContentManager contentManager = ContentManager.getInstance();
    private Intent postIntent;
    private HomeWeeklyAdapter weeklyAdapter;
    private HomeCommunityQuestionsAdapter homeCommunityQuestionsAdapter;
    View root = null;

    private HomeFragmentViewModel homeFragmentViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        initAdapters(root);

        homeFragmentViewModel = ViewModelProviders.of(this, new HomeFragmentViewModelFactory()).get(HomeFragmentViewModel.class);

        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postIntent = new Intent(getContext(), PostActivity.class);
    }

    private void initAdapters(View root) {
        //Weekly adapter
        weeklyAdapter = new HomeWeeklyAdapter();
        weeklyAdapter.setCallbacks(new HomeWeeklyAdapter.HomeWeeklyCallbacks() {
            @Override
            public void onItemClicked(PostData postData) {
                MainActivity.launchPostActivity(postData, postIntent, requireActivity());
            }
        });
        RecyclerView weeklyScroll = root.findViewById(R.id.weekly_scroll);
        weeklyScroll.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false));
        weeklyScroll.setAdapter(weeklyAdapter);

        //Community Questions Adapter
        homeCommunityQuestionsAdapter = new HomeCommunityQuestionsAdapter();
        homeCommunityQuestionsAdapter.setCallbacks(new HomeCommunityQuestionsAdapter.Callbacks() {
            @Override
            public void onItemClicked(@NotNull CommunityQuestionsData communityQuestionsData) {

            }
        });
        RecyclerView communityQuestionsScroll = root.findViewById(R.id.questions_scroll);
        ((SimpleItemAnimator) communityQuestionsScroll.getItemAnimator()).setSupportsChangeAnimations(false);
        communityQuestionsScroll.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
        communityQuestionsScroll.setAdapter(homeCommunityQuestionsAdapter);
        communityQuestionsScroll.setHasFixedSize(false);


    }

    public void tableContentLoaded() {
        weeklyAdapter.swapData(contentManager.getWeeklyMediaEntries());
    }

    public void questionContentLoaded() {
        homeCommunityQuestionsAdapter.swapData(contentManager.getCommunityQuestions());
        // TODO: Need to refactor this method to match the tableContentLoaded() so that the content will stay loaded when the fragment is switched.
    }

    private void slide_down(Context ctx, View v) {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    private void slide_up(Context ctx, View v) {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
}
