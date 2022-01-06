package sample.web.jsf.jsfexamples.beans;

import java.util.Date;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sample.web.jsf.jsfexamples.beans.counters.ApplicationScopedCounterBean;
import sample.web.jsf.jsfexamples.beans.counters.ConversationScopedCounterBean;
import sample.web.jsf.jsfexamples.beans.counters.RequestScopedCounterBean;
import sample.web.jsf.jsfexamples.beans.counters.SessionScopedCounterBean;
import sample.web.jsf.jsfexamples.utils.ContextUtils;

/**
 * Ziarno używane w przykładach dot. ziaren i atrybutów w poszczególnych zasięgach.
 * @author java
 */

@Named
@RequestScoped
public class BeansBean {

    /**
     * Creates a new instance of BeansBean
     */
    public BeansBean() {
    }
    
    /**
     * Zamyka sesję HTTP (odwołanie do klasy narzędziowej ContextUtils).
     */
    public void closeSession() {
        ContextUtils.invalidateSession();
    }

    /**
     * Zwraca identyfikator sesji HTTP (odwołanie do klasy narzędziowej ContextUtils).
     */
    public String getSessionID() {
        return ContextUtils.getSessionID();
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


    /** Wstrzyknięcia ziaren o poszczególnych zasięgach.
    Referencje są prywatne i nie są dla nich zdefiniowane akcesory (get, set)
    ponieważ z poziomu kodu strony odwołujemy się do nich bezpośrednio 
    jako do ziaren nazwanych (@Named). Z referencji tych korzysta wyłącznie metoda
    getCountersDescription().
    */

    @Inject
    private RequestScopedCounterBean requestScopedCounterBean;
    
    @Inject
    private SessionScopedCounterBean sessionScopedCounterBean;
    
    @Inject
    private ApplicationScopedCounterBean applicationScopedCounterBean;
    
    @Inject
    private ConversationScopedCounterBean conversationScopedCounterBean;
    
    /** 
     * Zwraca opisy liczników (wynik wywołania metody toString()) we wszystkich zasięgach. 
     */
    public String getCountersDescription() {
        StringBuilder sb = new StringBuilder(new Date().toString()).append("\n");
        sb.append("Licznik w zasięgu żądania: ").append(requestScopedCounterBean).append("\n");
        sb.append("Licznik w zasięgu sesji: ").append(sessionScopedCounterBean).append("\n");
        sb.append("Licznik w zasięgu aplikacji: ").append(applicationScopedCounterBean).append("\n");
        sb.append("Licznik w zasięgu konwersacji: ").append(conversationScopedCounterBean).append("\n");
        sb.append("Obiekt konwersacji: ").append(conversation).append("\n");
        return sb.toString();
    }

    /** 
     * Zwraca wartość parametru application.description zgodnie z wartością parametru skonfigurowanego w deskryptorze web.xml. 
     */
    public String getApplicationDescriptionParam() {
        return ContextUtils.getContextParameter("application.description");
    }
}
