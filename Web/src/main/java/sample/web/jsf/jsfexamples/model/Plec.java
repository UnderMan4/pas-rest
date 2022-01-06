/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.web.jsf.jsfexamples.model;

/**
 *
 * @author mak
 */
public enum Plec {
    
    K("Kobieta"), M("Mężczyzna");
    
    private String nazwa;
    
    private Plec (String nazwa) {
        this.nazwa = nazwa;
    }
    
    public String toString() {
        return nazwa;
    }
    
}
