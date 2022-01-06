package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model.Job;
import com.google.gson.Gson;
import p.lodz.pl.pas.services.Const;
import p.lodz.pl.pas.services.JobService;
import sample.web.jsf.jsfexamples.utils.ContextUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Ziarno używane w przykładach dot. ziaren i atrybutów w poszczególnych zasięgach.
 * @author java
 */

@Named
@SessionScoped
public class JobBean implements Serializable {

    private List<Job> jobList;

    /**
     * Creates a new instance of JobBean
     */
    public JobBean() {
    }

    @PostConstruct
    public void init() {


    }
    
    /**
     * Zamyka sesję HTTP (odwołanie do klasy narzędziowej ContextUtils).
     */
    public void closeSession() {
        ContextUtils.invalidateSession();
    }

    /** Wstrzyknięcie obiektu konwersacji, za pomocą którego 
    można zarządzać rozpoczęciem i zakończeniem konwersacji.
    */
    @Inject
    private Conversation conversation;

    /** Zwraca bieżący stan konwersacji, wartość ta jest używana do 
     * włączania/wyłączania kontrolki (przycisku) na stronie. */
    public boolean isConversationInProgress() {
        return !conversation.isTransient();
    }
    
    public void beginConversation() {
        if (conversation.isTransient()) conversation.begin();
    }
    
    public void endConversation(){
        if (!conversation.isTransient()) conversation.end();
    }



    public void getAllJobs() {
        URL url = null;
        ArrayList<Job> jobList = null;
        HttpURLConnection con = null;
        try {
            url = new URL(Const.MAIN_URL + "api/user/list");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(con.getInputStream());
            jobList = gson.fromJson(reader, (Type) Job.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) con.disconnect();

        }
    }



    /** 
     * Zwraca wartość parametru application.description zgodnie z wartością parametru skonfigurowanego w deskryptorze web.xml. 
     */
    public String getApplicationDescriptionParam() {
        return ContextUtils.getContextParameter("application.description");
    }
}
