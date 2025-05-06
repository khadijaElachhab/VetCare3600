package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Visite {
    private StringProperty date;
    private StringProperty motif;
    private StringProperty nomVeto;

    public Visite(String date, String motif, String nomVeto) {
        this.date = new SimpleStringProperty(date);
        this.motif = new SimpleStringProperty(motif);
        this.nomVeto = new SimpleStringProperty(nomVeto);
    }

    public Visite() {

    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getMotif() {
        return motif.get();
    }

    public StringProperty motifProperty() {
        return motif;
    }

    public String getNomVeto() {
        return nomVeto.get();
    }

    public StringProperty nomVetoProperty() {
        return nomVeto;
    }
}
