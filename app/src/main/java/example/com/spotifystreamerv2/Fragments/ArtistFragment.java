package example.com.spotifystreamerv2.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

    public ArtistFragment() {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.artistfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_refresh) {
            SpotifyArtistQuery mSpotifyArtistQuery = new SpotifyArtistQuery();
            mSpotifyArtistQuery.execute("the*");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist_search, container);

        setmArtistListAdapter(new ArtistListAdapter(getActivity(), getArrayOfArtists()));
        ListView listView = (ListView) rootView.findViewById(R.id.listview_artist);
        listView.setAdapter(getmArtistListAdapter());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "item clicked: " + adapterView.getItemAtPosition(i), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), TopTenActivity.class);
                intent.putExtra("ARTIST_ID", getmArtistListAdapter().getItem(i).artistId);
                intent.putExtra("ARTIST_NAME", getmArtistListAdapter().getItem(i).artistName);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
