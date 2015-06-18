package example.com.spotifystreamerv2.Models;

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

    public TrackInfo(String trackName, String trackId, String albumName, String albumImgUrl, String previewUrl) {
        this.trackName = trackName;
        this.trackId = trackId;
        this.albumName = albumName;
        this.albumImgUrl = albumImgUrl;
        this.previewUrl = previewUrl;
    }
}
