package com.watermark.community_app.communityapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.watermark.community_app.communityapp.adapters.HomeWeeklyAdapter;
import com.watermark.community_app.communityapp.data.CommunityQuestionsData;
import com.watermark.community_app.communityapp.data.PostData;

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
    View root = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        initAdapters(root);
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postIntent = new Intent(getContext(), PostActivity.class);
        weeklyAdapter = new HomeWeeklyAdapter();
    }

    private void initAdapters(View root) {
        RecyclerView weeklyScroll = root.findViewById(R.id.weekly_scroll);
        weeklyScroll.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        weeklyScroll.setAdapter(weeklyAdapter);
    }

    public void tableContentLoaded() {
        ArrayList<PostData> weeklyMediaEntries = contentManager.getWeeklyMediaEntries();
        
        weeklyAdapter.swapData(weeklyMediaEntries);
        weeklyAdapter.setCallbacks(new HomeWeeklyAdapter.HomeWeeklyCallbacks() {
            @Override
            public void onItemClicked(PostData postData) {
                MainActivity.launchPostActivity(postData, postIntent, requireActivity());
            }
        });
    }

    public void questionContentLoaded() {
        // TODO: Need to refactor this method to match the tableContentLoaded() so that the content will stay loaded when the fragment is switched.
        ArrayList<CommunityQuestionsData> questions = contentManager.getCommunityQuestions();

        // Get the main questions view
        View questionsLayout = root.findViewById(R.id.questions);
        LinearLayout questionsLayoutBody = (LinearLayout) questionsLayout;

        // Add each question to the view
        for (CommunityQuestionsData q : questions) {
            final TextView title = new TextView(getContext());
            title.setPadding(13, 10, 0, 0);
            title.setText(q.getQuestion());
            title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            title.setClickable(true);

            final TextView description = new TextView(getContext());
            description.setPadding(13, 2, 5, 10);
            description.setText(q.getDescription());
            description.setBackgroundColor(getResources().getColor(R.color.offWhite));
            description.setVisibility(View.GONE);

            // Set up the expand/collapse action
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (description.isShown()) {
                        slide_up(getContext(), description);
                        description.setVisibility(View.GONE);
                    } else {
                        description.setVisibility(View.VISIBLE);
                        slide_down(getContext(), description);
                    }
                }
            });

            questionsLayoutBody.addView(title);
            questionsLayoutBody.addView(description);
        }
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
