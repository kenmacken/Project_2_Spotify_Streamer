package example.com.spotifystreamerv2;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import example.com.spotifystreamerv2.Fragments.ArtistTopTenFragment;
import example.com.spotifystreamerv2.Fragments.MediaPlayerFragment;
import example.com.spotifystreamerv2.Models.TrackInfo;

public class MediaPlayerActivity extends FragmentActivity implements MediaPlayerFragment.OnChangeTrackListener {

    private final String TAG = "MediaPlayerActivity";
    private String artistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        Log.d(TAG, "onCreate");

        TrackInfo track = (TrackInfo) getIntent().getSerializableExtra("track");
        if (savedInstanceState == null) {
            Log.d(TAG, "track data: " + track);
            artistName = getIntent().getStringExtra("artist");
            Bundle arguments = new Bundle();
            arguments.putSerializable("track", track);
            arguments.putBoolean("large", false);
            arguments.putString("artist", artistName);
            MediaPlayerFragment fragment = MediaPlayerFragment.newInstance(false);
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.container_MediaPlayer, fragment).commit();
        }
    }

    @Override
    public void onTrackSelected(int trackNumber) {
        Log.d(TAG, "onTrackSelected");
        TrackInfo track = ArtistTopTenFragment.getmTopTenListAdapter().getItem(trackNumber);
        Bundle arguments = new Bundle();
        arguments.putSerializable("track", track);
        arguments.putBoolean("large", false);
        arguments.putString("artist", artistName);
        MediaPlayerFragment fragment = MediaPlayerFragment.newInstance(false);
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_MediaPlayer, fragment).commit();
    }
}
