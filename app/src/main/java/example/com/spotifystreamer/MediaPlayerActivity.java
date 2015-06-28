package example.com.spotifystreamer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import example.com.spotifystreamer.Fragments.ArtistTopTenFragment;
import example.com.spotifystreamer.Fragments.MediaPlayerFragment;
import example.com.spotifystreamer.Models.TrackInfo;

public class MediaPlayerActivity extends AppCompatActivity implements MediaPlayerFragment.OnChangeTrackListener {

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
            getSupportActionBar().setSubtitle(artistName);
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
