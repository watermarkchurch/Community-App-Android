package com.watermark.community_app.communityapp.data;

/**
 * Created by Blake on 3/1/2019.
 */
public class CommunityQuestionsData {
    private String question = "";
    private String description = "";
    private boolean isExpanded = false;

    public CommunityQuestionsData(String q, String d) {
        question = q;
        description = d;
    }

    public String getQuestion() {
        return question;
    }

    public String getDescription() {
        return description;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
