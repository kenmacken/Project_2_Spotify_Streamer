package example.com.spotifystreamerv2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ken on 11/06/2015.
 */
public class ArtistTopTenFragment extends Fragment {

    private static CustomListAdapter mCustomListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_artist_top_ten, container);
        return rootView;
    }
}
