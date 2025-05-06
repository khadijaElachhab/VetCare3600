package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Proprietaire {

    private StringProperty nom;
    private StringProperty prenom;
    private StringProperty email;
    private StringProperty telephone;

    // Liste des animaux associés à ce propriétaire
    private List<Animal> animaux = new ArrayList<>();

    // Constructeur par défaut
    public Proprietaire() {
        this.prenom = new SimpleStringProperty("");
        this.nom = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.telephone = new SimpleStringProperty("");
    }

    // Constructeur avec les nouveaux champs
    public Proprietaire(String nom, String prenom, String email, String telephone) {
        this.prenom = new SimpleStringProperty(prenom);
        this.nom = new SimpleStringProperty(nom);
        this.email = new SimpleStringProperty(email != null ? email : "");
        this.telephone = new SimpleStringProperty(telephone != null ? telephone : "");
    }

    // Constructeur complet pour compatibilité avec le code existant
    public Proprietaire(String nom, String prenom, String email, String telephone, String adresse, String ville) {
        this.prenom = new SimpleStringProperty(prenom);
        this.nom = new SimpleStringProperty(nom);
        this.email = new SimpleStringProperty(email != null ? email : "");
        this.telephone = new SimpleStringProperty(telephone != null ? telephone : "");
    }

    // Constructeurs hérités pour la compatibilité
    public Proprietaire(String nom, String prenom, String adresse) {
        this.prenom = new SimpleStringProperty(prenom);
        this.nom = new SimpleStringProperty(nom);
        this.email = new SimpleStringProperty("");
        this.telephone = new SimpleStringProperty("");
    }

    public Proprietaire(String nom, String prenom, String adresse, String ville, String telephone) {
        this.prenom = new SimpleStringProperty(prenom);
        this.nom = new SimpleStringProperty(nom);
        this.email = new SimpleStringProperty("");
        this.telephone = new SimpleStringProperty(telephone != null ? telephone : "");

    }

    // Accesseurs pour les propriétés
    public String getPrenom() {
        return prenom.get();
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public StringProperty prenomProperty() {
        return prenom;
    }
    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getTelephone() {
        return telephone.get();
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public StringProperty telephoneProperty() {
        return telephone;
    }



    // Gestion des animaux
    public List<Animal> getAnimaux() {
        return animaux;
    }

    public void setAnimaux(List<Animal> animaux) {
        if (animaux != null) {
            this.animaux = animaux;
        }
    }

    public void ajouterAnimal(Animal a) {
        if (a != null) {
            animaux.add(a);
        }
    }

    @Override
    public String toString() {
        return nom.get() + " " + prenom.get();
    }
}