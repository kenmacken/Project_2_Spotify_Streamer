package example.com.spotifystreamerv2;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistFragment extends Fragment {
    private static ArrayList<ArtistInfo> arrayOfArtists = new ArrayList<ArtistInfo>();
    private static CustomListAdapter mCustomListAdapter;

    public ArtistFragment() {
    }

    public static ArrayList<ArtistInfo> getArrayOfArtists() {
        return arrayOfArtists;
    }

    public static CustomListAdapter getmCustomListAdapter() {
        return mCustomListAdapter;
    }

    public static void setmCustomListAdapter(CustomListAdapter mCustomListAdapter) {
        ArtistFragment.mCustomListAdapter = mCustomListAdapter;
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

        setmCustomListAdapter(new CustomListAdapter(getActivity(), getArrayOfArtists()));
        ListView listView = (ListView) rootView.findViewById(R.id.listview_artist);
        listView.setAdapter(getmCustomListAdapter());

        return rootView;
    }
}
