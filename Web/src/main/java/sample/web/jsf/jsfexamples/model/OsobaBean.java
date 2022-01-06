/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.web.jsf.jsfexamples.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mak
 */
public class OsobaBean implements Serializable {
    
    public static List<OsobaBean> pobierzListeOsob() {
        List<OsobaBean> osoby = new ArrayList<OsobaBean>();
        osoby.add(new OsobaBean("John", "Doe", Plec.M));
        osoby.add(new OsobaBean("Jane", "Babbs", Plec.K));
        osoby.add(new OsobaBean("Foo", "Bar", Plec.M));
        return osoby;
    }

    private String imie;
    private String nazwisko;
    private Plec plec;
    private boolean aktywna = true;

    public OsobaBean(String imie, String nazwisko, Plec plec) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.plec = plec;
    }
    
    @Override
    public String toString() {
        return "OsobaBean{" + "imie=" + imie + ", nazwisko=" + nazwisko + ", plec=" + plec + ", aktywna=" + aktywna + '}';
    }

    public String getImieNazwisko() {
        return imie + " " + nazwisko;
    }

    
    
    /**
     * Get the value of aktywna
     *
     * @return the value of aktywna
     */
    public boolean isAktywna() {
        return aktywna;
    }

    /**
     * Set the value of aktywna
     *
     * @param aktywna new value of aktywna
     */
    public void setAktywna(boolean aktywna) {
        this.aktywna = aktywna;
    }

    /**
     * Get the value of plec
     *
     * @return the value of plec
     */
    public Plec getPlec() {
        return plec;
    }

    /**
     * Set the value of plec
     *
     * @param plec new value of plec
     */
    public void setPlec(Plec plec) {
        this.plec = plec;
    }

    /**
     * Get the value of nazwisko
     *
     * @return the value of nazwisko
     */
    public String getNazwisko() {
        return nazwisko;
    }

    /**
     * Set the value of nazwisko
     *
     * @param nazwisko new value of nazwisko
     */
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    /**
     * Get the value of imie
     *
     * @return the value of imie
     */
    public String getImie() {
        return imie;
    }

    /**
     * Set the value of imie
     *
     * @param imie new value of imie
     */
    public void setImie(String imie) {
        this.imie = imie;
    }
}
