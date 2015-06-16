package example.com.spotifystreamerv2.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.spotifystreamerv2.R;

/**
 * Created by ken on 15/06/2015.
 */
public class MediaPlayerFragment extends Fragment {

    public MediaPlayerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_media_player, container);
        return rootView;
    }
}
