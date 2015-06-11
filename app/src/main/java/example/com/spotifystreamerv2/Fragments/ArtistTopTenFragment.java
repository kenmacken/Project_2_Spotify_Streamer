package example.com.spotifystreamerv2.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import example.com.spotifystreamerv2.Adapters.TopTenListAdapter;
import example.com.spotifystreamerv2.Models.TrackInfo;
import example.com.spotifystreamerv2.R;

/**
 * Created by ken on 11/06/2015.
 */
public class ArtistTopTenFragment extends Fragment {
    private static ArrayList<TrackInfo> arrayOfTracks = new ArrayList<>();
    private static TopTenListAdapter mTopTenListAdapter;

    public ArtistTopTenFragment(){}

    public static TopTenListAdapter getmTopTenListAdapter() {
        return mTopTenListAdapter;
    }

    public static void setmTopTenListAdapter(TopTenListAdapter mTopTenListAdapter) {
        ArtistTopTenFragment.mTopTenListAdapter = mTopTenListAdapter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_artist_top_ten, container);

        setmTopTenListAdapter(new TopTenListAdapter(getActivity(), arrayOfTracks) );
        ListView listView = (ListView) rootView.findViewById(R.id.listview_artist_top_ten);
        listView.setAdapter(getmTopTenListAdapter());
        return rootView;
    }
}
