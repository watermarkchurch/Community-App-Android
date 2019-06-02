package com.watermark.community_app.communityapp.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.watermark.community_app.communityapp.R;

/**
 * Created by Blake on 3/12/2019.
 *
 * This Activity will show the content for a Post
 */
public class PostActivity extends AppCompatActivity {
    private CustomMediaController mediaController = null;
    private String title = "";
    private String content = "";
    private String media_url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mediaController = new CustomMediaController(this);

        // Get the Intent that started this activity and extract the string values
        Intent intent = getIntent();
        title = intent.getStringExtra(getString(R.string.title));
        content = intent.getStringExtra(getString(R.string.content));
        media_url = intent.getStringExtra(getString(R.string.media_url));

        TextView contentView = (TextView) findViewById(R.id.content);
        contentView.setText(content);

        try {
            /* TODO: this url may be a youtube link and will not play because we need a link to a video file.
             * Temporary link to use while testing: "https://s3.amazonaws.com/media-files.watermark.org/assets/20190218/a067faca-efeb-4de1-b69b-b4e6b3d1ff8f/Why-Should-Christians-Serve.mp4"
             */
            Uri uri = Uri.parse(media_url);

            final VideoView videoView = (VideoView) findViewById(R.id.video);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaController.setAnchorView(videoView);
                }
            });

            videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.start();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    private class CustomMediaController extends MediaController {
        private CustomMediaController(Context context) {
            super(context);
        }

        public void show() {
            super.show(0);
        }

        public void setAnchorView(View view){
            super.setAnchorView(view);
            View customView = View.inflate(getContext(),R.layout.video_control, null);

            TextView titleView = (TextView) customView.findViewById(R.id.titleView);
            titleView.setText(title);

            addView(customView);
        }
    }
}
