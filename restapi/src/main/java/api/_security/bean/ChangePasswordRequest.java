package api._security.bean;

public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String newPassword2;

    public String getOldPassword() {
        return oldPassword;
    }

    public ChangePasswordRequest setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public ChangePasswordRequest setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public ChangePasswordRequest setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
        return this;
    }
}
