
package sample.web.jsf.jsfexamples.beans.counters;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Klasa licznika oznaczona jako ziarno CDI o zasięgu sesji.
 * Ziarno zostało nazwane (@Named) aby można było odwołać się do niego z poziomu wyrażenia EL w stronie.
 * Serializowalność jest wymagana dla ziaren CDI które mogą zostać deaktywowane (wszystkie zasięgi szersze niż żądania)
 * @author java
 */
@Named
@SessionScoped
public class SessionScopedCounterBean extends AbstractCounter implements Serializable{
    
}
