package example.com.spotifystreamerv2.SpotifyAPI;

import android.os.AsyncTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.com.spotifystreamerv2.Adapters.ArtistListAdapter;
import example.com.spotifystreamerv2.Adapters.TopTenListAdapter;
import example.com.spotifystreamerv2.Fragments.ArtistTopTenFragment;
import example.com.spotifystreamerv2.Models.ArtistInfo;
import example.com.spotifystreamerv2.Models.TrackInfo;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
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
        Tracks topTenTracks = tracks;
        ArtistTopTenFragment.getmTopTenListAdapter().clear();
        String trackName;
        String albumName;
        String trackId;
        String albumImgUrl;
        String previewUrl;
        int trackNumber;
        for(int i = 0; i < topTenTracks.tracks.size(); i++) {
            Track currentTrack = topTenTracks.tracks.get(i);
            trackNumber = i;
            trackName = currentTrack.name;
            trackId = currentTrack.id;
            albumName = currentTrack.album.name;
            previewUrl = currentTrack.preview_url;
            if(currentTrack.album.images.isEmpty()) {
                albumImgUrl = "";
            } else {
                albumImgUrl = currentTrack.album.images.get(0).url;
            }
            ArtistTopTenFragment.getmTopTenListAdapter().add(new TrackInfo(trackName, trackId, albumName, albumImgUrl, previewUrl, trackNumber));
        }
    }
}
