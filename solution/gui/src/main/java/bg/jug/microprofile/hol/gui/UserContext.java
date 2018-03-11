package bg.jug.microprofile.hol.gui;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@SessionScoped
public class UserContext implements Serializable {

    private User loggedUser;
    private String userJWT;

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public String getUserJWT() {
        return userJWT;
    }

    public void setUserJWT(String userJWT) {
        this.userJWT = userJWT;
    }
}
