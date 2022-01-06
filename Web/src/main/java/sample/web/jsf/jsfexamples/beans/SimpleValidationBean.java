package sample.web.jsf.jsfexamples.beans;


import java.util.Date;
import java.util.ResourceBundle;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Ziarno służące jako zasobnik danych dla przykładów walidacji.
 * @author java
 */
@Named
@RequestScoped
public class SimpleValidationBean {

    /**
     * Creates a new instance of SimpleValidationBean
     */
    public SimpleValidationBean() {
    }
    
    private String requiredField;

    public String getRequiredField() {
        return requiredField;
    }

    public void setRequiredField(String requiredField) {
        this.requiredField = requiredField;
    }
    
    private String minLengthField;

    public String getMinLengthField() {
        return minLengthField;
    }

    public void setMinLengthField(String minLengthField) {
        this.minLengthField = minLengthField;
    }
    
    private Date dateField;

    public Date getDateField() {
        return dateField;
    }

    public void setDateField(Date dateField) {
        this.dateField = dateField;
    }

    private int intField;

    public int getIntField() {
        return intField;
    }

    public void setIntField(int intField) {
        this.intField = intField;
    }

    private String loginField;

    public String getLoginField() {
        return loginField;
    }

    public void setLoginField(String loginField) {
        this.loginField = loginField;
    }
    private String emailField;

    public String getEmailField() {
        return emailField;
    }

    public void setEmailField(String emailField) {
        this.emailField = emailField;
    }
    private String passwordField;

    public String getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(String passwordField) {
        this.passwordField = passwordField;
    }

    private String passwordRepeat;

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String checkPasswordMatching(){
        if (!(passwordField.equals(passwordRepeat))) {
            FacesContext fctx = FacesContext.getCurrentInstance();
            
            //Skonstruowanie komunikatu na podstawie pliku zasobów
            ResourceBundle rbl = ResourceBundle.getBundle("i18n.messages");
            FacesMessage fmsg = new FacesMessage(rbl.getString("passwords.not.matching"));

            //Dodatnie komunikatu do domyślnego - "pustego" elementu h:message
            fctx.addMessage(null, fmsg);
        }
        return null;//powrót do formularza
    }
}
