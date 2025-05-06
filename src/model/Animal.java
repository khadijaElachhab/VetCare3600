package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe représentant un animal
 */
public class Animal {
    private final StringProperty nom;
    private final StringProperty type;
    private final StringProperty age;

    /**
     * Constructeur
     * @param nom Le nom de l'animal
     * @param type Le type d'animal
     * @param age L'âge de l'animal
     */
    public Animal(String nom, String type, String age) {
        this.nom = new SimpleStringProperty(nom);
        this.type = new SimpleStringProperty(type);
        this.age = new SimpleStringProperty(age);
    }

    // Getters et setters pour les propriétés
    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public String getAge() {
        return age.get();
    }

    public void setAge(String age) {
        this.age.set(age);
    }

    public StringProperty ageProperty() {
        return age;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Animal other = (Animal) obj;
        return getNom().equals(other.getNom());
    }

    @Override
    public int hashCode() {
        return getNom().hashCode();
    }

    @Override
    public String toString() {
        return "Animal{" +
                "nom='" + getNom() + '\'' +
                ", type='" + getType() + '\'' +
                ", age='" + getAge() + '\'' +
                '}';
    }
}