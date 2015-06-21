package example.com.spotifystreamerv2.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.os.PowerManager;
import android.support.annotation.Nullable;
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

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import example.com.spotifystreamerv2.Models.TrackInfo;
import example.com.spotifystreamerv2.R;

/**
 * Created by ken on 15/06/2015.
 */
public class MediaPlayerFragment extends DialogFragment {

    private static final String ARG_SHOW_AS_DIALOG = "DetailedFragment.ARG_SHOW_AS_DIALOG";
    private final String TAG = "MediaPlayerFragment";

    String artistName;
    String albumName;
    String trackName;
    String previewUrl;
    String albumImgUrl;
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
    boolean paused = false;
    private Handler myHandler = new Handler();
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private WifiManager.WifiLock wifiLock;
    private Runnable updateTrackTime;

    public static MediaPlayerFragment newInstance(boolean showAsDialog) {
        Log.d("whatup", "testre");
        MediaPlayerFragment fragment = new MediaPlayerFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_SHOW_AS_DIALOG, showAsDialog);
        fragment.setArguments(args);
        return fragment;
    }

    public static MediaPlayerFragment newInstance() {
        return newInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        boolean b = args.getBoolean("large");
        /*if(b) {
            setShowsDialog(b);
        }*/
        //
        updateTrackTime = new Runnable() {
            @Override
            public void run() {
                try {
                    int trackTime = mediaPlayer.getCurrentPosition();
                    sb_trackSeek.setProgress(trackTime);
                    myHandler.postDelayed(this, 100);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        btn_next = (Button) view.findViewById(R.id.button_next);
        btn_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTrack(playing);
            }
        });

        Bundle arguments = getArguments();
        if(arguments != null) {
            TrackInfo track = (TrackInfo) arguments.getSerializable("track");
            Log.d(TAG, "track selected: " + track);
            albumName = track.albumName;
            trackName = track.trackName;
            previewUrl = track.previewUrl;
            albumImgUrl = track.albumImgUrl;
            tv_artist.setText(artistName);
            tv_track.setText(trackName);
            tv_album.setText(albumName);
            Picasso.with(getActivity()).load(albumImgUrl).resize(640, 640).centerCrop().into(iv_albumImage);
            playTrack(true);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            wifiLock.release();
        }
        super.onDestroyView();
    }

    private void playTrack(boolean play) {
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(previewUrl);
            mediaPlayer.setWakeMode(getActivity(), PowerManager.PARTIAL_WAKE_LOCK);
            wifiLock = ((WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE)).createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock");
            wifiLock.acquire();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    sb_trackSeek.setMax((int) mediaPlayer.getDuration());
                    tv_trackEnd.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getDuration()), TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getDuration())));
                    pauseTrack(false);

                }
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            //
            playing = false;
            wifiLock.release();
        }
    }

    private void pauseTrack(boolean pause) {
        if (pause) {
            mediaPlayer.pause();
            wifiLock.release();
            playing = false;
            btn_play_pause.setBackgroundResource(android.R.drawable.ic_media_play);
        } else {
            mediaPlayer.start();
            wifiLock.acquire();
            playing = true;
            btn_play_pause.setBackgroundResource(android.R.drawable.ic_media_pause);
            myHandler.postDelayed(updateTrackTime, 100);
        }
    }
}
