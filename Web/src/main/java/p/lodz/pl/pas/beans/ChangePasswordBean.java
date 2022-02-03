package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.services.ChangePasswordService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class ChangePasswordBean implements Serializable {


    @Inject
    ChangePasswordService changePasswordService;

    private String oldPassword;
    private String newPassword;


    public void changePassword() {
        changePasswordService.changePassword(oldPassword, newPassword);
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
