package example.com.spotifystreamerv2.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import example.com.spotifystreamerv2.Adapters.TopTenListAdapter;
import example.com.spotifystreamerv2.Models.ArtistInfo;
import example.com.spotifystreamerv2.Models.TrackInfo;
import example.com.spotifystreamerv2.R;
import example.com.spotifystreamerv2.SpotifyAPI.SpotifyArtistTopTen;

public class ArtistTopTenFragment extends Fragment {

    private static ArrayList<TrackInfo> arrayOfTracks = new ArrayList<>();
    private static TopTenListAdapter mTopTenListAdapter;

    public interface OnTrackSelectedListener {
        void onTrackSelected(int trackSelected);
    }

    public static TopTenListAdapter getmTopTenListAdapter() {
        return mTopTenListAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist_top_ten, container, false);
        //
        Bundle arguments = getArguments();
        if(arguments != null) {
            ArtistInfo artist = (ArtistInfo) arguments.getSerializable("artist");
            if(artist != null) {
                SpotifyArtistTopTen mSpotifyArtistTopTen = new SpotifyArtistTopTen();
                mSpotifyArtistTopTen.execute(artist.artistId);
            }
        }
        //
        mTopTenListAdapter = new TopTenListAdapter(getActivity(), arrayOfTracks);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_artist_top_ten);
        listView.setAdapter(mTopTenListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TrackInfo track = getmTopTenListAdapter().getItem(i);
                Log.d("artistInfoA", "track: " + track.trackName);
                ((OnTrackSelectedListener) getActivity()).onTrackSelected(i);
            }
        });
        return rootView;
    }
}
