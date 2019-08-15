import java.io.*;
import java.net.*;

public class HttpServer {
    private ServerSocket serverSocket;
    private BufferedReader clientInput;
    private PrintWriter clientOutput;
    private PrintWriter output;
    private String request;
    private Socket clientSocket;

    public HttpServer(ServerSocket serverSocket, PrintWriter output) {
        this.serverSocket = serverSocket;
        this.output = output;
    }

    public void start() {
        while (true) {
            communicate();
        }
    }

    public void communicate() {
        try {
            clientSocket = serverSocket.accept();
            parseRequestFrom(clientSocket);
            clientOutput.printf(sendResponse());
            clientSocket.close();
        } catch (IOException ex) {
            output.println(ex);
        }
    }

    public String parseRequestFrom(Socket clientSocket) {
        setUpIOStreams(clientSocket);
        try {
            request = clientInput.readLine();
        } catch (IOException ex) {
            output.println(ex);
        }
        return request;
    }

    public String sendResponse() {
        Routes routes = new Routes();
        Response response = new Response();
        if (routes.isValidRoute(request)) {
            return response.simpleGet();
        } else {
            return "invalid request";
        }
    }

    private void setUpIOStreams(Socket clientSocket) {
        try {
            clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);
            output.println("IO streams created");
        } catch (IOException ex) {
            output.println(ex);
        }
    }
}