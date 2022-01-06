package sample.web.jsf.jsfexamples.beans.counters;

import java.io.Serializable;


/**
 * Klasa abstrakcyjna, dziedziczona przez ziarna CDI o różnych zasięgach (scope).
 * Klasa implementuje prosty licznik wywołań, który powinien dawać różne wartości w zależności od zasięgu ziarna.
 * @author java
 */
//
public abstract class AbstractCounter {

    public AbstractCounter() {
    }

    protected int count = 0;

    public int getCount() {
        return count;
    }

    public void incrementCount(int factor) {
        this.count += factor;
    }

    public void incrementCount() {
        this.incrementCount(1);
    }
    
    @Override
    public String toString(){
        return (super.toString() + " : Stan licznika: " + count);
    }

}
