package example.com.spotifystreamer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.com.spotifystreamer.Models.ArtistInfo;
import example.com.spotifystreamer.R;

/**
 * Created by ken on 10/06/2015.
 */
public class ArtistListAdapter extends ArrayAdapter<ArtistInfo> {

    private final Context mContext;

    public ArtistListAdapter(Context mContext, ArrayList<ArtistInfo> artists) {
        super(mContext, R.layout.list_item_artist, artists);
        this.mContext = mContext;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ArtistInfo artist = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_artist, parent, false);
        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.list_item_artist_textview);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_artist_image_view);
        txtTitle.setText(artist.getArtistName());
        if(!artist.getArtistImgUrl().isEmpty()) {
            Picasso.with(mContext).load(artist.getArtistImgUrl()).resize(250,250).centerCrop().into(imageView);
        }
        return convertView;
    }
}
