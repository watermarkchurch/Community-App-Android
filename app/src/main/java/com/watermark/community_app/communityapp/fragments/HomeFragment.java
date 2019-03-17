package com.watermark.community_app.communityapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import com.watermark.community_app.communityapp.data.CommunityQuestionsData;
import com.watermark.community_app.communityapp.data.PostData;

import java.util.ArrayList;

/**
 * Created by Blake on 2/16/2019.
 *
 * The "Home" page.
 */
public class HomeFragment extends Fragment {
    private ContentManager contentManager = ContentManager.getInstance();
    private View thisView = null;
    private Intent postIntent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        postIntent = new Intent(getContext(), PostActivity.class);
        if (thisView == null) {
            thisView = inflater.inflate(R.layout.home_layout, container, false);
        }
        return thisView;
    }

    public void tableContentLoaded() {
        ArrayList<PostData> weeklyMediaEntries = contentManager.getWeeklyMediaEntries();

        // Get the main horizontal scroll view
        View scroll = thisView.findViewById(R.id.weekly_scroll);
        LinearLayout scrollView = (LinearLayout) scroll;

        // Add each table entry to the scroll view
        for (final PostData postData : weeklyMediaEntries) {
            TextView title = new TextView(getContext());
            title.setText(postData.getTitle());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(250, 300);
            params.setMargins(10, 10, 10, 10);
            title.setLayoutParams(params);
            //TODO: Setting the background to an image is slow. Commenting out while developing
            title.setBackgroundColor(getResources().getColor(R.color.black));
            //title.setBackground(e.getPostImage());
            title.setTextSize(25);
            title.setTextColor(getResources().getColor(R.color.white));
            title.setPadding(13, 0, 5, 0);
            scrollView.addView(title);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.launchPostActivity(postData, postIntent, (AppCompatActivity) getActivity());
                }
            });
        }
    }

    public void questionContentLoaded() {
        ArrayList<CommunityQuestionsData> questions = contentManager.getCommunityQuestions();

        // Get the main questions view
        View questionsLayout = thisView.findViewById(R.id.questions);
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

    private void slide_down(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    private void slide_up(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
}
