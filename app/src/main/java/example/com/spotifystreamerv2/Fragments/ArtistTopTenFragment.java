package example.com.spotifystreamerv2.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import example.com.spotifystreamerv2.Adapters.TopTenListAdapter;
import example.com.spotifystreamerv2.MediaPlayerActivity;
import example.com.spotifystreamerv2.Models.TrackInfo;
import example.com.spotifystreamerv2.R;

/**
 * Created by ken on 11/06/2015.
 */
public class ArtistTopTenFragment extends Fragment {
    private static ArrayList<TrackInfo> arrayOfTracks = new ArrayList<>();
    private static TopTenListAdapter mTopTenListAdapter;

    public ArtistTopTenFragment() {
    }

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

        setmTopTenListAdapter(new TopTenListAdapter(getActivity(), arrayOfTracks));
        ListView listView = (ListView) rootView.findViewById(R.id.listview_artist_top_ten);
        listView.setAdapter(getmTopTenListAdapter());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), MediaPlayerActivity.class);
                intent.putExtra("ALBUM_NAME", getmTopTenListAdapter().getItem(i).albumName);
                intent.putExtra("TRACK_NAME", getmTopTenListAdapter().getItem(i).trackName);
                intent.putExtra("PREVIEW_URL", getmTopTenListAdapter().getItem(i).previewUrl);
                intent.putExtra("IMAGE_URL", getmTopTenListAdapter().getItem(i).albumImgUrl);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
