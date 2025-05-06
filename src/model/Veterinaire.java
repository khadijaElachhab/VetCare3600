package model;

public class Veterinaire {
    private String prenom;
    private String nom;
    private String specialite;
    private String email;
    private String telephone;

    public Veterinaire() {
    }

    public Veterinaire(String prenom, String nom, String specialite, String email, String telephone) {
        this.prenom = prenom;
        this.nom = nom;
        this.specialite = specialite;
        this.email = email;
        this.telephone = telephone;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}