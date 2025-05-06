package controller;

import dao.VisiteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Animal;
import model.Visite;

public class AjouterVisiteController {

    @FXML
    private TextField fieldDate, fieldMotif;

    @FXML
    private ComboBox<String> comboVeto;

    @FXML
    private Label labelAnimal;

    private String nomAnimal;
    private Animal animal;

    // Liste des vétérinaires disponibles
    private ObservableList<String> veterinaires = FXCollections.observableArrayList(
            "Mr Lemoine","Mr Robert","Mr Derkaoui", "Mme Bennani", "Mr Lamrini"
    );

    public void setNomAnimal(String nomAnimal) {
        this.nomAnimal = nomAnimal;
        if (labelAnimal != null && nomAnimal != null) {
            labelAnimal.setText("Visite pour: " + nomAnimal);
        }
        System.out.println("AjouterVisiteController - nomAnimal défini: " + nomAnimal);
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
        if (animal != null) {
            this.nomAnimal = animal.getNom();
            if (labelAnimal != null) {
                labelAnimal.setText("Visite pour: " + animal.getNom());
            }
            System.out.println("AjouterVisiteController - animal défini: " + animal.getNom());
        }
    }

    @FXML
    public void initialize() {
        System.out.println("AjouterVisiteController - Initialisation du contrôleur");

        // Initialiser la liste des vétérinaires dans le ComboBox
        comboVeto.setItems(veterinaires);
    }

    @FXML
    private void ajouterVisite() {
        try {
            String date = fieldDate.getText().trim();
            String motif = fieldMotif.getText().trim();
            String veto = comboVeto.getValue();

            // Validation des champs
            if (date.isEmpty() || motif.isEmpty() || veto == null) {
                showAlert(Alert.AlertType.ERROR, "Tous les champs sont requis.");
                return;
            }

            // Validation du nom de l'animal
            if (nomAnimal == null || nomAnimal.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Aucun animal sélectionné.");
                return;
            }

            System.out.println("Tentative d'ajout de visite pour: " + nomAnimal);

            Visite nouvelleVisite = new Visite(date, motif, veto);

            // Essayer d'ajouter la visite au DAO avec gestion d'erreur
            boolean succes = false;
            try {
                succes = VisiteDAO.ajouterVisite(nomAnimal, nouvelleVisite);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur lors de l'ajout de la visite: " + e.getMessage());
                return;
            }

            // Vérifier si l'ajout a réussi
            if (succes) {
                showAlert(Alert.AlertType.INFORMATION, "Visite ajoutée avec succès !");
                // Fermer la fenêtre après ajout
                Stage stage = (Stage) fieldDate.getScene().getWindow();
                stage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Impossible d'ajouter la visite. Veuillez vérifier les informations.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur inattendue: " + e.getMessage());
        }
    }

    @FXML
    private void annuler() {
        Stage stage = (Stage) fieldDate.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Erreur" : "Information");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setLabelAnimal(Label labelAnimal) {
        this.labelAnimal = labelAnimal;
    }
}