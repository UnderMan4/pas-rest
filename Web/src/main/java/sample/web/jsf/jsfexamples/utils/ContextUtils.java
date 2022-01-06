
package sample.web.jsf.jsfexamples.utils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Przykład sposobu uzyskiwania dostępu z poziomu ziarna zarządzanego przez JSF do:
 * - atrybutów żądania, sesji i aplikacji 
 * - parametrów konfiguracyjnych zdefiniowanych w deskryptorze wdrożenia web.xml
 * - zakończenia sesji (metoda invalidate())
 * UWAGA! Nie jest to ziarno, lecz zwykła klasa narzędziowa. Metody tej klasy muszą być jednak wywoływane w kontekście żądania obsługiwanego przez JSF, inaczej 
 * nie uzyska ona kontekstu JSF (FacesContext).
 * @author java
 */
//@ManagedBean
//@RequestScoped
public class ContextUtils {

    /**
     * Creates a new instance of AttributesUtils
     */
    public ContextUtils() {
    }
    
    /** 
     * Zwraca obiekt FacesContext - kontekst serwletu FacesServlet
     */
    public static ExternalContext getContext(){
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    /** 
     * Wyszukuje atrybut o zadanej nazwie w kontekście aplikacji
     */
    public static Object getApplicationAttribute(String attributeName){
        return getContext().getApplicationMap().get(attributeName);
    }

    /** 
     * Wyszukuje atrybut o zadanej nazwie w kontekście sesji
     */
    public static Object getSessionAttribute(String attributeName){
        return getContext().getSessionMap().get(attributeName);
    }

    /** 
     * Wyszukuje atrybut o zadanej nazwie w kontekście żądania
     */
    public static Object getRequestAttribute(String attributeName){
        return getContext().getRequestMap().get(attributeName);
    }

    /** 
     * Wyszukuje parametr inicjalizacyjny o zadanej nazwie
     */
    public static String getContextParameter(String paramName){
        return getContext().getInitParameter(paramName);
    }
    
    /** 
     * Dokonuje zamknięcia bieżącej sesji
     */
    public static void invalidateSession(){
        getContext().invalidateSession();
    }

    /** 
     * Zwraca identyfikator bieżącej sesji
     */
    public static String getSessionID(){
        HttpSession session = (HttpSession) getContext().getSession(true);
        return session.getId();
    }

}
