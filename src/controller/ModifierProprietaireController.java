package controller;

import dao.ProprietaireDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Proprietaire;

public class ModifierProprietaireController {

    @FXML
    private TextField fieldPrenom;
    @FXML
    private TextField fieldNom;

    @FXML
    private TextField fieldEmail;

    @FXML
    private TextField fieldTelephone;

    private Proprietaire proprietaire;
    private Stage dialogStage;

    // Ajouter un callback
    private ProprietaireModificationCallback callback;

    // Attributs pour garder les valeurs originales (utiles pour les mises à jour)
    private String originalNom;
    private String originalPrenom;
    private String originalEmail;
    private String originalTelephone;

    // Ajout d'un constructeur par défaut pour JavaFX
    public ModifierProprietaireController() {
        // Constructeur vide requis par JavaFX
    }

    // Conserver le constructeur avec paramètre si nécessaire
    public ModifierProprietaireController(String originalTelephone) {
        this.originalTelephone = originalTelephone;
    }

    /**
     * Définit le callback pour notifier de la mise à jour
     * @param callback Le callback à appeler après la mise à jour
     */
    public void setCallback(ProprietaireModificationCallback callback) {
        this.callback = callback;
    }

    /**
     * Initialise les données du propriétaire à modifier
     * @param proprietaire Le propriétaire à modifier
     */
    public void setProprietaire(Proprietaire proprietaire) {
        this.proprietaire = proprietaire;

        // Sauvegarder les valeurs originales
        this.originalNom = proprietaire.getNom();
        this.originalPrenom = proprietaire.getPrenom();
        this.originalEmail = proprietaire.getEmail();
        this.originalTelephone = proprietaire.getTelephone();

        // Remplir les champs avec les données existantes
        fieldNom.setText(proprietaire.getNom());
        fieldPrenom.setText(proprietaire.getPrenom());
        fieldEmail.setText(proprietaire.getEmail());

        // Afficher le téléphone s'il existe
        if (proprietaire.getTelephone() != null) {
            fieldTelephone.setText(proprietaire.getTelephone());
        }
    }

    /**
     * Définit la fenêtre de dialogue
     * @param dialogStage La fenêtre de dialogue
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Enregistre les modifications du propriétaire
     */
    @FXML
    private void enregistrerModification() {
        if (isInputValid()) {
            // Mise à jour des données du propriétaire
            proprietaire.setNom(fieldNom.getText());
            proprietaire.setPrenom(fieldPrenom.getText());
            proprietaire.setEmail(fieldEmail.getText());
            proprietaire.setTelephone(fieldTelephone.getText());

            // Enregistrer les modifications avec le DAO
            boolean success = ProprietaireDAO.updateProprietaire(proprietaire);

            if (success) {
                // Appeler le callback avant de fermer la fenêtre
                if (callback != null) {
                    callback.onProprietaireUpdated(proprietaire);
                }

                // Fermer la fenêtre en sécurité
                if (dialogStage != null) {
                    // Si dialogStage a été correctement initialisé
                    dialogStage.close();
                } else {
                    // Solution alternative: obtenir la fenêtre à partir d'un élément de l'interface
                    Stage stage = (Stage) fieldNom.getScene().getWindow();
                    stage.close();
                }
            } else {
                // Afficher un message d'erreur
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur de sauvegarde");
                alert.setHeaderText("Erreur lors de la sauvegarde");
                alert.setContentText("Impossible de mettre à jour le propriétaire.");
                alert.showAndWait();
            }
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (fieldNom.getText() == null || fieldNom.getText().isEmpty()) {
            errorMessage += "Le nom est obligatoire!\n";
        }

        if (fieldPrenom.getText() == null || fieldPrenom.getText().isEmpty()) {
            errorMessage += "Le prénom est obligatoire!\n";
        }

        if (fieldEmail.getText() == null || fieldEmail.getText().isEmpty()) {
            errorMessage += "L'adresse email est obligatoire!\n";
        }

        // Rendre le téléphone obligatoire
        if (fieldTelephone.getText() == null || fieldTelephone.getText().isEmpty()) {
            errorMessage += "Le numéro de téléphone est obligatoire!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Afficher un message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Champs invalides");
            alert.setHeaderText("Veuillez corriger les champs invalides");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

    /**
     * Annule la modification et ferme la fenêtre
     */
    @FXML
    private void annuler() {
        if (dialogStage != null) {
            // Si dialogStage a été correctement initialisé
            dialogStage.close();
        } else {
            // Solution alternative: obtenir la fenêtre à partir d'un élément de l'interface
            Stage stage = (Stage) fieldNom.getScene().getWindow();
            stage.close();
        }
    }

    public String getOriginalNom() {
        return originalNom;
    }

    public void setOriginalNom(String originalNom) {
        this.originalNom = originalNom;
    }

    public String getOriginalPrenom() {
        return originalPrenom;
    }

    public void setOriginalPrenom(String originalPrenom) {
        this.originalPrenom = originalPrenom;
    }

    public String getOriginalEmail() {
        return originalEmail;
    }

    public void setOriginalEmail(String originalEmail) {
        this.originalEmail = originalEmail;
    }

    public String getOriginalTelephone() {
        return originalTelephone;
    }

    public void setOriginalTelephone(String originalTelephone) {
        this.originalTelephone = originalTelephone;
    }
}