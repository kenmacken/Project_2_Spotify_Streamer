package example.com.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import example.com.spotifystreamer.Fragments.ArtistFragment;
import example.com.spotifystreamer.Fragments.ArtistTopTenFragment;
import example.com.spotifystreamer.Fragments.MediaPlayerFragment;
import example.com.spotifystreamer.Models.ArtistInfo;
import example.com.spotifystreamer.Models.TrackInfo;
import example.com.spotifystreamer.Services.MusicService;
import example.com.spotifystreamer.SpotifyAPI.SpotifyArtistQuery;
import example.com.spotifystreamer.Fragments.ArtistFragment.OnArtistSelectedListener;
import example.com.spotifystreamer.Fragments.ArtistTopTenFragment.OnTrackSelectedListener;
import example.com.spotifystreamer.Fragments.MediaPlayerFragment.OnChangeTrackListener;

public class MainActivity extends AppCompatActivity implements OnArtistSelectedListener, OnTrackSelectedListener, OnChangeTrackListener {
    private static final String TOPTENFRAGMENT_TAG = "TTFTAG";
    private final String TAG = "MainActivity";
    private boolean isTwoPane = false;
    private String artistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        determinePaneLayout(savedInstanceState);
        EditText artistSearch = (EditText) findViewById(R.id.editText_artistSearch);
        artistSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String artist = editable.toString();
                SpotifyArtistQuery mSpotifyArtistQuery = new SpotifyArtistQuery();
                mSpotifyArtistQuery.execute(artist + "*");
                ArtistFragment.getLvArtists().clearChoices();
                getSupportActionBar().setSubtitle("");
            }
        });
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void determinePaneLayout(Bundle savedInstanceState) {
        if (findViewById(R.id.container_TopTen) != null) {
            isTwoPane = true;
            if (savedInstanceState == null) {
                Log.d(TAG, "new");
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container_TopTen, new ArtistTopTenFragment(), TOPTENFRAGMENT_TAG)
                        .commit();
            } else {
                Log.d(TAG, "already exists");
                return;
            }
        } else {
            isTwoPane = false;
        }
    }

    @Override
    public void onArtistSelected(ArtistInfo artist) {
        if (isTwoPane) {
            artistName = artist.getArtistName();
            getSupportActionBar().setSubtitle(artistName);
            Bundle args = new Bundle();
            args.putSerializable("artist", artist);
            //
            FragmentManager fm = getSupportFragmentManager();
            if(fm.findFragmentByTag(TOPTENFRAGMENT_TAG) != null) {
                ArtistTopTenFragment fragment = new ArtistTopTenFragment();
                fragment.setArguments(args);
                Log.d(TAG, "replace top tracks frag");
                fm.beginTransaction()
                        .replace(R.id.container_TopTen, fragment, TOPTENFRAGMENT_TAG)
                        .commit();
            } else {
                Log.d(TAG, "top tracks frag already exists");
            }
        } else {
            Intent intent = new Intent(this, TopTenActivity.class);
            intent.putExtra("artist", artist);
            startActivity(intent);
        }
    }

    @Override
    public void onTrackSelected(int trackNumber) {
        TrackInfo track = ArtistTopTenFragment.getmTopTenListAdapter().getItem(trackNumber);
        Log.d(TAG, "track selected");
        if (isTwoPane) {
            FragmentManager fm = getSupportFragmentManager();
            if(fm.findFragmentByTag("tablet") == null) {
                Log.d(TAG, "create dialog");
                Bundle args = new Bundle();
                args.putSerializable("track", track);
                args.putString("artist", artistName);
                MediaPlayerFragment fragment = MediaPlayerFragment.newInstance();
                fragment.setArguments(args);
                fragment.show(getSupportFragmentManager(), "tablet");
            } else {
                Log.d(TAG, "dialog already exists");
            }
        }
    }
}
