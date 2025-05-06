package dao;

import model.Visite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisiteDAO {

    // Associe chaque nom d'animal à ses visites
    private static Map<String, List<Visite>> historique = new HashMap<>();

    public static boolean ajouterVisite(String nomAnimal, Visite visite) {
        historique.computeIfAbsent(nomAnimal, k -> new ArrayList<>()).add(visite);
        return true;  // Correction: retourner true pour indiquer que l'ajout a réussi
    }

    public static List<Visite> getVisitesPourAnimal(String nomAnimal) {
        return historique.getOrDefault(nomAnimal, new ArrayList<>());
    }
}