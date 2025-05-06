package dao;

import model.Veterinaire;

import java.util.ArrayList;
import java.util.List;

public class VeterinaireDAO {

    private static List<Veterinaire> veterinaires = new ArrayList<>();

    static {
        // Données simulées avec tous les champs nécessaires
        veterinaires.add(new Veterinaire("Camille", "Lemoine", "Chirurgie vétérinaire", "lemoine@gmail.com", "0123456789"));
        veterinaires.add(new Veterinaire("Julien", "Robert", "Dermatologie vétérinaire", "robert@gmail.com", "0987654321"));
        veterinaires.add(new Veterinaire("Mohamed", "Derkaoui", "Chirurgien vétérinaire", "Derkaoui@gmail.com", "0123456789"));
        veterinaires.add(new Veterinaire("Safaa", "Bennani", "Médecine vétérinaire", "Bennani@gmail.com", "0987654321"));
        veterinaires.add(new Veterinaire("Ali", "Lamrini", "Dermatologie vétérinaire", "Lamrini@gmail.com", "0147258369"));
    }

    public static List<Veterinaire> getAll() {
        return new ArrayList<>(veterinaires);
    }

    public static void add(Veterinaire veterinaire) {
        veterinaires.add(veterinaire);
    }

    public static void update(Veterinaire veterinaire, int index) {
        if (index >= 0 && index < veterinaires.size()) {
            veterinaires.set(index, veterinaire);
        }
    }

    public static void delete(int index) {
        if (index >= 0 && index < veterinaires.size()) {
            veterinaires.remove(index);
        }
    }
}