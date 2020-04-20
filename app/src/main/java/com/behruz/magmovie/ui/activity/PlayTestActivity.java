package com.behruz.magmovie.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.jaedongchicken.ytplayer.JLog;
import com.jaedongchicken.ytplayer.YoutubePlayerView;
import com.jaedongchicken.ytplayer.model.PlaybackQuality;
import com.jaedongchicken.ytplayer.model.YTParams;

public class PlayTestActivity extends AppCompatActivity {

    private TextView currentSec;
    private YoutubePlayerView youtubePlayerView;
    private String name;
    private String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        setContentView(R.layout.activity_play_test);
        currentSec = (TextView) findViewById(R.id.currentSec);

        // get id from XML
        youtubePlayerView = (YoutubePlayerView) findViewById(R.id.youtubePlayerView);

        // make auto height of youtube. if you want to use 'wrap_content'
        youtubePlayerView.setAutoPlayerHeight(this);

        // if you want to change white backgrond, #default is black.
        // youtubePlayerView.setWhiteBackgroundColor();

        // Control values : see more # https://developers.google.com/youtube/player_parameters?hl=en
        YTParams params = new YTParams();
        // params.setControls(0);
        params.setAutoplay(1);
        params.setVolume(100);
        params.setPlaybackQuality(PlaybackQuality.small);

        currentSec.setText(String.valueOf(0));

        // initialize YoutubePlayerCallBackListener with Params and Full Video URL
        // youtubePlayerView.initializeWithUrl("https://www.youtube.com/watch?v=dxWvtMOGAhw", params, new YoutubePlayerView.YouTubeListener())

        // initialize YoutubePlayerCallBackListener with Params and Full Video URL
        // To Use - avoid UMG block!!!! but you'd better make own your server for your real service.
        // youtubePlayerView.initializeWithCustomURL("p1Zt47V3pPw" or "http://jaedong.net/youtube/p1Zt47V3pPw", params, new YoutubePlayerView.YouTubeListener())

        // Have to use old version user, if you have already set own your handler.
        // youtubePlayerView.setHandlerDisable();

        youtubePlayerView.initializeWithCustomURL(key, params, new YoutubePlayerView.YouTubeListener() {

            @Override
            public void onReady() {
                // when player is ready.
                JLog.i("onReady()");
            }

            @Override
            public void onStateChange(YoutubePlayerView.STATE state) {
                /**
                 * YoutubePlayerView.STATE
                 *
                 * UNSTARTED, ENDED, PLAYING, PAUSED, BUFFERING, CUED, NONE
                 *
                 */

                JLog.i("onStateChange(" + state + ")");
            }

            @Override
            public void onPlaybackQualityChange(String arg) {
                String message = "onPlaybackQualityChange(" + arg + ")";
               // Toast.makeText(PlayTestActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPlaybackRateChange(String arg) {
                String message = "onPlaybackRateChange(" + arg + ")";
              //  Toast.makeText(PlayTestActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String arg) {
                String message = "onError(" + arg + ")";
             //   Toast.makeText(PlayTestActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onApiChange(String arg) {
                String message = "onApiChange(" + arg + ")";
              //e  Toast.makeText(PlayTestActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCurrentSecond(double second) {
                currentSec.setText(String.valueOf(second));
            }

            @Override
            public void onDuration(double duration) {
                String message = "onDuration(" + duration + ")";
                JLog.i(message);
            }

            @Override
            public void logs(String log) {
                // javascript debug log. you don't need to use it.
                JLog.d(log);
            }
        });


        // psuse video
        // youtubePlayerView.pause();

        // play video when it's ready
        // youtubePlayerView.play();

        // cue video
        // youtubePlayerView.onCueVideo("WCchr07kLPE");

        // start with 20 secs
        // youtubePlayerView.onLoadVideo("WCchr07kLPE", 20);

        // seek to 20 secs
        // youtubePlayerView.seekToMillis(20);
    }

    private void getData(){
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        name = intent.getStringExtra("name");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // this is optional but you need.
        youtubePlayerView.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // pause video when on the background mode.
        youtubePlayerView.pause();
    }
}
