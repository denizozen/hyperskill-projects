package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpotifyCallerHelper {

    private static String SPOTIFY_ENDPOINT = "https://api.spotify.com/v1/browse/";
    private static Map<String, String> categoryIds = new HashMap<>();

    static void setEndpoint(String endpoint){
        SPOTIFY_ENDPOINT = endpoint + "/v1/browse/";
    }
    static List<SpotifyResponse> callFeatured() {
        JsonObject jo = sendNewRequest("featured-playlists");

        System.out.println("---FEATURED---");
        List<SpotifyResponse> response = new ArrayList<>();
        if(jo != null){
            JsonArray array = jo.getAsJsonObject("playlists").getAsJsonArray("items");

            for (JsonElement jsonElement : array) {
                SpotifyResponse item = new SpotifyResponse();
                item.setName(jsonElement.getAsJsonObject().get("name").getAsString());
                item.setUrl(jsonElement.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString());
                response.add(item);
            }
        }
        return response;
    }

    static List<SpotifyResponse> callNew() {
        JsonObject jo = sendNewRequest("new-releases");
        System.out.println("---NEW RELEASES---");
        List<SpotifyResponse> response = new ArrayList<>();
        if(jo != null){
            JsonArray array = jo.getAsJsonObject("albums").getAsJsonArray("items");

            for (JsonElement jsonElement : array) {
                SpotifyResponse item = new SpotifyResponse();
                item.setName(jsonElement.getAsJsonObject().get("name").getAsString());
                JsonArray artistsArray = jsonElement.getAsJsonObject().get("artists").getAsJsonArray();

                List<String> artistsList = new ArrayList<>();
                for (JsonElement artist : artistsArray) {
                    artistsList.add(artist.getAsJsonObject().get("name").getAsString());
                }

                item.setArtistsList(artistsList);
                item.setUrl(jsonElement.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString());
                response.add(item);
            }
        }
        return response;
    }

    static void getCategories() {
        JsonObject jo = sendNewRequest("categories");
        if(jo != null){
            JsonArray array = jo.getAsJsonObject("categories").getAsJsonArray("items");

            for (JsonElement jsonElement : array) {
                String name = jsonElement.getAsJsonObject().get("name").getAsString();
                String id = jsonElement.getAsJsonObject().get("id").getAsString();
                categoryIds.put(name, id);
            }
        }
    }

    static List<String> callCategories(){
        getCategories();
        return new ArrayList<>(categoryIds.keySet());
    }

    static List<SpotifyResponse> callPlaylists(String input) {
        if (categoryIds.isEmpty()){
            getCategories();
        }

        if (!categoryIds.containsKey(input)) {
            System.out.println("Unknown category name.");
            return null;
        }

        JsonObject jo = sendNewRequest("categories/" + categoryIds.get(input) + "/playlists");
        List<SpotifyResponse> response = new ArrayList<>();
        if(jo != null){
            JsonArray array = jo.getAsJsonObject("playlists").getAsJsonArray("items");

            for (JsonElement jsonElement : array) {
                SpotifyResponse item = new SpotifyResponse();
                item.setName(jsonElement.getAsJsonObject().get("name").getAsString());
                item.setUrl(jsonElement.getAsJsonObject().get("external_urls").getAsJsonObject()
                        .get("spotify").getAsString());
            }
        }
        return response;
    }

    private static JsonObject sendNewRequest(String endpoint) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + Authentication.ACCESS_TOKEN)
                .uri(URI.create(SPOTIFY_ENDPOINT + endpoint))
                .GET()
                .build();
        System.out.println("Sending GET to: " + SPOTIFY_ENDPOINT + endpoint);

        try {

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            System.out.println("---SUCCESS---");
            return JsonParser.parseString(response.body()).getAsJsonObject();

        } catch (InterruptedException | IOException e) {
            System.out.println("Error response");
        }
        return null;
    }

    static void callExit() {
        System.out.println("---GOODBYE!---");
        System.exit(0);
    }

}
