package controller;

import dao.ProprietaireDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Proprietaire;

public class NouveauProprietaireController {
    @FXML private TextField champNom;
    @FXML private TextField champPrenom;
    @FXML private TextField champEmail;
    @FXML private TextField champTelephone;

    @FXML
    public void ajouter() {
        String nom = champNom.getText().trim();
        String prenom = champPrenom.getText().trim();
        String email = champEmail.getText().trim(); // Modifié ici aussi
        String telephone = champTelephone.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || telephone.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Tous les champs sont obligatoires !");
            return;
        }

        // Création et insertion du nouveau propriétaire
        // Note: Le champ stockera l'email
        Proprietaire nouveau = new Proprietaire(nom, prenom, "");
        nouveau.setEmail(email); // Stockage de l'email
        nouveau.setTelephone(telephone);

        ProprietaireDAO.ajouterProprietaire(nouveau);

        // Afficher simplement une alerte de succès
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Propriétaire ajouté avec succès !");

        // Effacer les champs du formulaire
        clearFields();
    }

    private void showAlert(Alert.AlertType alertType, String title, String msg) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void clearFields() {
        champNom.clear();
        champPrenom.clear();
        champEmail.clear();
        champTelephone.clear();

        // Optionnel : replacer le focus sur le premier champ
        champPrenom.requestFocus();
    }

    @FXML
    public void retour() throws Exception {
        goHome(); // Utilise la méthode existante
    }

    @FXML
    public void goHome() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/accueil.fxml"));
        BorderPane root = loader.load();
        Stage stage = (Stage) champNom.getScene().getWindow();
        stage.setScene(new Scene(root, 900, 600));
    }
}