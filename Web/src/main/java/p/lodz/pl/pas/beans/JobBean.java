package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model.Job;
import p.lodz.pl.pas.services.Const;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;

/**
 * Ziarno używane w przykładach dot. ziaren i atrybutów w poszczególnych zasięgach.
 * @author java
 */

@Named
@SessionScoped
public class JobBean implements Serializable {

    /**
     * Creates a new instance of JobBean
     */
    public JobBean() {
    }

    public List<Job> getJobList() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Const.MAIN_URL);
        return target.path("api").path("job").path("list").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Job>>() {
        });
    }

//    /**
//     * Zamyka sesję HTTP (odwołanie do klasy narzędziowej ContextUtils).
//     */
//    public void closeSession() {
//        ContextUtils.invalidateSession();
//    }
//
//    /** Wstrzyknięcie obiektu konwersacji, za pomocą którego
//    można zarządzać rozpoczęciem i zakończeniem konwersacji.
//    */
//    @Inject
//    private Conversation conversation;
//
//    /** Zwraca bieżący stan konwersacji, wartość ta jest używana do
//     * włączania/wyłączania kontrolki (przycisku) na stronie. */
//    public boolean isConversationInProgress() {
//        return !conversation.isTransient();
//    }
//
//    public void beginConversation() {
//        if (conversation.isTransient()) conversation.begin();
//    }
//
//    public void endConversation() {
//        if (!conversation.isTransient()) conversation.end();
//    }


//    /**
//     * Zwraca wartość parametru application.description zgodnie z wartością parametru skonfigurowanego w deskryptorze web.xml.
//     */
//    public String getApplicationDescriptionParam() {
//        return ContextUtils.getContextParameter("application.description");
//    }
}
