package advisor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Authentication {

    public static String SERVER_PATH = "https://accounts.spotify.com";
    public static String REDIRECT_URI = "http://127.0.0.1:8080";
    public static String CLIENT_ID = "b7e62bd4956f4af1a0537c07165ec822";
    public static String CLIENT_SECRET = "85fa43f14e894a0a8f5a8a0bce8f84e4";
    public static String ACCESS_TOKEN = "";
    public static String ACCESS_CODE = "";

    static void setServerPath(String path){
        SERVER_PATH = path;
    }

    public void getAccessCode() {

        String authRequestUri = SERVER_PATH +
                "/authorize?client_id=" + CLIENT_ID +
                "&response_type=code" +
                "&redirect_uri=" + REDIRECT_URI;

        System.out.println("use this link to request the access code:");
        System.out.println(authRequestUri);

        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.start();
            server.createContext("/",
                    exchange -> {
                        String query = exchange.getRequestURI().getQuery();
                        String request;

                        if (query != null && query.contains("code")) {
                            for (String param : query.split("&")) {
                                if (param.startsWith("code=")) {
                                    ACCESS_CODE = param.substring("code=".length());
                                    break;
                                }
                            }

                            System.out.println("code received");
                            System.out.println(ACCESS_CODE);
                            request = "Got the code. Return back to your program.";
                        } else {
                            request = "Authorization code not found. Try again.";
                        }

                        exchange.sendResponseHeaders(200, request.length());
                        exchange.getResponseBody().write(request.getBytes());
                        exchange.getResponseBody().close();
                    });

            System.out.println("waiting for code...");
            while (ACCESS_CODE.isEmpty()) {
                Thread.sleep(100);
            }
            server.stop(5);

        } catch (IOException | InterruptedException e) {
            System.out.println("Server error");
        }
        System.out.println("code received");
    }

    public String getAccessToken() {

        System.out.println("making http request for access_token...");
        System.out.println("response:");

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(SERVER_PATH + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=authorization_code"
                                + "&code=" + ACCESS_CODE
                                + "&client_id=" + CLIENT_ID
                                + "&client_secret=" + CLIENT_SECRET
                                + "&redirect_uri=" + REDIRECT_URI))
                .build();

        try {

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
            System.out.println("---SUCCESS---");
            return parseAccessToken(response.body());

        } catch (InterruptedException | IOException e) {
            System.out.println("Error response");
        }

        return "";
    }

    private String parseAccessToken(String body) {
        JsonObject jo = JsonParser.parseString(body).getAsJsonObject();
        return jo.get("access_token").getAsString();
    }
}
