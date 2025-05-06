package controller;

import dao.ProprietaireDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Proprietaire;

import java.io.IOException;
import java.util.List;

public class RechercheProprietaireController {

    @FXML
    private TextField champNom;

    @FXML
    private TableView<Proprietaire> tableResultats;

    @FXML
    private TableColumn<Proprietaire, String> colNom;

    @FXML
    private TableColumn<Proprietaire, String> colPrenom;

    @FXML
    private TableColumn<Proprietaire, String> colEmail;

    @FXML
    private TableColumn<Proprietaire, String> colTelephone;

    // Initialiser le DAO
    private ProprietaireDAO proprietaireDAO = new ProprietaireDAO();

    @FXML
    public void initialize() {
        colNom.setCellValueFactory(cell -> cell.getValue().nomProperty());
        colPrenom.setCellValueFactory(cell -> cell.getValue().prenomProperty());
        colEmail.setCellValueFactory(cell -> cell.getValue().emailProperty());
        colTelephone.setCellValueFactory(cell -> cell.getValue().telephoneProperty());


        // Ajout d'un événement de double-clic sur les lignes du tableau
        tableResultats.setOnMouseClicked(this::handleTableClick);
    }

    // Gestionnaire de double-clic sur le tableau
    private void handleTableClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Proprietaire selectedProprietaire = tableResultats.getSelectionModel().getSelectedItem();
            if (selectedProprietaire != null) {
                afficherFicheProprietaire(selectedProprietaire);
            }
        }
    }

    // Recherche simple par nom
    @FXML
    public void rechercherProprietaire() {
        String nomRecherche = champNom.getText().trim();

        if (nomRecherche.isEmpty()) {
            showAlert("Veuillez saisir un nom.");
            return;
        }
        // Utiliser l'instance du DAO au lieu de la méthode statique
        List<Proprietaire> resultats = ProprietaireDAO.rechercherParNom(nomRecherche);

        if (!resultats.isEmpty()) {
            tableResultats.setItems(FXCollections.observableArrayList(resultats));
        } else {
            showAlert("Aucun propriétaire trouvé.");
            tableResultats.getItems().clear();
        }
        // Déboguer - afficher des messages dans la console
        System.out.println("Recherche effectuée pour: " + nomRecherche);
        System.out.println("Nombre de résultats: " + resultats.size());
    }

    // Navigation vers l'accueil
    @FXML
    public void goHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/accueil.fxml"));
            BorderPane root = loader.load();
            Stage stage = (Stage) champNom.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ouvre la fenêtre pour ajouter un propriétaire
    @FXML
    public void ajouterProprietaire() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/nouveau_proprietaire.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Nouveau propriétaire");
            stage.setScene(new Scene(root, 900, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Affiche la liste complète (optionnelle)
    @FXML
    public void goToListeProprietaires() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/liste_proprietaires.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Liste des propriétaires");
            stage.setScene(new Scene(root, 900, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ouvre la fiche d'un propriétaire
    private void afficherFicheProprietaire(Proprietaire p) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/infos_proprietaire.fxml"));
            BorderPane root = loader.load();

            InfosProprietaireController controller = loader.getController();
            controller.setProprietaire(p);

            Stage stage = new Stage();
            stage.setTitle("Informations du propriétaire");
            stage.setScene(new Scene(root, 900, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Affiche une alerte
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public ProprietaireDAO getProprietaireDAO() {
        return proprietaireDAO;
    }

    public void setProprietaireDAO(ProprietaireDAO proprietaireDAO) {
        this.proprietaireDAO = proprietaireDAO;
    }
}