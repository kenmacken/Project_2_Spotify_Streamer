package example.com.spotifystreamerv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import example.com.spotifystreamerv2.Fragments.ArtistTopTenFragment;
import example.com.spotifystreamerv2.Models.ArtistInfo;
import example.com.spotifystreamerv2.Models.TrackInfo;

/**
 * Created by ken on 11/06/2015.
 */
public class TopTenActivity extends FragmentActivity implements ArtistTopTenFragment.OnTrackSelectedListener {
    private final String TAG = "TopTenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        //
        ArtistInfo artist = (ArtistInfo) getIntent().getSerializableExtra("artist");
        //
        if(savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putSerializable("artist", artist);
            ArtistTopTenFragment fragment = new ArtistTopTenFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_TopTen, fragment)
                    .commit();
        }
    }

    @Override
    public void onTrackSelected(TrackInfo track) {
        Log.d(TAG, "track selected: " + track.trackName);
        Intent intent = new Intent(this, MediaPlayerActivity.class);
        intent.putExtra("track", track);
        startActivity(intent);
    }
}
