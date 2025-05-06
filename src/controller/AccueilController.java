package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccueilController implements Initializable {

    @FXML
    private BorderPane mainBorderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    // Méthode pour retourner à la page d'accueil
    @FXML
    public void goHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/accueil.fxml"));
            BorderPane accueilPane = loader.load();
            mainBorderPane.getScene().setRoot(accueilPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour afficher la liste des propriétaires
    @FXML
    public void goFindOwners() {
        loadPage("/view/liste_proprietaires.fxml");
    }

    // Méthode pour afficher la liste des vétérinaires
    @FXML
    public void goVets() {
        loadPage("/view/liste_veto.fxml");
    }

    // Méthode pour afficher la page d'ajout de propriétaire
    @FXML
    public void goAddOwner() {
        loadPage("/view/nouveau_proprietaire.fxml");
    }

    // Fonction utilitaire pour charger une page simple dans le center
    private void loadPage(String cheminFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(cheminFxml));
            Pane page = loader.load();
            mainBorderPane.setCenter(page);
        } catch (IOException e) {
            e.printStackTrace();
            // En cas d'erreur, afficher un message dans la console
            System.err.println("Impossible de charger la page: " + cheminFxml);

            // Option: vous pourriez ajouter un message d'erreur dans l'interface
            Label errorLabel = new Label("Erreur lors du chargement de la page. Veuillez réessayer plus tard.");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px; -fx-padding: 20;");

            StackPane errorPane = new StackPane(errorLabel);
            errorPane.setStyle("-fx-background-color: #b8c1f0;");
            mainBorderPane.setCenter(errorPane);
        }
    }

    @FXML
    public void goContact() {
        try {
            loadPage("/view/Contact.fxml");  // Assurez-vous que ce chemin est correct
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur lors du chargement de la page de contacts : " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}