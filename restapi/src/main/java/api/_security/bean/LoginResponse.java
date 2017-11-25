package api._security.bean;

public class LoginResponse {

    private String token;
    private User user;

    public User getUser() {
        return user;
    }

    public LoginResponse(String token, User user) {
        this.user = user;
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

}