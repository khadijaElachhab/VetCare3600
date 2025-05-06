package controller;

import dao.VeterinaireDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Veterinaire;

import java.io.IOException;

public class ListeVetoController {
    @FXML
    private TableView<Veterinaire> tableVeto;

    @FXML
    private TableColumn<Veterinaire, String> colPrenom;

    @FXML
    private TableColumn<Veterinaire, String> colNom;

    @FXML
    private TableColumn<Veterinaire, String> colSpecialite;

    @FXML
    private TableColumn<Veterinaire, String> colEmail;

    @FXML
    private TableColumn<Veterinaire, String> colTelephone;

    @FXML
    private Button btnRetour;

    @FXML
    private Button btnAjouter;

    @FXML
    public void initialize() {
        // Configuration des colonnes
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colSpecialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        // Chargement des données
        tableVeto.setItems(FXCollections.observableArrayList(VeterinaireDAO.getAll()));
    }

    @FXML
    public void goHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/accueil.fxml"));
            BorderPane root = loader.load();
            Stage stage = (Stage) tableVeto.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ajouterProprietaire() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/nouveau_proprietaire.fxml"));
            BorderPane root = loader.load();
            Stage stage = (Stage) tableVeto.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}