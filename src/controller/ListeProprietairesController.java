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

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ListeProprietairesController {

    @FXML
    private TextField champRecherche;

    private List<Proprietaire> tousLesProprietaires;

    @FXML
    public void initialize() {
        tousLesProprietaires = ProprietaireDAO.getAll();
    }

    @FXML
    public void rechercher() {
        String filtre = champRecherche.getText().toLowerCase().trim();

        List<Proprietaire> resultat = tousLesProprietaires.stream()
                .filter(p -> p.getNom().toLowerCase().contains(filtre))
                .collect(Collectors.toList());

        if (resultat.isEmpty()) {
            showAlert("Aucun propriétaire trouvé pour : " + filtre);
        } else {
            Proprietaire p = resultat.get(0); // on prend le premier résultat
            afficherInfosProprietaire(p);
        }
    }

    private void afficherInfosProprietaire(Proprietaire p) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/infos_proprietaire.fxml"));
            BorderPane root = loader.load();

            InfosProprietaireController controller = loader.getController();
            controller.setProprietaire(p);

            Stage stage = new Stage();
            stage.setTitle("Détails propriétaire");
            stage.setScene(new Scene(root, 900, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToAjoutProprietaire() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/nouveau_proprietaire.fxml"));
            BorderPane root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter un propriétaire");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
