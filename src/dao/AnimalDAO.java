package dao;

import model.Animal;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class AnimalDAO {

    private static final String FILE_PATH = "animaux.txt";

    // Map des animaux par nom de propriétaire (ex: "Khadija" → liste de ses animaux)
    private static final Map<String, List<Animal>> animauxParProprietaire = new HashMap<>();

    // Initialisation - charge les données du fichier s'il existe
    static {
        chargerDepuisFichier();
    }

    // Ajouter un animal pour un propriétaire
    public static boolean ajouterAnimal(String nomProprietaire, Animal animal) {
        // Ajouter en mémoire
        animauxParProprietaire
                .computeIfAbsent(nomProprietaire, k -> new ArrayList<>())
                .add(animal);

        // Persister dans le fichier
        return sauvegarderDansFichier(nomProprietaire, animal);
    }

    // Sauvegarder un animal dans le fichier
    private static boolean sauvegarderDansFichier(String nomProprietaire, Animal animal) {
        try {
            // Format: proprietaire,nomAnimal,ageAnimal,typeAnimal
            String ligne = nomProprietaire + "," +
                    animal.getNom() + "," +
                    animal.getAge() + "," +
                    animal.getType() + "\n";

            // Créer le fichier s'il n'existe pas, sinon ajouter à la fin
            if (!Files.exists(Paths.get(FILE_PATH))) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                    writer.write(ligne);
                }
            } else {
                Files.write(Paths.get(FILE_PATH), ligne.getBytes(), StandardOpenOption.APPEND);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Charger tous les animaux depuis le fichier
    private static void chargerDepuisFichier() {
        try {
            if (Files.exists(Paths.get(FILE_PATH))) {
                List<String> lignes = Files.readAllLines(Paths.get(FILE_PATH));

                // Vider la structure en mémoire avant de charger
                animauxParProprietaire.clear();

                for (String ligne : lignes) {
                    String[] parts = ligne.split(",");
                    if (parts.length == 4) {
                        String nomProprietaire = parts[0];
                        String nomAnimal = parts[1];
                        String ageAnimal = parts[2];
                        String typeAnimal = parts[3];

                        Animal animal = new Animal(nomAnimal, ageAnimal, typeAnimal);

                        // Ajouter en mémoire
                        animauxParProprietaire
                                .computeIfAbsent(nomProprietaire, k -> new ArrayList<>())
                                .add(animal);
                    }
                }
                System.out.println("Animaux chargés depuis le fichier: " + getTousLesAnimaux().size());
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des animaux: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Réécrire tout le fichier (utilisé après suppression ou modification)
    private static boolean reécrireFichier() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, List<Animal>> entry : animauxParProprietaire.entrySet()) {
                String nomProprietaire = entry.getKey();
                List<Animal> animaux = entry.getValue();

                for (Animal animal : animaux) {
                    String ligne = nomProprietaire + "," +
                            animal.getNom() + "," +
                            animal.getAge() + "," +
                            animal.getType() + "\n";
                    writer.write(ligne);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer les animaux d'un propriétaire
    public static List<Animal> getAnimauxPourProprietaire(String nomProprietaire) {
        return new ArrayList<>(animauxParProprietaire.getOrDefault(nomProprietaire, new ArrayList<>()));
    }

    // Supprimer un animal
    public static boolean supprimerAnimal(String nomProprietaire, Animal animal) {
        List<Animal> animaux = animauxParProprietaire.get(nomProprietaire);
        if (animaux != null) {
            boolean supprime = animaux.remove(animal);
            if (supprime) {
                // Mettre à jour le fichier après suppression
                return reécrireFichier();
            }
        }
        return false;
    }

    // Mettre à jour un animal
    public static boolean modifierAnimal(String nomProprietaire, Animal ancien, Animal nouveau) {
        List<Animal> animaux = animauxParProprietaire.get(nomProprietaire);
        if (animaux != null) {
            int index = animaux.indexOf(ancien);
            if (index != -1) {
                animaux.set(index, nouveau);
                // Mettre à jour le fichier après modification
                return reécrireFichier();
            }
        }
        return false;
    }

    // Retourner tous les animaux
    public static List<Animal> getTousLesAnimaux() {
        return animauxParProprietaire.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    // Rafraîchir les données depuis le fichier
    public static void recharger() {
        chargerDepuisFichier();
    }

    // Obtenir le chemin du fichier
    public static String getFilePath() {
        return FILE_PATH;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(animal.getNom(), animal.getNom());
    }


}