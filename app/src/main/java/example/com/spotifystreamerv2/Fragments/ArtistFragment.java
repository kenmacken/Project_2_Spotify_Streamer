package example.com.spotifystreamerv2.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import example.com.spotifystreamerv2.Adapters.ArtistListAdapter;
import example.com.spotifystreamerv2.Models.ArtistInfo;
import example.com.spotifystreamerv2.R;
import example.com.spotifystreamerv2.SpotifyAPI.SpotifyArtistQuery;
import example.com.spotifystreamerv2.TopTenActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistFragment extends Fragment {
    private static ArrayList<ArtistInfo> arrayOfArtists = new ArrayList<ArtistInfo>();
    private static ArtistListAdapter mArtistListAdapter;
    private OnArtistSelectedListener artistListener;

    public interface OnArtistSelectedListener {
        public void onArtistSelected(ArtistInfo artist);
    }

    public static ArrayList<ArtistInfo> getArrayOfArtists() {
        return arrayOfArtists;
    }

    public static ArtistListAdapter getmArtistListAdapter() {
        return mArtistListAdapter;
    }

    public static void setmArtistListAdapter(ArtistListAdapter mArtistListAdapter) {
        ArtistFragment.mArtistListAdapter = mArtistListAdapter;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnArtistSelectedListener) {
            artistListener = (OnArtistSelectedListener) activity;
        } else {
            throw new ClassCastException(
                    activity.toString() + " must implement ArtistFragment.OnArtistSelectedListener"
            );
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist_search, container, false);
        //
        setmArtistListAdapter(new ArtistListAdapter(getActivity(), getArrayOfArtists()));
        ListView lvArtists = (ListView) rootView.findViewById(R.id.listview_artist);
        lvArtists.setAdapter(getmArtistListAdapter());
        lvArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArtistInfo artist = getmArtistListAdapter().getItem(i);
                Log.d("artistInfoA", "artist: " + artist.artistName);
                artistListener.onArtistSelected(artist);
            }
        });
        return rootView;
    }
}
