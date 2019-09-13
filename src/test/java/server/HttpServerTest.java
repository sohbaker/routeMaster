package server;

import org.junit.*;
import java.io.*;
import java.net.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class HttpServerTest {
    private HttpServer server;
    private PrintWriter serverMessages = new PrintWriter(new StringWriter(), true);
    private MockClientSocketCreator mockClientSocketCreator = new MockClientSocketCreator();
    private Socket mockClientSocket;
    private ServerSocket mockServerSocket;

    @Test
    public void sendsEmptyResponseWithStatusCode200ForSimpleGetRequest() throws IOException {
        mockClientSocket = mockClientSocketCreator.createWithInput("GET /simple_get HTTP/1.1");
        mockServerSocket = new MockServerSocket(mockClientSocket);
        server = new HttpServer(mockServerSocket, serverMessages);

        server.communicate();
        assertThat(mockClientSocket.getOutputStream().toString(), containsString("200"));
    }

    @Test
    public void sends404ResponseForNotFoundRequest() throws IOException {
        mockClientSocket = mockClientSocketCreator.createWithInput("GET /not_found_resource HTTP/1.1");
        mockServerSocket = new MockServerSocket(mockClientSocket);
        server = new HttpServer(mockServerSocket, serverMessages);

        server.communicate();
        assertThat(mockClientSocket.getOutputStream().toString(), containsString("404"));
    }

    @Test
    public void sendsEchoedResponseWithStatusCode200ForSimplePostRequest() throws IOException {
        mockClientSocket = mockClientSocketCreator.createWithInput("POST /echo_body HTTP/1.1\r\nContent Length: 5\r\n\r\nhello");
        mockServerSocket = new MockServerSocket(mockClientSocket);
        server = new HttpServer(mockServerSocket, serverMessages);

        server.communicate();

        assertThat(mockClientSocket.getOutputStream().toString(), containsString("200"));
        assertThat(mockClientSocket.getOutputStream().toString(), containsString("hello"));
    }
}