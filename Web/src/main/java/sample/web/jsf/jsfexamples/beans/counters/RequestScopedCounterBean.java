
package sample.web.jsf.jsfexamples.beans.counters;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Klasa licznika oznaczona jako ziarno CDI o zasięgu żądania.
 * Ziarno zostało nazwane (@Named) aby można było odwołać się do niego z poziomu wyrażenia EL w stronie.
 * @author java
 */
@Named
@RequestScoped
public class RequestScopedCounterBean extends AbstractCounter {
    
}
