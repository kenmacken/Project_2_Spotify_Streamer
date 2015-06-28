package example.com.spotifystreamerv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import example.com.spotifystreamerv2.Fragments.ArtistTopTenFragment;
import example.com.spotifystreamerv2.Models.ArtistInfo;
import example.com.spotifystreamerv2.Models.TrackInfo;

public class TopTenActivity extends AppCompatActivity implements ArtistTopTenFragment.OnTrackSelectedListener {
    private final String TAG = "TopTenActivity";
    private String artistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        //
        Log.d(TAG, "onCreate");
        ArtistInfo artist = (ArtistInfo) getIntent().getSerializableExtra("artist");
        artistName = artist.artistName;
        //
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putSerializable("artist", artist);
            getSupportActionBar().setSubtitle(artistName);
            ArtistTopTenFragment fragment = new ArtistTopTenFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_TopTen, fragment)
                    .commit();
        }
    }

    @Override
    public void onTrackSelected(int trackNumber) {
        TrackInfo track = ArtistTopTenFragment.getmTopTenListAdapter().getItem(trackNumber);
        Log.d(TAG, "track selected: " + track.trackName);
        Intent intent = new Intent(this, MediaPlayerActivity.class);
        intent.putExtra("track", track);
        intent.putExtra("artist", artistName);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
