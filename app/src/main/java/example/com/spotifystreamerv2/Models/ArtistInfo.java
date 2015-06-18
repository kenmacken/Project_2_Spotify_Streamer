package example.com.spotifystreamerv2.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ken on 10/06/2015.
 */
public class ArtistInfo implements Serializable {
    private static final long serialVersionUID = -1213949467658913456L;
    public String artistName;
    public String artistImgUrl;
    public String artistId;

    public ArtistInfo(String artistName, String artistImgUrl, String artistId) {
        this.artistName = artistName;
        this.artistImgUrl = artistImgUrl;
        this.artistId = artistId;
    }
}
