package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.Job;
import p.lodz.pl.pas.services.JobService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Ziarno używane w przykładach dot. ziaren i atrybutów w poszczególnych zasięgach.
 * @author java
 */

@Named
@SessionScoped
public class JobListBean implements Serializable {

    @Inject
    JobService jobService;

    /**
     * Creates a new instance of JobBean
     */
    public JobListBean() {
    }

    public List<Job> getJobList() {
        return jobService.getAllJobs();
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
