package advisor;

import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
    static boolean authenticated = false;
    static String accessToken;
    static String accessServer = "https://accounts.spotify.com";
    static String resourceServer = "https://api.spotify.com";
    static PageLister<?> page = null;
    static int pageSize = 5;

    public static void main(String[] args) {

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-access" -> accessServer = args[i + 1];
                case "-resource" -> resourceServer = args[i + 1];
                case "-page" -> pageSize = Integer.parseInt(args[i + 1]);
            }
        }

        Authentication.setServerPath(accessServer);
        SpotifyCallerHelper.setEndpoint(resourceServer);

        Scanner input = new Scanner(System.in);

        while(input.hasNext()){
            String[] inputs = input.nextLine().toLowerCase().split(" ");
            switch (inputs[0].toLowerCase()){
                case "auth":
                    accessToken = getServer();
                    break;
                case "featured":
                    if(!authenticated){
                        System.out.println("Please, provide access for application.");
                    } else{
                        List<SpotifyResponse> spotifyResponses = SpotifyCallerHelper.callFeatured();
                        page = new PageLister<>(spotifyResponses, pageSize);
                        page.showCurrentPage();
                    }
                    break;
                case "new":
                    if(!authenticated){
                        System.out.println("Please, provide access for application.");
                    } else{
                        List<SpotifyResponse> spotifyResponses = SpotifyCallerHelper.callNew();
                        page = new PageLister<>(spotifyResponses, pageSize);
                        page.showCurrentPage();
                    }
                    break;
                case "categories":
                    if(!authenticated){
                        System.out.println("Please, provide access for application.");
                    } else{
                        List<String> spotifyResponses = SpotifyCallerHelper.callCategories();
                        page = new PageLister<>(spotifyResponses, pageSize);
                        page.showCurrentPage();
                    }
                    break;
                case "playlists":
                    if(!authenticated){
                    System.out.println("Please, provide access for application.");
                } else{
                        List<SpotifyResponse> spotifyResponses = SpotifyCallerHelper.callPlaylists(inputs[1]);
                        page = new PageLister<>(spotifyResponses, pageSize);
                        page.showCurrentPage();
                }
                    break;
                case "exit":
                    SpotifyCallerHelper.callExit();
                    break;
                case "next":
                    if(page != null){
                        page.next();;
                    }else{
                        System.out.println("No data loaded.");
                    }
                    break;
                case "prev":
                    if(page != null){
                        page.prev();;
                    }else{
                        System.out.println("No data loaded.");
                    }

            }

        }

    }

    private static String getServer() {
        Authentication authentication = new Authentication();
        authentication.getAccessCode();
        authenticated = true;
        return authentication.getAccessToken();

    }
}
