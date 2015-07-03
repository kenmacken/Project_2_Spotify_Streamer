package example.com.spotifystreamer.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

/**
 * Created by ken on 30/06/2015.
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{
    private MediaPlayer mMediaPlayer;
    private WifiManager.WifiLock wifiLock;
    private static final String ACTION_PLAY = "com.example.action.PLAY";
    private static final String ACTION_PAUSE = "com.example.action.PAUSE";
    private boolean playing = false;
    private final IBinder musicBind = new MusicBinder();


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Music Service", "start");
        setupMediaPlayer(intent.getStringExtra("SONG_URL"));
        Log.d("Music Service", "s");
        /*switch (intent.getAction()) {
            case ACTION_PLAY:
                if(mMediaPlayer != null) {
                    setupMediaPlayer(intent.getStringExtra("SONG_URL"));
                }
                break;
            case ACTION_PAUSE:
                pauseTrack(true);
                break;
        }*/

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            if (wifiLock.isHeld()) {
                wifiLock.release();
            }
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        return super.onUnbind(intent);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        pauseTrack(false);
    }

    private void setupMediaPlayer(String songUrl) {
        if(mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(songUrl);
            mMediaPlayer.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
            wifiLock = ((WifiManager) this.getSystemService(Context.WIFI_SERVICE)).createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock");
            wifiLock.acquire();
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnErrorListener(this);
        } catch (Exception e) {
            Log.e("MUSIC SERVICE", "Error setting data source", e);
            playing = false;
            wifiLock.release();
        }

    }

    public void pauseTrack(boolean pause) {
        if (pause) {
            mMediaPlayer.pause();
            wifiLock.release();
            playing = false;
            //btn_play_pause.setBackgroundResource(android.R.drawable.ic_media_play);
        } else {
            mMediaPlayer.start();
            wifiLock.acquire();
            playing = true;
            //btn_play_pause.setBackgroundResource(android.R.drawable.ic_media_pause);
            //myHandler.postDelayed(updateTrackTime, 100);
        }
    }

    public void stopTrack() {
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        wifiLock.release();
        playing = false;
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    public boolean isPlaying() {
        return playing;
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}
