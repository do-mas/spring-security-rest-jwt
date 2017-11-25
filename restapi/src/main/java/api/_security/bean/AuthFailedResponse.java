package api._security.bean;

public class AuthFailedResponse {

    private String status;
    private String message;

    public AuthFailedResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public AuthFailedResponse() {
    }

    public String getStatus() {
        return status;
    }

    public AuthFailedResponse setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AuthFailedResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
