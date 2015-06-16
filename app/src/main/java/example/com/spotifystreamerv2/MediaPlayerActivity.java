package example.com.spotifystreamerv2;

import android.content.Context;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by ken on 15/06/2015.
 */
public class MediaPlayerActivity extends AppCompatActivity {

    String previewUrl;
    Button btn_play_pause;
    SeekBar sb_trackSeek;
    TextView tv_trackEnd;
    boolean playing = false;
    boolean paused = false;
    private Handler myHandler = new Handler();
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private WifiManager.WifiLock wifiLock;
    private Runnable updateTrackTime = new Runnable() {
        @Override
        public void run() {
            try {
                int trackTime = mediaPlayer.getCurrentPosition();
                sb_trackSeek.setProgress(trackTime);
                myHandler.postDelayed(this, 100);
            } catch(Exception e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        TextView tv_artist = (TextView) findViewById(R.id.textView_artistName);
        TextView tv_track = (TextView) findViewById(R.id.textView_trackName);
        TextView tv_album = (TextView) findViewById(R.id.textView_albumName);
        ImageView iv_albumImage = (ImageView) findViewById(R.id.imageView_album);
        sb_trackSeek = (SeekBar) findViewById(R.id.seekBar_track);
        TextView tv_trackStart = (TextView) findViewById(R.id.textView_trackStart);
        tv_trackEnd = (TextView) findViewById(R.id.textView_trackEnd);
        Button btn_previous = (Button) findViewById(R.id.button_previous);
        btn_play_pause = (Button) findViewById(R.id.button_play);
        Button btn_next = (Button) findViewById(R.id.button_next);

        btn_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTrack(playing);
            }
        });

        String artistId = getIntent().getExtras().getString("ARTIST_ID");
        String artistName = getIntent().getExtras().getString("ARTIST_NAME");
        String albumName = getIntent().getExtras().getString("ALBUM_NAME");
        String trackName = getIntent().getExtras().getString("TRACK_NAME");
        previewUrl = getIntent().getExtras().getString("PREVIEW_URL");
        String albumImgUrl = getIntent().getExtras().getString("IMAGE_URL");


        tv_artist.setText(artistName);
        tv_track.setText(trackName);
        tv_album.setText(albumName);
        Picasso.with(getApplicationContext()).load(albumImgUrl).resize(640, 640).centerCrop().into(iv_albumImage);
        //tv_trackEnd.setText("0:30");

        playTrack(true);


    }

    @Override
    protected void onStop() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            wifiLock.release();
        }
        super.onStop();
    }

    private void playTrack(boolean play) {
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(previewUrl);
            mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            wifiLock = ((WifiManager) getSystemService(Context.WIFI_SERVICE)).createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock");
            wifiLock.acquire();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    sb_trackSeek.setMax((int) mediaPlayer.getDuration());
                    tv_trackEnd.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getDuration()), TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getDuration())));
                    pauseTrack(false);

                }
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            //
            playing = false;
            wifiLock.release();
        }
    }

    private void pauseTrack(boolean pause) {
        if (pause) {
            mediaPlayer.pause();
            wifiLock.release();
            playing = false;
            btn_play_pause.setBackgroundResource(android.R.drawable.ic_media_pause);
        } else {
            mediaPlayer.start();
            wifiLock.acquire();
            playing = true;
            btn_play_pause.setBackgroundResource(android.R.drawable.ic_media_play);
            myHandler.postDelayed(updateTrackTime, 100);
        }
    }
}