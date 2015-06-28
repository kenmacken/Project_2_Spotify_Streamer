package example.com.spotifystreamer.SpotifyAPI;

import android.os.AsyncTask;

import java.util.List;

import example.com.spotifystreamer.Fragments.ArtistFragment;
import example.com.spotifystreamer.Models.ArtistInfo;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by ken on 10/06/2015.
 */
public class SpotifyArtistQuery extends AsyncTask<String, Void, ArtistsPager> {
    private final String TAG = "SpotifyArtistQuery";

    @Override
    protected ArtistsPager doInBackground(String... strings) {
        try {
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            ArtistsPager artistsPager = spotify.searchArtists(strings[0]);
            return artistsPager;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArtistsPager artistsPager) {
        try {
            List<Artist> artistList = artistsPager.artists.items;
            ArtistFragment.getmArtistListAdapter().clear();
            String artistName;
            String artistImgUrl;
            String artistId;
            for(int i = 0; i < artistList.size(); i++) {
                artistName = artistList.get(i).name;
                if(artistList.get(i).images.isEmpty()) {
                    artistImgUrl = "";
                } else {
                    artistImgUrl = artistList.get(i).images.get(0).url;
                }
                artistId = artistList.get(i).id;
                ArtistFragment.getmArtistListAdapter().add(new ArtistInfo(artistName, artistImgUrl, artistId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
