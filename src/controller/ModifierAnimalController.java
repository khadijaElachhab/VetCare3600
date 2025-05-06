package controller;

import dao.AnimalDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Animal;

/**
 * Contrôleur pour la modification d'un animal
 */
public class ModifierAnimalController {

    @FXML
    private TextField fieldNom;

    @FXML
    private TextField fieldAge;

    @FXML
    private ComboBox<String> comboType;

    private Animal animal;
    private Stage dialogStage;
    private String nomProprietaire;

    // Ajouter un callback
    private AnimalModificationCallback callback;

    // Attributs pour garder les valeurs originales
    private String originalNom;
    private String originalAge;
    private String originalType;

    /**
     * Constructeur par défaut requis par JavaFX
     */
    public ModifierAnimalController() {
        // Constructeur vide
    }

    /**
     * Initialisation des composants
     * Cette méthode est appelée automatiquement après le chargement du FXML
     */
    @FXML
    private void initialize() {
        System.out.println("ModifierAnimalController initialisé");
        // Limiter l'entrée dans le champ âge aux chiffres uniquement
        fieldAge.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });
    }

    /**
     * Définit le callback pour notifier de la mise à jour
     * @param callback Le callback à appeler après la mise à jour
     */
    public void setCallback(AnimalModificationCallback callback) {
        this.callback = callback;
    }

    /**
     * Initialise les données de l'animal à modifier
     * @param animal L'animal à modifier
     */
    public void setAnimal(Animal animal) {
        this.animal = animal;

        // Sauvegarder les valeurs originales
        this.originalNom = animal.getNom();
        this.originalAge = animal.getAge();
        this.originalType = animal.getType();

        // Remplir les champs avec les données existantes
        if (fieldNom != null) {
            fieldNom.setText(animal.getNom());
        } else {
            System.err.println("fieldNom est null");
        }

        if (fieldAge != null) {
            fieldAge.setText(animal.getAge());
        } else {
            System.err.println("fieldAge est null");
        }

        if (comboType != null) {
            comboType.setValue(animal.getType());
        } else {
            System.err.println("comboType est null");
        }

        // Pour debug
        System.out.println("Données animal à modifier: " + animal.getNom() + ", "
                + animal.getAge() + ", " + animal.getType());
    }

    /**
     * Définit le nom du propriétaire de l'animal
     * @param nomProprietaire Le nom du propriétaire
     */
    public void setNomProprietaire(String nomProprietaire) {
        this.nomProprietaire = nomProprietaire;
    }

    /**
     * Définit la fenêtre de dialogue
     * @param dialogStage La fenêtre de dialogue
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Enregistre les modifications de l'animal
     */
    @FXML
    private void enregistrerModification() {
        if (isInputValid()) {
            // Récupérer les nouvelles valeurs
            String nouveauNom = fieldNom.getText();
            String nouvelAge = fieldAge.getText();
            String nouveauType = comboType.getValue();

            System.out.println("Nouvelles valeurs saisies: " + nouveauNom + ", "
                    + nouvelAge + ", " + nouveauType);

            // Créer un nouvel objet Animal avec les valeurs modifiées
            Animal animalModifie = new Animal(nouveauNom, nouveauType, nouvelAge);

            // Vérifier si le propriétaire est défini
            if (nomProprietaire == null || nomProprietaire.isEmpty()) {
                // Essayer de récupérer le nom du propriétaire à partir du contexte parent
                try {
                    // Si nous sommes appelés depuis InfosProprietaireController,
                    // on peut récupérer le nom du propriétaire
                    Stage stage = (Stage) fieldNom.getScene().getWindow();
                    Object userData = stage.getUserData();
                    if (userData instanceof String) {
                        nomProprietaire = (String) userData;
                    }
                } catch (Exception e) {
                    System.err.println("Impossible de récupérer le nom du propriétaire: " + e.getMessage());
                }
            }

            // Si on n'a toujours pas le nom du propriétaire, on affiche une erreur
            if (nomProprietaire == null || nomProprietaire.isEmpty()) {
                showAlert("Erreur", "Nom du propriétaire manquant",
                        "Impossible de modifier l'animal: le nom du propriétaire est introuvable.");
                return;
            }

            // Enregistrer les modifications avec le DAO
            boolean success = AnimalDAO.modifierAnimal(nomProprietaire, animal, animalModifie);

            if (success) {
                // Appeler le callback avant de fermer la fenêtre
                if (callback != null) {
                    callback.onAnimalModified(animal, animalModifie);
                }

                // Afficher un message de succès
                showAlert("Succès", "Animal modifié",
                        "L'animal a été modifié avec succès.");

                // Fermer la fenêtre
                closeWindow();
            } else {
                // Afficher un message d'erreur
                showAlert("Erreur", "Échec de modification",
                        "Impossible de modifier l'animal dans le fichier.");
            }
        }
    }

    /**
     * Vérifie si les entrées sont valides
     * @return true si les entrées sont valides, false sinon
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (fieldNom.getText() == null || fieldNom.getText().isEmpty()) {
            errorMessage += "Le nom est obligatoire!\n";
        }

        if (fieldAge.getText() == null || fieldAge.getText().isEmpty()) {
            errorMessage += "L'âge est obligatoire!\n";
        }

        if (comboType.getValue() == null || comboType.getValue().isEmpty()) {
            errorMessage += "Le type est obligatoire!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Afficher un message d'erreur
            showAlert("Champs invalides", "Veuillez corriger les champs invalides", errorMessage);
            return false;
        }
    }

    /**
     * Affiche une alerte avec le titre, l'en-tête et le message spécifiés
     */
    private void showAlert(String title, String header, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Annule la modification et ferme la fenêtre
     */
    @FXML
    private void annuler() {
        closeWindow();
    }

    /**
     * Ferme la fenêtre de dialogue
     */
    private void closeWindow() {
        if (dialogStage != null) {
            // Si dialogStage a été correctement initialisé
            dialogStage.close();
        } else {
            try {
                // Solution alternative: obtenir la fenêtre à partir d'un élément de l'interface
                Stage stage = (Stage) fieldNom.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                System.err.println("Impossible de fermer la fenêtre: " + e.getMessage());
            }
        }
    }

    // Getters pour les valeurs originales
    public String getOriginalNom() {
        return originalNom;
    }

    public String getOriginalAge() {
        return originalAge;
    }

    public String getOriginalType() {
        return originalType;
    }
}