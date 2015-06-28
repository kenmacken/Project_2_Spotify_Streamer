package example.com.spotifystreamerv2.Fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import example.com.spotifystreamerv2.Adapters.ArtistListAdapter;
import example.com.spotifystreamerv2.Models.ArtistInfo;
import example.com.spotifystreamerv2.R;

public class ArtistFragment extends Fragment {
    private static ArrayList<ArtistInfo> arrayOfArtists = new ArrayList<>();
    private static ArtistListAdapter mArtistListAdapter;
    private static ListView lvArtists;

    public static ListView getLvArtists() {
        return lvArtists;
    }

    public interface OnArtistSelectedListener {
        void onArtistSelected(ArtistInfo artist);
    }

    public static ArtistListAdapter getmArtistListAdapter() {
        return mArtistListAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist_search, container, false);
        //
        mArtistListAdapter = new ArtistListAdapter(getActivity(), arrayOfArtists);
        lvArtists = (ListView) rootView.findViewById(R.id.listview_artist);
        lvArtists.setAdapter(mArtistListAdapter);
        lvArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArtistInfo artist = mArtistListAdapter.getItem(i);
                Log.d("artistInfoA", "artist: " + artist.artistName);
                ((OnArtistSelectedListener) getActivity()).onArtistSelected(artist);
            }
        });
        return rootView;
    }
}
