package example.com.spotifystreamerv2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.com.spotifystreamerv2.Models.TrackInfo;
import example.com.spotifystreamerv2.R;

/**
 * Created by ken on 11/06/2015.
 */
public class TopTenListAdapter extends ArrayAdapter<TrackInfo> {

    private final Context mContext;

    public TopTenListAdapter(Context mContext, ArrayList<TrackInfo> topTracks) {
        super(mContext, R.layout.list_item_artist_top_ten);
        this.mContext = mContext;
    }

    //@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrackInfo track = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_artist_top_ten, parent, false);
        }
        TextView txtTrack = (TextView) convertView.findViewById(R.id.list_item_trackName_textview);
        TextView txtAlbum = (TextView) convertView.findViewById(R.id.list_item_albumName_textview);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_album_image_view);
        txtTrack.setText(track.trackName);
        txtAlbum.setText(track.albumName);
        if(!track.albumImgUrl.isEmpty()) {
            Picasso.with(mContext).load(track.albumImgUrl).resize(250,250).centerCrop().into(imageView);
        }
        return convertView;
    }
}
