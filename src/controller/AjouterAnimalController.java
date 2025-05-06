package controller;

import dao.AnimalDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Animal;
import model.Proprietaire;

public class AjouterAnimalController {
    @FXML
    private TextField fieldNom;

    @FXML
    private TextField fieldAge;

    @FXML
    private ComboBox<String> comboType;

    private Proprietaire proprietaire;
    private AnimalModificationCallback callback;

    // Chemin du fichier pour sauvegarder les animaux
    private static final String FILE_PATH = "animaux.txt";

    // Setter pour le callback
    public void setCallback(AnimalModificationCallback callback) {
        this.callback = callback;
    }

    // Initialisation du propriétaire depuis la page précédente
    public void setProprietaire(Proprietaire proprietaire) {
        this.proprietaire = proprietaire;
        if (proprietaire != null) {
            System.out.println("Propriétaire reçu pour ajout animal : " + proprietaire.getNom());
        } else {
            System.out.println("Propriétaire non initialisé !");
        }
    }

    @FXML
    private void initialize() {
        System.out.println("Formulaire Ajouter Animal initialisé !");

        // Limiter l'entrée dans le champ âge aux chiffres uniquement
        fieldAge.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });
    }

    @FXML
    private void ajouterAnimal() {
        if (proprietaire == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun propriétaire sélectionné !");
            return;
        }

        String nom = fieldNom.getText().trim();
        String age = fieldAge.getText().trim();
        String type = comboType.getValue();

        if (nom.isEmpty() || age.isEmpty() || type == null) {
            showAlert(Alert.AlertType.WARNING, "Champs manquants", "Tous les champs sont obligatoires !");
            return;
        }

        // Création et ajout de l'animal
        Animal animal = new Animal(nom, type, age);
        proprietaire.ajouterAnimal(animal);

        // Enregistrement dans le fichier via la DAO
        boolean succes = AnimalDAO.ajouterAnimal(proprietaire.getNom(), animal);

        if (succes) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Animal ajouté avec succès !");

            // Notification du changement via le callback
            if (callback != null) {
                callback.onAnimalAdded(animal);
            }

            // Fermer la fenêtre après validation
            Stage stage = (Stage) fieldNom.getScene().getWindow();
            stage.close();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur d'enregistrement",
                    "Impossible d'enregistrer l'animal dans le fichier.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static String getFilePath() {
        return FILE_PATH;
    }

    // Pour annuler et fermer la fenêtre
    @FXML
    private void annuler() {
        Stage stage = (Stage) fieldNom.getScene().getWindow();
        stage.close();
    }
}