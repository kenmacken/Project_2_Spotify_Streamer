package example.com.spotifystreamer.Models;

import java.io.Serializable;

/**
 * Created by ken on 10/06/2015.
 */
public class ArtistInfo implements Serializable {
    private static final long serialVersionUID = -1213949467658913456L;
    private String artistName;
    private String artistImgUrl;
    private String artistId;

    public ArtistInfo(String artistName, String artistImgUrl, String artistId) {
        this.artistName = artistName;
        this.artistImgUrl = artistImgUrl;
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistImgUrl() {
        return artistImgUrl;
    }

    public String getArtistId() {
        return artistId;
    }
}
