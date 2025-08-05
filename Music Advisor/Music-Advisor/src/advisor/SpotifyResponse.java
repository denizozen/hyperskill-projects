package advisor;

import com.google.gson.JsonElement;

import java.util.List;

public class SpotifyResponse {

    private String name;
    private String url;
    private List<String> artists;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setArtistsList(List<String> artists) {
        this.artists = artists;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        if (!artists.isEmpty()) {
            sb.append(artists).append("\n");
        }
        sb.append(url).append("\n");
        return sb.toString();
    }
}
