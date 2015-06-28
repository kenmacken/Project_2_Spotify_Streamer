package example.com.spotifystreamer.Models;

import java.io.Serializable;

/**
 * Created by ken on 11/06/2015.
 */
public class TrackInfo implements Serializable {
    private static final long serialVersionUID = -1213949467658913457L;
    public String trackName;
    public String trackId;
    public String albumName;
    public String albumImgUrl;
    public String previewUrl;
    public int trackNumber;

    public TrackInfo(String trackName, String trackId, String albumName, String albumImgUrl, String previewUrl, int trackNumber) {
        this.trackName = trackName;
        this.trackId = trackId;
        this.albumName = albumName;
        this.albumImgUrl = albumImgUrl;
        this.previewUrl = previewUrl;
        this.trackNumber = trackNumber;
    }
}
