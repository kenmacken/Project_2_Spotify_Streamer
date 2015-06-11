package example.com.spotifystreamerv2.Models;

/**
 * Created by ken on 11/06/2015.
 */
public class TrackInfo {
    public String trackName;
    public String trackId;
    public String albumName;
    public String albumImgUrl;

    public TrackInfo(String trackName, String trackId, String albumName, String albumImgUrl) {
        this.trackName = trackName;
        this.trackId = trackId;
        this.albumName = albumName;
        this.albumImgUrl = albumImgUrl;
    }
}
