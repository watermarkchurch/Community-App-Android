package com.watermark.community_app.communityapp;

import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.google.gson.internal.LinkedTreeMap;
import com.watermark.community_app.communityapp.data.CommunityQuestionsData;
import com.watermark.community_app.communityapp.data.PostData;
import com.watermark.community_app.communityapp.data.ShelfItem;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Blake on 3/1/2019.
 *
 * This class will pull all content from the Contentful space.
 */
public class ContentManager {

    // Callback to the main GUI thread
    public interface ContentLoadedCallback {
        void tableContentLoaded();
        void questionContentLoaded();
        void pantryContentLoaded();
    }
    private ContentLoadedCallback loadedCallback;

    // Contentful Connection Info/Client
    private static final String apiKey = "1a59417431042ea345e03358ae98e58b4f2302a25608e62eec457c05e469cf25";
    private static final String spaceId = "943xvw9uyovc";
    private CDAClient client = CDAClient.builder().setSpace(spaceId).setToken(apiKey).build();

    // Content Type Arrays
    private CDAArray table_cda_array;
    private CDAArray questions_cda_array;
    private CDAArray pantry_cda_array;
    private CDAArray shelf_cda_array;

    // Processed Arrays
    private ArrayList<PostData> table_list = new ArrayList<>();
    private ArrayList<CommunityQuestionsData> questions_list = new ArrayList<>();
    private ArrayList<ShelfItem> pantry_list = new ArrayList<>();

    // Singleton loading
    private static class InstanceLoader {
        private static final ContentManager instance = new ContentManager();
    }

    public static ContentManager getInstance() {
        return InstanceLoader.instance;
    }

    // Constructor: Pull all content types and store locally
    private ContentManager() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                table_cda_array = client.fetch(CDAEntry.class).where("content_type", "table").all();
                questions_cda_array = client.fetch(CDAEntry.class).where("content_type", "communityQuestions").all();
                pantry_cda_array = client.fetch(CDAEntry.class).where("content_type", "pantry").all();
                shelf_cda_array = client.fetch(CDAEntry.class).where("content_type", "shelf").all();
            }
        });

        processQuestionContent();
        processTableContent();
        processPantryContent();
    }

    public void setCallback(ContentLoadedCallback callback) {
        loadedCallback = callback;
    }

    private void processTableContent() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground (Void...params){
                // Process each item in the array
                for (CDAResource r : table_cda_array.items()) {
                    CDAEntry entry = (CDAEntry) r;
                    ArrayList<CDAEntry> posts = entry.getField("posts");

                    // Process each post in the entry
                    for (CDAEntry postEntry : posts) {
                        CDAEntry post = table_cda_array.entries().get(postEntry.id());
                        PostData data = processPostData(post, table_cda_array);
                        table_list.add(data);
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute (Void v){
                loadedCallback.tableContentLoaded();
            }
        }.execute();
    }

    private void processQuestionContent() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground (Void...params){
                // Process each item in the array
                for (CDAResource r : questions_cda_array.items()) {
                    CDAEntry e = (CDAEntry) r;
                    ArrayList<CDAEntry> questions = e.getField("questions");

                    // Process each question in the entry
                    for (CDAEntry postEntry : questions) {
                        CDAEntry post = questions_cda_array.entries().get(postEntry.id());

                        CommunityQuestionsData data = new CommunityQuestionsData(post.getField("question").toString(), post.getField("description").toString());
                        questions_list.add(data);
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute (Void v){
                loadedCallback.questionContentLoaded();
            }
        }.execute();
    }

    private void processPantryContent() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground (Void...params){
                // Process each item in the array
                for (CDAResource r : pantry_cda_array.items()) {
                    CDAEntry e = (CDAEntry) r;
                    ArrayList<CDAEntry> shelves = e.getField("shelves");

                    // Process each top level shelf
                    for (CDAEntry shelfEntry : shelves) {
                        ShelfItem data = processShelfItem(shelfEntry);
                        pantry_list.add(data);
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute (Void v){
                loadedCallback.pantryContentLoaded();
            }
        }.execute();
    }

    private PostData processPostData(CDAEntry post, CDAArray assets) {
        BitmapDrawable background = null;
        CDAAsset image = post.getField("postImage");
        if (image != null && assets.assets() != null) {
            CDAAsset imageAsset = assets.assets().get(image.id());
            LinkedTreeMap map = imageAsset.getField("file");
            String imageUrl = (String) map.get("url");
            URL url = null;
            try {
                //TODO: Setting the background to an image is slow. Commenting out while developing
                //url = new URL("http:" + imageUrl);
                //Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                //background = new BitmapDrawable(bitmap);
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
            }
        }

        return new PostData(checkFieldForNull(post, "title"), checkFieldForNull(post, "mediaUrl"), checkFieldForNull(post, "content"), background);
    }

    /**
     * Recursive Algorithm to build the pantry menu.
     */
    private ShelfItem processShelfItem(CDAEntry shelfEntry) {
        CDAEntry shelf = shelf_cda_array.entries().get(shelfEntry.id());
        ArrayList<CDAEntry> posts = shelf.getField("posts");
        ArrayList<CDAEntry> shelves = shelf.getField("shelves");

        ArrayList<ShelfItem> nestedShelvesData = new ArrayList<>();
        ArrayList<PostData> postData = new ArrayList<>();

        if (posts != null) {
            for (CDAEntry p : posts) {
                PostData data = processPostData(p, shelf_cda_array);
                postData.add(data);
            }
        }

        if (shelves == null || shelves.isEmpty()) {
            return new ShelfItem(shelf.getField("name").toString(), postData, nestedShelvesData);
        }

        for (CDAEntry ns : shelves) {
            ShelfItem i = processShelfItem(ns);
            if (i != null) {
                nestedShelvesData.add(i);
            }
        }

        return new ShelfItem(shelf.getField("name").toString(), postData, nestedShelvesData);
    }

    private String checkFieldForNull(CDAEntry p, String s) {
        String result = "";
        if (p.getField(s) != null) {
            result = p.getField(s).toString();
        }

        return result;
    }

    public ArrayList<CommunityQuestionsData> getCommunityQuestions() {
        return questions_list;
    }

    public ArrayList<PostData> getWeeklyMediaEntries() {
        return table_list;
    }

    public ArrayList<ShelfItem> getPantryEntries() {
        for (ShelfItem i : pantry_list) {
            System.out.println("--------------------------------------");
            System.out.println(i.toString());
            System.out.println("--------------------------------------");
        }
        return pantry_list;
    }
}
