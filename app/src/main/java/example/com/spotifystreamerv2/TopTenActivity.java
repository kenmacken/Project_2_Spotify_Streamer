package example.com.spotifystreamerv2;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import example.com.spotifystreamerv2.SpotifyAPI.SpotifyArtistTopTen;

/**
 * Created by ken on 11/06/2015.
 */
public class TopTenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        String artistId = getIntent().getExtras().getString("ARTIST_ID");
        String artistName = getIntent().getExtras().getString("ARTIST_NAME");
        getSupportActionBar().setSubtitle(artistName);

        SpotifyArtistTopTen mSpotifyArtistTopTen = new SpotifyArtistTopTen();
        mSpotifyArtistTopTen.execute(artistId);
    }
}
