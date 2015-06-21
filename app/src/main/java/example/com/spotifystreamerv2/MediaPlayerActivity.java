package example.com.spotifystreamerv2;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import example.com.spotifystreamerv2.Fragments.MediaPlayerFragment;
import example.com.spotifystreamerv2.Models.TrackInfo;

/**
 * Created by ken on 15/06/2015.
 */
public class MediaPlayerActivity extends FragmentActivity {

    private final String TAG = "MediaPlayerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        TrackInfo track = (TrackInfo) getIntent().getSerializableExtra("track");
        if (savedInstanceState == null) {
            Log.d(TAG, "track data: " + track);
            Bundle arguments = new Bundle();
            arguments.putSerializable("track", track);
            arguments.putBoolean("large", false);
            MediaPlayerFragment fragment = MediaPlayerFragment.newInstance(false);
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.container_MediaPlayer, fragment).commit();
        }
    }

}
