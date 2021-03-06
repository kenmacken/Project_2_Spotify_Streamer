package example.com.spotifystreamer.Fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.DialogFragment;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import example.com.spotifystreamer.Models.TrackInfo;
import example.com.spotifystreamer.R;
import example.com.spotifystreamer.Services.MusicService;
import example.com.spotifystreamer.Services.MusicService.MusicBinder;

public class MediaPlayerFragment extends DialogFragment {

    private final String TAG = "MediaPlayerFragment";

    String artistName;
    String albumName;
    String trackName;
    String previewUrl;
    String albumImgUrl;
    int trackNumber;
    Button btn_play_pause;
    Button btn_previous;
    Button btn_next;
    SeekBar sb_trackSeek;
    TextView tv_trackEnd;
    TextView tv_artist;
    TextView tv_track;
    TextView tv_album;
    ImageView iv_albumImage;
    TextView tv_trackStart;
    boolean playing = false;
    private Handler myHandler = new Handler();
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private WifiManager.WifiLock wifiLock;
    private Runnable updateTrackTime;
    //
    private MusicService mMussicService;
    private Intent playerIntent;
    private boolean musicBound = false;

    public static MediaPlayerFragment newInstance(boolean showAsDialog) {
        MediaPlayerFragment fragment = new MediaPlayerFragment();
        return fragment;
    }

    public static MediaPlayerFragment newInstance() {
        return newInstance(true);
    }

    public interface OnChangeTrackListener {
        void onTrackSelected(int trackSelected);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        artistName = args.getString("artist");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_player, container, false);

        tv_artist = (TextView) view.findViewById(R.id.textView_artistName);
        tv_track = (TextView) view.findViewById(R.id.textView_trackName);
        tv_album = (TextView) view.findViewById(R.id.textView_albumName);
        iv_albumImage = (ImageView) view.findViewById(R.id.imageView_album);
        sb_trackSeek = (SeekBar) view.findViewById(R.id.seekBar_track);
        tv_trackStart = (TextView) view.findViewById(R.id.textView_trackStart);
        tv_trackEnd = (TextView) view.findViewById(R.id.textView_trackEnd);
        btn_previous = (Button) view.findViewById(R.id.button_previous);
        btn_play_pause = (Button) view.findViewById(R.id.button_play);

        btn_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pauseTrack(playing);
                mMussicService.pauseTrack(mMussicService.isPlaying());
            }
        });

        sb_trackSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int position, boolean userInput) {
                if(mediaPlayer != null && userInput) {
                    mediaPlayer.seekTo(position);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(mediaPlayer != null) {
                    mediaPlayer.pause();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mediaPlayer != null) {
                    mediaPlayer.start();
                }
            }
        });


        Bundle arguments = getArguments();
        if (arguments != null) {
            TrackInfo track = (TrackInfo) arguments.getSerializable("track");
            Log.d(TAG, "track selected: " + track);
            Log.d(TAG, "track number: " + track.trackNumber);
            trackNumber = track.trackNumber;
            albumName = track.albumName;
            trackName = track.trackName;
            previewUrl = track.previewUrl;
            albumImgUrl = track.albumImgUrl;
            tv_artist.setText(artistName);
            tv_track.setText(trackName);
            tv_album.setText(albumName);
            Picasso.with(getActivity()).load(albumImgUrl).resize(640, 640).centerCrop().into(iv_albumImage);
            //playTrack(previewUrl);
            //
            playerIntent = new Intent(getActivity(), MusicService.class);
            playerIntent.putExtra("SONG_URL", previewUrl);
            getActivity().bindService(playerIntent, musicConnection, Context.BIND_AUTO_CREATE);
            getActivity().startService(playerIntent);
        }

        btn_next = (Button) view.findViewById(R.id.button_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nextTrack()) {
                    ((OnChangeTrackListener) getActivity()).onTrackSelected(trackNumber + 1);
                }
            }
        });

        btn_next = (Button) view.findViewById(R.id.button_next);
        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (previousTrack()) {
                    ((OnChangeTrackListener) getActivity()).onTrackSelected(trackNumber - 1);
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private ServiceConnection musicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicBinder binder = (MusicBinder)iBinder;
            mMussicService = binder.getService();
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicBound = false;
        }
    };

    private boolean nextTrack() {
        if (ArtistTopTenFragment.getmTopTenListAdapter().getCount() > trackNumber + 1) {
            mMussicService.stopTrack();
            try {
                getDialog().dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        Toast.makeText(getActivity(), "No more tracks", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean previousTrack() {
        if (trackNumber > 0) {
            mMussicService.stopTrack();
            try {
                getDialog().dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        Toast.makeText(getActivity(), "No more tracks", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void stopTrack() {
        mediaPlayer.stop();
        wifiLock.release();
        playing = false;
    }
}
