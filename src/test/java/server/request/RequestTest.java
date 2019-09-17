package server.request;

import org.junit.*;

import static org.junit.Assert.*;

public class RequestTest {
    private Request request;

    @Before
    public void setUpFake() {
        String fakeRequest = "GET /simple_get HTTP/1.1\r\nHost: localhost:3000\r\n\r\nbody text";
        request = new Request().extractDetails(fakeRequest);
    }

    @Test
    public void returnsTheRequestMethod() {
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void returnsTheRequestPath() {
        assertEquals("/simple_get", request.getPath());
    }

    @Test
    public void returnsTheRequestHost() {
        assertEquals("localhost:3000", request.getHost());
    }

    @Test
    public void returnsTheRequestBody() {
        assertEquals("body text", request.getBody());
    }
}