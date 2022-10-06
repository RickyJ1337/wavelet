import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) { // Gives current number if the path is just /
            return String.format("Number: %d", num);
        } else if (url.getPath().equals("/increment")) { // Increases num by 1 if path is /increment
            num += 1;
            return String.format("Number incremented!");
        } else {
            System.out.println("Path: " + url.getPath()); 
            if (url.getPath().contains("/add")) { // Increases num by whatever count is if path is /add
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("count")) {
                    num += Integer.parseInt(parameters[1]);
                    return String.format("Number increased by %s! It's now %d", parameters[1], num);
                }
            }
            return "404 Not Found!"; // Error message for all other paths
        }
    }
}

class NumberServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
