public class Routes {
    public boolean isGetRequest(String request) {
        return request.contains("GET /simple_get HTTP/1.1");
    }

    public boolean isNotFound(String request) {
        return request.contains("GET /not_found_resource HTTP/1.1");
    }

    public boolean isPostRequest(String request) {
        return request.contains("POST /echo_body HTTP/1.1");
    }
}