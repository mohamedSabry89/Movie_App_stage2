package android.example.searchmovies.trailers;

public class Trailer {

    private String videoKey;
    private String videoName;
    private String site;

    public Trailer() {
    }

    public Trailer(String videoKey, String videoName, String site) {
        this.videoKey = videoKey;
        this.videoName = videoName;
        this.site = site;
    }

    public String getVideoKey() {
        return videoKey;
    }

    public void setVideoKey(String videoKey) {
        this.videoKey = videoKey;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
