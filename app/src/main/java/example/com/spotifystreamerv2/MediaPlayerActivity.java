package example.com.spotifystreamerv2;

import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import example.com.spotifystreamerv2.Fragments.MediaPlayerFragment;
import example.com.spotifystreamerv2.Models.TrackInfo;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by ken on 15/06/2015.
 */
public class MediaPlayerActivity extends FragmentActivity implements MediaPlayerFragment.OnCallback {

    private final String TAG = "MediaPlayerActivity";



    @Override
    public void onCallback() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_media_player);

        TrackInfo track = (TrackInfo) getIntent().getSerializableExtra("track");
        if(savedInstanceState == null) {
            Log.d(TAG, "testyo");
            Bundle arguments = new Bundle();
            arguments.putSerializable("track", track);
            showMediaPlayer(track);
        }
    }

    public void showMediaPlayer(TrackInfo track) {
        Bundle args = new Bundle();
        args.putSerializable("track", track);
        DialogFragment dialog = new MediaPlayerFragment();
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "MediaPlayerFragment");
    }
}
