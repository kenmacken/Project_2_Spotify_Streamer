package example.com.spotifystreamerv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import example.com.spotifystreamerv2.Adapters.ArtistListAdapter;
import example.com.spotifystreamerv2.Adapters.TopTenListAdapter;
import example.com.spotifystreamerv2.Fragments.ArtistFragment;
import example.com.spotifystreamerv2.Fragments.ArtistTopTenFragment;
import example.com.spotifystreamerv2.Fragments.MediaPlayerFragment;
import example.com.spotifystreamerv2.Models.ArtistInfo;
import example.com.spotifystreamerv2.Models.TrackInfo;
import example.com.spotifystreamerv2.SpotifyAPI.SpotifyArtistQuery;

public class MainActivity extends FragmentActivity implements ArtistFragment.OnArtistSelectedListener, ArtistTopTenFragment.OnTrackSelectedListener, MediaPlayerFragment.OnChangeTrackListener {
    private static final String TOPTENFRAGMENT_TAG = "TTFTAG";
    private final String TAG = "MainActivity";
    private EditText artistSearch;
    private boolean isTwoPane = false;
    private String artistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        determinePaneLayout(savedInstanceState);
        artistSearch = (EditText) findViewById(R.id.editText_artistSearch);
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
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void determinePaneLayout(Bundle savedInstanceState) {
        if (findViewById(R.id.container_TopTen) != null) {
            isTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_TopTen, new ArtistTopTenFragment(), TOPTENFRAGMENT_TAG)
                        .commit();
            }
        } else {
            isTwoPane = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onArtistSelected(ArtistInfo artist) {
        if (isTwoPane) {
            artistName = artist.artistName;
            Bundle args = new Bundle();
            args.putSerializable("artist", artist);
            ArtistTopTenFragment fragment = new ArtistTopTenFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_TopTen, fragment, TOPTENFRAGMENT_TAG)
                    .commit();
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
            Bundle args = new Bundle();
            args.putSerializable("track", track);
            args.putBoolean("large", true);
            args.putString("artist", artistName);
            MediaPlayerFragment fragment = MediaPlayerFragment.newInstance();

            fragment.setArguments(args);
            fragment.show(getSupportFragmentManager(), "tablet");
        }
    }
}
