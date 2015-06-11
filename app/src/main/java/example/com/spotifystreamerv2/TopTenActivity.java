package example.com.spotifystreamerv2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ken on 11/06/2015.
 */
public class TopTenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        String artistId = getIntent().getExtras().getString("ARTIST_ID");
        SpotifyArtistTopTen mSpotifyArtistTopTen = new SpotifyArtistTopTen();
        mSpotifyArtistTopTen.execute(artistId);
    }
}
