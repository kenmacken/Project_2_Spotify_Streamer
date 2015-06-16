package example.com.spotifystreamerv2.Models;

/**
 * Created by ken on 11/06/2015.
 */
public class TrackInfo {
    public String trackName;
    public String trackId;
    public String albumName;
    public String albumImgUrl;
    public String previewUrl;
    public String artistName;

    public TrackInfo(String trackName, String trackId, String albumName, String albumImgUrl, String previewUrl, String artistName) {
        this.trackName = trackName;
        this.trackId = trackId;
        this.albumName = albumName;
        this.albumImgUrl = albumImgUrl;
        this.previewUrl = previewUrl;
        this.artistName = artistName;
    }
}
