package example.com.spotifystreamerv2;


import android.os.AsyncTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by ken on 11/06/2015.
 */
public class SpotifyArtistTopTen extends AsyncTask<String, Void, Tracks> {
    private final String TAG = "SpotifyArtistTop10Query";

    @Override
    protected Tracks doInBackground(String... strings) {
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();
        HashMap<String, Object> spotifyMap = new HashMap<String, Object>();
        spotifyMap.put("country", new String("US"));
        Tracks artistsTopTen = spotify.getArtistTopTrack(strings[0], spotifyMap);
        return artistsTopTen;
    }

    @Override
    protected void onPostExecute(Tracks tracks) {
        //List<Tracks> topTenTracks = tracks;
        super.onPostExecute(tracks);
    }
}
