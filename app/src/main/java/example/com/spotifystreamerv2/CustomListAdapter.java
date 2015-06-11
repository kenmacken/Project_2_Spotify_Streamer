package example.com.spotifystreamerv2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ken on 10/06/2015.
 */
public class CustomListAdapter extends ArrayAdapter<ArtistInfo> {

    private final Context mContext;

    public CustomListAdapter(Context mContext, ArrayList<ArtistInfo> artists) {
        super(mContext, R.layout.list_item_artist, artists);
        this.mContext = mContext;
    }

    public View getView(int position, View view, ViewGroup parent) {
        ArtistInfo artist = getItem(position);
        if(view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_artist, parent, false);
        }

        TextView txtTitle = (TextView) view.findViewById(R.id.list_item_artist_textview);
        ImageView imageView = (ImageView) view.findViewById(R.id.list_item_artist_image_view);
        txtTitle.setText(artist.artistName);
        if(!artist.artistImgUrl.isEmpty()) {
            Picasso.with(mContext).load(artist.artistImgUrl).resize(50,50).centerCrop().into(imageView);
        }
        return view;
    }
}