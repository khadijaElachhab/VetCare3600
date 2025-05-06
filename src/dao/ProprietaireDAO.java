package dao;

import model.Proprietaire;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProprietaireDAO {

    private static final String FICHIER_PROPRIETAIRES = "proprietaires.txt";
    private static List<Proprietaire> proprietaires = new ArrayList<>();

    /**
     * Charge les données des propriétaires depuis le fichier
     */
    public static void init() {
        proprietaires.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_PROPRIETAIRES))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(";");
                if (parts.length >= 3) {
                    // Format: nom;prenom;email;telephone
                    Proprietaire p = new Proprietaire();
                    p.setNom(parts[0]);
                    p.setPrenom(parts[1]);
                    p.setEmail(parts[2]);

                    // Gérer le champ téléphone (maintenant obligatoire)
                    if (parts.length > 3) {
                        p.setTelephone(parts[3]);
                    } else {
                        p.setTelephone(""); // Valeur par défaut pour éviter les null
                    }

                    proprietaires.add(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Fichier introuvable, une nouvelle base sera créée.");
        }
    }

    /**
     * Ajoute un propriétaire et l'enregistre dans le fichier
     *
     * @param p Le propriétaire à ajouter
     */
    public static void ajouterProprietaire(Proprietaire p) {
        proprietaires.add(p); // Ajouter en mémoire

        try (FileWriter fw = new FileWriter(FICHIER_PROPRIETAIRES, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            // Format: nom;prenom;email;telephone
            StringBuilder sb = new StringBuilder();
            sb.append(p.getNom()).append(";");
            sb.append(p.getPrenom()).append(";");
            sb.append(p.getEmail()).append(";");
            sb.append(p.getTelephone() != null ? p.getTelephone() : "");

            out.println(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recherche des propriétaires par terme de recherche (nom, prénom, email ou téléphone)
     *
     * @param terme Le terme à rechercher
     * @return Liste des propriétaires correspondants
     */
    public static List<Proprietaire> rechercher(String terme) {
        if (terme == null || terme.trim().isEmpty()) {
            return getAll();
        }

        String termeLower = terme.toLowerCase();

        return proprietaires.stream()
                .filter(p ->
                        (p.getNom() != null && p.getNom().toLowerCase().contains(termeLower)) ||
                                (p.getPrenom() != null && p.getPrenom().toLowerCase().contains(termeLower)) ||
                                (p.getEmail() != null && p.getEmail().toLowerCase().contains(termeLower)) ||
                                (p.getTelephone() != null && p.getTelephone().contains(termeLower))
                )
                .collect(Collectors.toList());
    }

    /**
     * Recherche des propriétaires par nom
     *
     * @param nom Le nom à rechercher
     * @return Liste des propriétaires correspondants
     */
    public static List<Proprietaire> rechercherParNom(String nom) {
        return proprietaires.stream()
                .filter(p -> p.getNom().toLowerCase().contains(nom.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Recherche un propriétaire par téléphone
     *
     * @param telephone Le numéro de téléphone à rechercher
     * @return Liste des propriétaires correspondants
     */
    public static List<Proprietaire> rechercherParTelephone(String telephone) {
        return proprietaires.stream()
                .filter(p -> p.getTelephone() != null && p.getTelephone().contains(telephone))
                .collect(Collectors.toList());
    }

    /**
     * Retourne tous les propriétaires
     *
     * @return Liste de tous les propriétaires
     */
    public static List<Proprietaire> getAll() {
        return new ArrayList<>(proprietaires);
    }

    /**
     * Supprime un propriétaire
     *
     * @param p Le propriétaire à supprimer
     * @return true si la suppression a réussi
     */
    public static boolean supprimerProprietaire(Proprietaire p) {
        boolean removed = proprietaires.remove(p);

        if (removed) {
            // Mettre à jour le fichier
            sauvegarderTout();
        }

        return removed;
    }

    /**
     * Met à jour un propriétaire
     *
     * @param proprietaire Le propriétaire avec les nouvelles données
     * @return true si la mise à jour a réussi
     */
    public static boolean updateProprietaire(Proprietaire proprietaire) {
        // Trouver l'index du propriétaire dans la liste
        int index = -1;
        for (int i = 0; i < proprietaires.size(); i++) {
            Proprietaire p = proprietaires.get(i);
            if (p == proprietaire) {  // On vérifie si c'est le même objet
                index = i;
                break;
            }
        }

        if (index != -1) {
            // Le propriétaire existe déjà en mémoire, pas besoin de le remplacer
            // car c'est le même objet qui a été modifié

            // Mettre à jour le fichier
            return sauvegarderTout();
        }

        return false;
    }

    /**
     * Méthode alternative de mise à jour qui prend l'ancien propriétaire et le nouveau
     *
     * @param originalProprietaire Le propriétaire original à modifier
     * @param nouveauProprietaire Le propriétaire avec les nouvelles données
     * @return true si la mise à jour a réussi
     */
    public static boolean updateProprietaire(Proprietaire originalProprietaire, Proprietaire nouveauProprietaire) {
        int index = proprietaires.indexOf(originalProprietaire);

        if (index != -1) {
            proprietaires.set(index, nouveauProprietaire);
            return sauvegarderTout();
        }

        return false;
    }

    /**
     * Sauvegarde tous les propriétaires dans le fichier
     *
     * @return true si la sauvegarde a réussi
     */
    private static boolean sauvegarderTout() {
        try {
            Path path = Paths.get(FICHIER_PROPRIETAIRES);
            List<String> lines = new ArrayList<>();

            for (Proprietaire p : proprietaires) {
                StringBuilder sb = new StringBuilder();
                sb.append(p.getNom()).append(";");
                sb.append(p.getPrenom()).append(";");
                sb.append(p.getEmail()).append(";");
                sb.append(p.getTelephone() != null ? p.getTelephone() : "");

                lines.add(sb.toString());
            }

            Files.write(path, lines);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}