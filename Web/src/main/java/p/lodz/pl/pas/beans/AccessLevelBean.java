package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.AccessLevel;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class AccessLevelBean implements Serializable {


    private AccessLevel accessLevel;


    public AccessLevelBean() {
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public AccessLevel[] getAccessLevelsList() {
        return AccessLevel.values();
    }
}

